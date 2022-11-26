package com.example.foodinfo.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foodinfo.databinding.FragmentSearchCategoryBinding
import com.example.foodinfo.ui.adapter.SearchLabelsAdapter
import com.example.foodinfo.ui.decorator.SearchRecipeItemDecoration
import com.example.foodinfo.utils.appComponent
import com.example.foodinfo.utils.repeatOn
import com.example.foodinfo.utils.showDescriptionDialog
import com.example.foodinfo.view_model.SearchCategoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchCategoryFragment : BaseFragment<FragmentSearchCategoryBinding>(
    FragmentSearchCategoryBinding::inflate
) {

    private val args: SearchCategoryFragmentArgs by navArgs()

    private lateinit var recyclerAdapter: SearchLabelsAdapter

    private val viewModel: SearchCategoryViewModel by viewModels {
        requireActivity().appComponent.viewModelsFactory()
    }

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }

    private val onSearchClickListener: () -> Unit = {
        findNavController().navigate(
            SearchCategoryFragmentDirections.actionFSearchCategoryToFSearchInput()
        )
    }

    private val onHeaderClickListener: () -> Unit = {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val categoryItem = viewModel.category
            withContext(Dispatchers.Main) {
                showDescriptionDialog(
                    categoryItem.name,
                    categoryItem.description,
                    categoryItem.preview
                )
            }
        }
    }

    private val onItemClickListener: (String, String) -> Unit = { category, label ->
        findNavController().navigate(
            SearchCategoryFragmentDirections.actionFSearchCategoryToFSearchLabel(
                category,
                label
            )
        )
    }


    override fun initUI() {
        viewModel.categoryName = args.category

        binding.tvCategory.text = args.category
        binding.tvCategory.setOnClickListener { onHeaderClickListener() }
        binding.btnBack.setOnClickListener { onBackClickListener() }
        binding.btnSearch.setOnClickListener { onSearchClickListener() }


        recyclerAdapter = SearchLabelsAdapter(
            requireContext(),
            onItemClickListener,
        )

        with(binding.rvLabels) {
            adapter = recyclerAdapter
            setHasFixedSize(true)
            addItemDecoration(
                SearchRecipeItemDecoration(
                    resources.getDimensionPixelSize(com.example.foodinfo.R.dimen.search_labels_item_horizontal),
                    resources.getDimensionPixelSize(com.example.foodinfo.R.dimen.search_labels_item_vertical),
                    3
                )
            )
        }
    }

    override fun subscribeUI() {
        repeatOn(Lifecycle.State.STARTED) {
            withContext(Dispatchers.IO) {
                recyclerAdapter.submitList(viewModel.labels)
            }
        }
    }
}