package com.example.foodinfo.repository.use_case

import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transformWhile


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

    /**
     * Function that helps to update data dependent on recipe attributes.
     *
     * This function will collect [attrFlow] until [State.Success] or [State.Error] will be collected.
     *
     * If [State.Success] was collected, will call [updateDataDelegate], pass [State.Success.data] to it,
     * stops collecting and return null.
     *
     * If an error occurred inside [updateDataDelegate] or [State.Error] was collected, will return that error
     *
     */
    fun <attrT> setResolved(
        attrFlow: Flow<State<attrT>>,
        updateDataDelegate: (attrT) -> Unit
    ): Exception? {
        var result: Exception? = null
        attrFlow.transformWhile<State<attrT>, Boolean> { attrs ->
            when (attrs) {
                is State.Loading -> {
                    true
                }
                is State.Success -> {
                    try {
                        updateDataDelegate(attrs.data)
                    } catch (e: Exception) {
                        result = e
                    }
                    false
                }
                is State.Error   -> {
                    result = attrs.error
                    false
                }
            }
        }
        return result
    }
}