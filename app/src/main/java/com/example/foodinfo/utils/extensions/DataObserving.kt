package com.example.foodinfo.utils.extensions

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.State.Utils.filterState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * Collect provided [dataFlow] inside [observe] with [Lifecycle.State.STARTED], applying
 * [filterState] to filter out all states with the same content and invokes the appropriate
 * delegate depending on the collected state.
 *
 * @param dataFlow Flow of data to observe.
 * @param useLoadingData Determines whether to use data from [State.Loading] or not.
 * @param onStart Called when [State.Initial] was collected. Usage example: hide UI and show
 * loading spinner/placeholders.
 * @param onFinish Called after first [onSuccess] (or [State.Loading] if [useLoadingData] is **true**).
 * Usage example: hide loading spinner/placeholders and start animation for initialized UI.
 * @param onFailure Called if [State.Failure] was collected. Default is [logStateError]. Usage example:
 * show error placeholder if UI was not yet initialized or show snackbar if UI already initialized.
 * @param onSuccess Called each time when [State.Success] (or [State.Loading] if [useLoadingData] is **true**)
 * was collected. Usage example: initialize UI.
 */
inline fun <T> Fragment.observeState(
    dataFlow: Flow<State<T>>,
    useLoadingData: Boolean,
    crossinline onStart: () -> Unit = {},
    crossinline onFinish: () -> Unit = {},
    crossinline onSuccess: suspend (T) -> Unit,
    crossinline onFailure: (Int, Int, Throwable) -> Unit = ::logStateError,
) {
    var initializationCompleted = true
    observe(Lifecycle.State.STARTED) {
        dataFlow.filterState(useLoadingData).collect { state ->
            when {
                state is State.Initial                                           -> {
                    onStart()
                    initializationCompleted = false
                }
                state is State.Failure                                           -> {
                    onFailure(state.errorCode!!, state.messageID!!, state.throwable!!)
                }
                state.data != null && (state is State.Success || useLoadingData) -> {
                    onSuccess(state.data)
                    if (!initializationCompleted) {
                        onFinish()
                        initializationCompleted = true
                    }
                }
            }
        }
    }
}

/**
 * Collect provided [dataFlow] and [pageFlow] inside different [observe] with [Lifecycle.State.STARTED],
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
 *     observer.observePage(
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
 * @param useLoadingData Determines whether to use data from [State.Loading] or not.
 * @param onFailure Called if [State.Failure] was collected. Default is [logStateError]. Usage example:
 * show error placeholder if UI was not yet initialized or show snackbar if UI already initialized.
 * @param onSuccess Called each time when [State.Success] (or [State.Loading] if [useLoadingData] is **true**)
 * was collected from [dataFlow].
 * @param onPageCollected Called each time when [PagingData] was collected from [pageFlow].
 *
 */
inline fun <T, R : Any> Fragment.observePages(
    dataFlow: Flow<State<T>>,
    pageFlow: Flow<PagingData<R>>,
    useLoadingData: Boolean,
    crossinline onSuccess: suspend (T) -> Unit,
    crossinline onFailure: (Int, Int, Throwable) -> Unit = ::logStateError,
    crossinline onPageCollected: suspend (PagingData<R>) -> Unit
) {
    observe(Lifecycle.State.STARTED) {
        dataFlow.filterState(useLoadingData).collect { state ->
            when {
                state is State.Failure                                           -> {
                    onFailure(state.errorCode!!, state.messageID!!, state.throwable!!)
                }
                state.data != null && (state is State.Success || useLoadingData) -> {
                    onSuccess(state.data)
                }
            }
        }
    }
    observe(Lifecycle.State.STARTED) {
        pageFlow.collectLatest { page ->
            onPageCollected(page)
        }
    }
}

/**
 * Shorthand for
 * ~~~
 * viewLifecycleOwner.lifecycleScope.launch {
 *     viewLifecycleOwner.lifecycle.repeatOnLifecycle(state) {
 *     ...
 *     }
 * }
 * ~~~
 */
inline fun Fragment.observe(
    state: Lifecycle.State,
    crossinline runnable: suspend () -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(state) {
            runnable.invoke()
        }
    }
}

/**
 * Logs error code, throwable and error message with [State.LOG_TAG] and shows SnackBar with error code
 */
fun Fragment.logStateError(errorCode: Int, messageID: Int, throwable: Throwable) {
    Log.d(State.LOG_TAG, "code: $errorCode message: ${getString(messageID)}", throwable)
    view?.let { view ->
        Snackbar.make(view, errorCode.toString(), Snackbar.LENGTH_SHORT).show()
    }
}
