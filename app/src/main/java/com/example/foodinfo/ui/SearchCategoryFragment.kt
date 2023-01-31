package com.example.foodinfo.ui

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentSearchCategoryBinding
import com.example.foodinfo.ui.adapter.SearchLabelsAdapter
import com.example.foodinfo.ui.decorator.GridItemDecoration
import com.example.foodinfo.utils.appComponent
import com.example.foodinfo.utils.baseAnimation
import com.example.foodinfo.utils.repeatOn
import com.example.foodinfo.view_model.SearchCategoryViewModel
import kotlinx.coroutines.flow.collectLatest


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

    private val onItemClickListener: (Int) -> Unit = { infoID ->
        findNavController().navigate(
            SearchCategoryFragmentDirections.actionFSearchCategoryToFSearchLabel(infoID)
        )
    }


    override fun initUI() {
        viewModel.categoryID = args.categoryID

        binding.tvCategory.text = viewModel.category.name
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
                GridItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.search_labels_item_horizontal),
                    resources.getDimensionPixelSize(R.dimen.search_labels_item_vertical),
                    3,
                    RecyclerView.VERTICAL
                )
            )
            itemAnimator = null
        }
    }

    override fun subscribeUI() {
        repeatOn(Lifecycle.State.STARTED) {
            viewModel.labels.collectLatest {
                binding.rvLabels.isVisible = false
                recyclerAdapter.submitList(it) {
                    binding.rvLabels.isVisible = true
                    binding.rvLabels.baseAnimation()
                }
            }
        }
    }
}