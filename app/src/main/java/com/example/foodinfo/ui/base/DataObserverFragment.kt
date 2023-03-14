package com.example.foodinfo.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.example.foodinfo.utils.State
import com.example.foodinfo.utils.extensions.repeatOn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot


/**
 * Fragment that can observe provided data flow using [observeData]
 */
abstract class DataObserverFragment<VB : ViewBinding>(
    bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : BaseFragment<VB>(bindingInflater) {

    private var isUIInitialized = false

    /**
     * Collect provided [dataFlow] inside [repeatOn] with [Lifecycle.State.STARTED], applying
     * [distinctUntilChanged] to filter out all states with the same content and invokes the appropriate
     * delegate depending on the collected state.
     *
     * @param dataFlow Flow of data to observe.
     * @param useLoadingData When true, if [State.Loading] will be collected with [State.data] **!= null**,
     * [onInitUI] or [onRefreshUI] will be called.
     * @param onStart Called before starting to collect provided [dataFlow]. Usage example: hide UI and show
     * loading spinner/placeholders.
     * @param onError Called if [State.Error] was collected. Usage example: show error placeholder if UI was
     * not yet initialized or show snackbar if UI already initialized.
     * @param onInitUI Called when [State] with [State.data] **!= null** was collected for the first time.
     * Usage example: hide loading spinner/placeholders, initialize UI and start initialize animation.
     * @param onRefreshUI Called when [State] with [State.data] **!= null** was collected for all subsequent
     * times except the first. Usage example: update UI and start refresh animation.
     */
    internal inline fun <T> observeData(
        dataFlow: Flow<State<T>>,
        useLoadingData: Boolean,
        crossinline onError: (Int, Throwable, Int) -> Unit = { _, _, _ -> },
        crossinline onStart: () -> Unit = {},
        crossinline onInitUI: suspend (T) -> Unit,
        crossinline onRefreshUI: suspend (T) -> Unit
    ) {
        repeatOn(Lifecycle.State.STARTED) {
            if (!isUIInitialized)
                onStart()
            dataFlow
                .filterNot(State.Utils::isEmptyLoading)
                .distinctUntilChanged(
                    if (useLoadingData) State.Utils::isEqualInsensitive else State.Utils::isEqual
                )
                .collect { state ->
                    when (state) {
                        is State.Error   -> {
                            onError(state.messageID!!, state.throwable!!, state.errorCode!!)
                        }
                        is State.Success -> {
                            if (!isUIInitialized) {
                                onInitUI(state.data!!)
                                isUIInitialized = true
                            } else {
                                onRefreshUI(state.data!!)
                            }
                        }
                        is State.Loading -> {
                            if (useLoadingData && state.data != null) {
                                if (!isUIInitialized) {
                                    onInitUI(state.data)
                                    isUIInitialized = true
                                } else {
                                    onRefreshUI(state.data)
                                }
                            }
                        }
                    }
                }
        }
    }
}