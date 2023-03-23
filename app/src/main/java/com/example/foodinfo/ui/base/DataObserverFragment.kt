package com.example.foodinfo.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.collection.arrayMapOf
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.viewbinding.ViewBinding
import com.example.foodinfo.utils.State
import com.example.foodinfo.utils.extensions.filterState
import com.example.foodinfo.utils.extensions.repeatOn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest


/**
 * Fragment that can observe provided data flow using [observeData] or [observePage]
 */
abstract class DataObserverFragment<VB : ViewBinding>(
    bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : BaseFragment<VB>(bindingInflater) {

    private val initializedUIParts = arrayMapOf<Int, Boolean>()

    /**
     * Collect provided [dataFlow] inside [repeatOn] with [Lifecycle.State.STARTED], applying
     * [filterState] to filter out all states with the same content and invokes the appropriate
     * delegate depending on the collected state.
     *
     * @param dataFlow Flow of data to observe.
     * @param uiPartID Any unique number that doesn't change over time. It makes it possible to use
     * [observeData] multiple times to update different screen features and helps [DataObserverFragment]
     * to choose whether [onStart] and [onFinish] should be called or not.
     * @param useLoadingData When true, if [State.Loading] will be collected with [State.data] **!= null**,
     * [onSuccess] will be called.
     * @param onStart Called before starting to collect provided [dataFlow]. Usage example: hide UI and show
     * loading spinner/placeholders.
     * @param onFinish Called after first [onSuccess]. Usage example: hide loading spinner/placeholders and
     * start animation for initialized UI.
     * @param onError Called if [State.Error] was collected. Usage example: show error placeholder if UI was
     * not yet initialized or show snackbar if UI already initialized.
     * @param onSuccess Called each time when [State] with [State.data] **!= null** was collected.
     * Usage example: initialize UI.
     */
    internal inline fun <T> observeData(
        dataFlow: Flow<State<T>>,
        uiPartID: Int = 0,
        useLoadingData: Boolean,
        crossinline onError: (Int, Throwable, Int) -> Unit = { _, _, _ -> },
        crossinline onStart: () -> Unit = {},
        crossinline onFinish: () -> Unit = {},
        crossinline onSuccess: suspend (T) -> Unit
    ) {
        repeatOn(Lifecycle.State.STARTED) {
            if (uiPartID !in initializedUIParts) {
                initializedUIParts[uiPartID] = false
            }
            if (!initializedUIParts[uiPartID]!!) {
                onStart()
            }
            dataFlow.filterState(useLoadingData).collect { state ->
                when {
                    state is State.Error                                             -> {
                        onError(state.messageID!!, state.throwable!!, state.errorCode!!)
                    }
                    state.data != null && (state is State.Success || useLoadingData) -> {
                        onSuccess(state.data)
                        if (!initializedUIParts[uiPartID]!!) {
                            onFinish()
                            initializedUIParts[uiPartID] = true
                        }
                    }
                }
            }
        }
    }

    /**
     * Collect provided [dataFlow] and [pageFlow] inside different [repeatOn] with [Lifecycle.State.STARTED],
     * applying [filterState] to filter out all states with the same content from [dataFlow] and invokes
     * the appropriate delegate depending on the collected state.
     *
     * Mainly this function helps to handle UI state depending on collected [State] from [dataFlow] while
     * observing [pageFlow].
     *
     * ### USE CASE:
     *
     * ~~~
     * class ViewModel() {
     *     private val _extraData = MutableSharedFlow<ExtraData>(extraBufferCapacity = 1)
     *
     *     val extraData: SharedFlow<State<ExtraData>> = repository.getExtraData()
     *         .shareIn(viewModelScope, SharingStarted.Lazily, 0)
     *
     *     val pagedData = _extraData
     *         .flatMapLatest { data -> repository.getPagedData(data) }
     *         .cachedIn(viewModelScope)
     *         .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())
     *
     *     fun setExtra(data: ExtraData) {
     *         _extraData.tryEmit(data)
     *      }
     * }
     * ~~~
     *
     * ~~~
     * class Fragment() {
     *     observePage(
     *         useLoadingData = true,
     *         dataFlow = viewModel.extraData,
     *         pageFlow = viewModel.pagedData,
     *         onError = { }, // handle errors
     *         onSuccess = viewModel::setExtra,
     *         onPageCollected = pagedDataAdapter::submitData
     *     )
     * }
     * ~~~
     *
     * @param dataFlow Flow of extra data to observe.
     * @param pageFlow Flow of paged data to observe.
     * @param useLoadingData When true, if [State.Loading] will be collected with [State.data] **!= null**,
     * [onSuccess] will be called.
     * @param onError Called if [State.Error] was collected. Usage example: show error placeholder if UI was
     * not yet initialized or show snackbar if UI already initialized.
     * @param onSuccess Called each time when [State] with [State.data] **!= null** was collected from [dataFlow].
     * @param onPageCollected Called each time when [PagingData] was collected from [pageFlow].
     *
     */
    internal inline fun <T, R : Any> observePage(
        dataFlow: Flow<State<T>>,
        pageFlow: Flow<PagingData<R>>,
        useLoadingData: Boolean,
        crossinline onError: (Int, Throwable, Int) -> Unit = { _, _, _ -> },
        crossinline onSuccess: suspend (T) -> Unit,
        crossinline onPageCollected: suspend (PagingData<R>) -> Unit,
    ) {
        repeatOn(Lifecycle.State.STARTED) {
            dataFlow.filterState(useLoadingData).collect { state ->
                when {
                    state is State.Error                                             -> {
                        onError(state.messageID!!, state.throwable!!, state.errorCode!!)
                    }
                    state.data != null && (state is State.Success || useLoadingData) -> {
                        onSuccess(state.data)
                    }
                }
            }
        }
        repeatOn(Lifecycle.State.STARTED) {
            pageFlow.collectLatest { page ->
                onPageCollected(page)
            }
        }
    }
}