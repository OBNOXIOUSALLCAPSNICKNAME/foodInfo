package com.example.foodinfo.domain.state

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*


/**
 * Function that will help to atomically fetch data that depends on some extra data.
 *
 * It will collect [extraData] and on each emission of [State] with [State.data] **!= null**
 * will invoke [outputDataProvider] and re-emit everything from provided flow to result flow.
 * Previous flow from [outputDataProvider] will be canceled.
 *
 * - All [State.Initial] from [outputDataProvider] will be filtered out to ensure that UI will receive
 * this state only once.
 *
 * - All repetitions of same [State.data] from [extraData] will be filtered out to avoid redundant
 * [outputDataProvider] calls.
 *
 * - If [State.Failure] was collected from [extraData], it will be emitted into result flow and
 * [outputDataProvider] will not be called.
 *
 * - If [State.Loading] with [State.data] **!= null** was collected from [extraData], result flow will
 * transform [State.Success] into [State.Loading] provided by [outputDataProvider] and ignore [State.Failure].
 *
 */
@OptIn(ExperimentalCoroutinesApi::class)
internal fun <extraT, outputT> getResolved(
    extraData: Flow<State<extraT>>,
    outputDataProvider: (extraT) -> Flow<State<outputT>>
) = channelFlow<State<outputT>> {
    send(State.Initial())

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
            is State.Initial                   -> {
                flowOf(State.Initial())
            }
            is State.Success, is State.Loading -> {
                outputDataProvider(extraState.data!!)
            }
            is State.Failure                   -> {
                flowOf(State.Failure(extraState.messageID!!, extraState.throwable!!, extraState.errorCode!!))
            }
        }
    }.collect { resultState ->
        lastResult = resultState
        when {
            (isLoading && resultState is State.Success) -> {
                send(State.Loading(resultState.data!!))
            }
            (isLoading && resultState is State.Failure) ||
            (resultState is State.Initial)              -> {
                //no-op
            }
            else                                        -> {
                send(resultState)
            }
        }
    }
}