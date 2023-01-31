package com.example.foodinfo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.example.foodinfo.utils.UiState
import com.example.foodinfo.utils.repeatOn
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

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


    private var _uiState = MutableSharedFlow<UiState>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    ).also {
        it.tryEmit(UiState.Loading())
    }
    private val uiState: SharedFlow<UiState> = _uiState.asSharedFlow()

    private val uiChunksState: HashMap<Any, UiState> = hashMapOf()


    /**
     * Observes UI state changes.
     *
     * All repetitions of the same state are filtered out.
     *
     * @param callBack runnable that is executed each time UI state changes
     */
    fun observeUiState(callBack: suspend (UiState) -> Unit) {
        repeatOn(Lifecycle.State.STARTED) {
            uiState.distinctUntilChanged { old, new ->
                old.equalState(new)
            }.collectLatest {
                callBack(it)
            }
        }
    }

    fun updateUiState(value: UiState): Boolean {
        return _uiState.tryEmit(value)
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