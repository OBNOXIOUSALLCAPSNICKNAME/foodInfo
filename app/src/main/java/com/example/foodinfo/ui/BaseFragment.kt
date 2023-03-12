package com.example.foodinfo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.example.foodinfo.utils.State
import com.example.foodinfo.utils.repeatOn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot


abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    private var isUIInitialized = false

    /**
     * Calls in onViewCreated().
     *
     * Function to initialize Views and add clickListeners to them.
     */
    open fun initUI() {}

    /**
     * Calls in onViewCreated() after [initUI]
     *
     * Function to launch all necessary coroutines.
     */
    open fun subscribeUI() {}


    /**
     * @param dataFlow Flow of data to observe. All repetitions of the same [State] will be filtered out
     * with [State.Utils.isEqual].
     * @param useLoadingData When true, if [State.Loading] will be collected with [State.data] **!= null**,
     * [onInitUI] or [onRefreshUI] will be called.
     * @param onStart Called before starting to collect provided [dataFlow]. Usage example: hide UI and show
     * loading spinner/placeholders.
     * @param onError Called if [State.Error] was collected. Usage example: show error placeholder if UI was
     * not yet initialized or show snackbar if UI already initialized.
     * @param onInitUI Called when [State] with [State.data] **!= null** collected and [isUIInitialized]
     * is **false**. After completion, [isUIInitialized] will be changed to **true**. Usage example: hide
     * loading spinner/placeholders, initialize UI and start initialize animation.
     * @param onRefreshUI Called when [State] with [State.data] **!= null** collected and [isUIInitialized]
     * is **true**. Usage example: update UI and start refresh animation.
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
                            onError(state.messageID!!, state.error!!, state.errorCode!!)
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        subscribeUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}