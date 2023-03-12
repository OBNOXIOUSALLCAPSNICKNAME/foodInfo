package com.example.foodinfo.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*


/**
 * Function that will help to atomically fetch data that depends on some extra data
 *
 * It will collect [extraData] and on each emission of [State] with [State.data] **!= null**
 * will invoke [outputDataProvider] and re-emit everything from provided flow to result flow.
 * Previous flow from [outputDataProvider] will be canceled.
 *
 * - If [State.Error] was collected from [extraData], it will be emitted into result flow and
 * [outputDataProvider] will not be called.
 *
 * - If [State.Loading] with [State.data] **!= null** was collected from [extraData],
 * [outputDataProvider] will be invoked but [transform] will be applied to provided flow to re-emit every
 * [State] with [State.data] **!= null** into result flow **ONLY** as [State.Loading]. Also, if [State.Error]
 * will be collected from provided flow, it **WILL NOT** be emitted into result flow.
 *
 * - All repetitions of same [State.data] from [extraData] will be filtered out to avoid redundant calls of
 * [outputDataProvider]. It may lead into situations when [State.Success] will be ignored due to [State.Loading]
 * was previously collected with the same [State.data] (which is bad for screens that does not use data from
 * [State.Loading]). To fix that, [getResolved] will emit latest value collected from [outputDataProvider].
 *
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun <extraT, outputT> getResolved(
    extraData: Flow<State<extraT>>,
    outputDataProvider: (extraT) -> Flow<State<outputT>>
) = flow {
    emit(State.Loading())

    var equalData = false
    var lastValue: State<outputT>? = null

    extraData.distinctUntilChanged { old, new ->
        equalData = State.isEqualData(old.data, new.data)
        State.isEqual(old, new)
    }.flatMapLatest { extraState ->
        when (extraState) {
            is State.Loading -> {
                if (extraState.data != null) {
                    outputDataProvider(extraState.data).transform { resultState ->
                        lastValue = resultState
                        resultState.data?.let { emit(State.Loading(it)) }
                    }
                } else {
                    flowOf(State.Loading())
                }
            }
            is State.Success -> {
                if (equalData && lastValue != null) {
                    if (lastValue!!.data != null) {
                        flowOf(State.Success(lastValue!!.data!!))
                    } else {
                        flowOf(lastValue!!)
                    }
                } else {
                    outputDataProvider(extraState.data!!)
                }
            }
            is State.Error   -> {
                flowOf(State.Error(extraState.messageID!!, extraState.error!!, extraState.errorCode!!))
            }
        }
    }.collect(::emit)
}