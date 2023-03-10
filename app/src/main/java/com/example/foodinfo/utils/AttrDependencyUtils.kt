package com.example.foodinfo.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*


/**
 * Function that will help to atomically fetch data that depends on some extra data
 *
 * It will collect [extraDataFlow] and on each emission of [State] with [State.data] **!= null**
 * will invoke [dataFlowProvider] and re-emit everything from provided flow to result flow. Previous flow from
 * [dataFlowProvider] will be canceled.
 *
 * If [State.Error] was collected from [extraDataFlow], it will be emitted into result flow and
 * [dataFlowProvider] will not be called.
 *
 * If [State.Loading] with [State.data] **!= null** was collected from [extraDataFlow],
 * [dataFlowProvider] will be invoked but [transform] will be applied to provided flow to re-emit every
 * [State] with [State.data] **!= null** into result flow ONLY with [State.Loading].
 *
 * **Example**
 * ~~~
 * val flow1 = flowOf(Loading(1), Loading(2), Success(2))
 * val flow2 = flowOf(Loading("a"), Success("b"))
 *
 * getResolved(
 *     extraDataFlow = flow1.onEach { delay(100) },
 *     dataFlowProvider = { extra -> flow2.transform { "${it}_${extra}"  } }
 * ).collect { print(it) }
 * ~~~
 * **Output**
 * ~~~
 * Loading("a_1")
 * Loading("b_1")
 * Loading("a_2")
 * Loading("b_2")
 * Loading("a_2")
 * Success("b_2")
 * ~~~
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun <extraT, outT> getResolved(
    extraDataFlow: Flow<State<extraT>>,
    dataFlowProvider: (extraT) -> Flow<State<outT>>
) = flow {
    emit(State.Loading())

    extraDataFlow.flatMapLatest { state ->
        when (state) {
            is State.Success -> {
                dataFlowProvider(state.data!!)
            }
            is State.Error   -> {
                flowOf(State.Error(state.messageID!!, state.error!!, state.errorCode!!))
            }
            is State.Loading -> {
                if (state.data != null) {
                    dataFlowProvider(state.data).transform {
                        if (it.data != null) {
                            emit(State.Loading(it.data))
                        } else {
                            emit(it)
                        }
                    }
                } else {
                    flowOf(State.Loading())
                }
            }
        }
    }.collect(::emit)
}

/**
 * Similar to [getResolved] but with the difference that all repetitions of same data from [extraDataFlow]
 * will be filtered out (regardless of state type) and all emissions from [dataFlowProvider]'s flow will be
 * directly re-emitted into result flow without state changing regardless of [extraDataFlow] state type.
 *
 * **Example**
 * ~~~
 * val flow1 = flowOf(Loading(1), Success(1), Loading(1), Success(2),)
 * val flow2 = flowOf(Loading("a"), Success("b"))
 *
 * getResolvedFiltered(
 *     extraDataFlow = flow1.onEach { delay(100) },
 *     dataFlowProvider = { extra -> flow2.transform { "${it}_${extra}"  } }
 * ).collect { print(it) }
 * ~~~
 * **Output**
 * ~~~
 * Loading("a_1")
 * Success("b_1")
 * Loading("a_2")
 * Success("b_2")
 * ~~~
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun <extraT, outT> getResolvedFiltered(
    extraDataFlow: Flow<State<extraT>>,
    dataFlowProvider: (extraT) -> Flow<State<outT>>
) = flow {
    emit(State.Loading())

    extraDataFlow.distinctUntilChanged(State.Utils::isEqualInsensitive).flatMapLatest { state ->
        when (state) {
            is State.Error                     -> {
                flowOf(State.Error(state.messageID!!, state.error!!, state.errorCode!!))
            }
            is State.Success, is State.Loading -> {
                if (state.data != null) {
                    dataFlowProvider(state.data)
                } else {
                    flowOf(State.Loading())
                }
            }
        }
    }.collect(::emit)
}