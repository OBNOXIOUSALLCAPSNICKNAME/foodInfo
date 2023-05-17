package com.example.foodinfo.features.search.fragment

import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.core.ui.base.BaseFragment
import com.example.foodinfo.core.ui.base.adapter.AppListAdapter
import com.example.foodinfo.core.ui.base.adapter.appListAdapter
import com.example.foodinfo.core.utils.extensions.appViewModels
import com.example.foodinfo.core.utils.extensions.hideKeyboard
import com.example.foodinfo.core.utils.extensions.observe
import com.example.foodinfo.core.utils.extensions.showKeyboard
import com.example.foodinfo.databinding.FragmentSearchInputBinding
import com.example.foodinfo.features.search.adapter.searchInputAdapterDelegate
import com.example.foodinfo.features.search.model.SearchInputVHModel
import com.example.foodinfo.features.search.view_model.SearchInputViewModel
import kotlinx.coroutines.flow.collectLatest


class SearchInputFragment : BaseFragment<FragmentSearchInputBinding>(
    FragmentSearchInputBinding::inflate
) {

    private val onArrowClickListener: (SearchInputVHModel) -> Unit = { searchInput ->
        with(binding) {
            etSearchInput.setQuery(searchInput.inputText, false)
            showKeyboard(binding.etSearchInput)
        }
    }

    private val onItemClickListener: (SearchInputVHModel) -> Unit = { searchInput ->
        viewModel.addToHistory(searchInput.inputText)
        findNavController().navigate(
            SearchInputFragmentDirections.actionFSearchInputToFSearchQuery(searchInput.inputText)
        )
    }

    private val onFilterClickListener: () -> Unit = {
        findNavController().navigate(
            SearchInputFragmentDirections.actionFSearchInputToFSearchFilter()
        )
    }

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }

    private val onScrollStateListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                hideKeyboard()
            }
        }
    }

    private val onQueryChangedListener = object : OnQueryTextListener,
        SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String): Boolean {
            viewModel.updateSearchHistory(newText)
            return false
        }

        override fun onQueryTextSubmit(query: String): Boolean {
            viewModel.addToHistory(query)
            findNavController().navigate(
                SearchInputFragmentDirections.actionFSearchInputToFSearchQuery(query)
            )
            return false
        }
    }


    private val viewModel: SearchInputViewModel by appViewModels()

    private val recyclerAdapter: AppListAdapter by appListAdapter(
        searchInputAdapterDelegate(onArrowClickListener, onItemClickListener)
    )


    override fun initUI() {
        binding.btnBack.setOnClickListener { onBackClickListener() }
        binding.btnFilter.setOnClickListener { onFilterClickListener() }

        with(binding.rvSearchInput) {
            adapter = recyclerAdapter
            setHasFixedSize(true)
            addOnScrollListener(onScrollStateListener)
        }
        binding.etSearchInput.setOnQueryTextListener(onQueryChangedListener)
    }

    override fun subscribeUI() {
        observe(Lifecycle.State.STARTED) {
            viewModel.searchHistory.collectLatest(recyclerAdapter::submitList)
        }
    }


    override fun onResume() {
        super.onResume()
        showKeyboard(binding.etSearchInput)
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }
}