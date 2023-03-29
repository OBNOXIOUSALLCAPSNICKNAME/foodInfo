package com.example.foodinfo.repository.state_handling

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*


/**
 * Function that will help to atomically fetch data that depends on some extra data
 *
 * It will collect [extraData] and on each emission of [State] with [State.data] **!= null**
 * will invoke [outputDataProvider] and re-emit everything from provided flow to result flow.
 * Previous flow from [outputDataProvider] will be canceled.
 *
 * - All repetitions of same [State.data] from [extraData] will be filtered out to avoid redundant
 * [outputDataProvider] calls.
 *
 * - If [State.Error] was collected from [extraData], it will be emitted into result flow and
 * [outputDataProvider] will not be called.
 *
 * - If [State.Loading] with [State.data] **!= null** was collected from [extraData], result flow will
 * transform [State.Success] into [State.Loading] provided by [outputDataProvider] and ignore [State.Error]
 *
 */
@OptIn(ExperimentalCoroutinesApi::class)
internal fun <extraT, outputT> getResolved(
    extraData: Flow<State<extraT>>,
    outputDataProvider: (extraT) -> Flow<State<outputT>>
) = channelFlow {
    send(State.Loading())

    var isLoading = true
    var equalData = false
    var lastResult: State<outputT>? = null

    extraData.distinctUntilChanged { old, new ->
        isLoading = new is State.Loading
        equalData = State.isEqualData(old.data, new.data)
        State.isEqual(old, new)
    }.transform { tempState ->
        if (tempState is State.Success && equalData && lastResult != null) {
            send(lastResult!!)
        } else {
            emit(tempState)
        }
    }.flatMapLatest { extraState ->
        when (extraState) {
            is State.Success, is State.Loading -> {
                if (extraState.data != null) {
                    outputDataProvider(extraState.data)
                } else {
                    flowOf(State.Loading())
                }
            }
            is State.Error                     -> {
                flowOf(State.Error(extraState.messageID!!, extraState.throwable!!, extraState.errorCode!!))
            }
        }
    }.collect { resultState ->
        lastResult = resultState
        when {
            isLoading && resultState is State.Success -> {
                send(State.Loading(resultState.data))
            }
            isLoading && resultState is State.Error   -> {

            }
            else                                      -> {
                send(resultState)
            }
        }
    }
}