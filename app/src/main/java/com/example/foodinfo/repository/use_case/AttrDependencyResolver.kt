package com.example.foodinfo.repository.use_case

import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


interface AttrDependencyResolver {

    /**
     * Function that helps to collect data dependent on recipe attributes.
     *
     * This function will collect [attrFlow] and re-emit everything until [State.Success].
     *
     * If [State.Success] was collected, it will call [dataFlowProvider], pass [State.Success.data]
     * to it and start collecting and re-emitting everything from the dataFlow provided by [dataFlowProvider].
     *
     */
    fun <attrT, dataT> getResolved(
        attrFlow: Flow<State<attrT>>,
        dataFlowProvider: (attrT) -> Flow<State<dataT>>
    ): Flow<State<dataT>> {
        return flow {
            emit(State.Loading())
            attrFlow.collect { attrs ->
                when (attrs) {
                    is State.Loading -> {}
                    is State.Success -> {
                        dataFlowProvider(attrs.data).collect(this::emit)
                    }
                    is State.Error   -> {
                        emit(State.Error(attrs.message, attrs.error))
                    }
                }
            }
        }
    }
}