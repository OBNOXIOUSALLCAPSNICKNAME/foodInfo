package com.example.foodinfo.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


/**
 * Fragment that helps avoid boilerplate binding code and launch all necessary UI preparations it the
 * correct lifecycle state using [initUI] and [subscribeUI]
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