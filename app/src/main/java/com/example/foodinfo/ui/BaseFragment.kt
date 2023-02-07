package com.example.foodinfo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.example.foodinfo.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * Base class to avoid boilerplate binding initialization and releasing.
 */
abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

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
     * Observes data state changes.
     *
     * All repetitions of the same state are filtered out by comparing it's data.
     *
     * @param dataFlow Flow of data to observe.
     * @param root UI view or ViewGroup that will be replaced with error placeholder by [defaultErrorHandler]
     * if no errorHandlerDelegate was passed.
     * @param errorHandlerDelegate Calls when data state is [State.Error] (e.g. show proper error placeholder
     * according to passed error and message). Uses [defaultErrorHandler] by default
     * @param loadingHandlerDelegate Calls when data state is [State.Loading]. (e.g. show Loading spinner or
     * placeholders). NOTE that if dataFlow runs on background, fragment can enter [Fragment.isResumed]
     * and will be shown to user before this block is called and can lead to unwanted UI appearance.
     * Does nothing by default
     * @param successHandlerDelegate Calls when data state is [State.Success]. (e.g. initialize
     * UI with passed data and hide spinner). Does nothing by default
     * @param onInitStart Calls before before data collection starts. (e.g. hide all UI).
     * Does nothing by default
     * @param onInitComplete Calls after the first call of loadingHandlerDelegate. (e.g. start initialization
     * animation). Does nothing by default
     * @param onRefreshStart Calls before loadingHandlerDelegate (every time except first
     * loadingHandlerDelegate call). (e.g. hide UI and determine which part of data was updated to start
     * proper refresh animation. Just like payloads in DiffUtil). Does nothing by default
     * @param onRefreshComplete Calls after loadingHandlerDelegate (every time except first
     * loadingHandlerDelegate call). (e.g. start refresh animation). Does nothing by default
     */
    fun <T> observeData(
        dataFlow: Flow<State<T>>,
        root: View = binding.root,
        errorHandlerDelegate: (View, String, Exception) -> Unit = this::defaultErrorHandler,
        loadingHandlerDelegate: () -> Unit = {},
        successHandlerDelegate: suspend (T) -> Unit = {},
        onInitStart: () -> Unit = {},
        onInitComplete: (T) -> Unit = {},
        onRefreshStart: (T) -> Unit = {},
        onRefreshComplete: (T) -> Unit = {}
    ) {
        repeatOn(Lifecycle.State.STARTED) {
            onInitStart()
            var isInitialized = false
            dataFlow.distinctUntilChanged { old, new -> old.equalState(new) }.collectLatest { data ->
                Log.d("123", "${data}")
                when (data) {
                    is State.Error   -> {
                        errorHandlerDelegate(root, data.message, data.error)
                    }
                    is State.Success -> {
                        if (!isInitialized) {
                            successHandlerDelegate(data.data)
                            onInitComplete(data.data)
                            isInitialized = true
                        } else {
                            onRefreshStart(data.data)
                            successHandlerDelegate(data.data)
                            onRefreshComplete(data.data)
                        }
                    }
                    is State.Loading -> {
                        loadingHandlerDelegate()
                    }
                }
            }
        }
    }

    //TODO implement exceptions handling
    private fun defaultErrorHandler(root: View, message: String, error: Exception) {
        root.isVisible = false
        when (error) {
            is NoDataException        -> {

            }
            is CorruptedDataException -> {

            }
            is NoInternetException    -> {

            }
            else                      -> {

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