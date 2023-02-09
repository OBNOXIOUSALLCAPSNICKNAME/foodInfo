package com.example.foodinfo.ui

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentSearchCategoryBinding
import com.example.foodinfo.ui.adapter.SearchLabelsAdapter
import com.example.foodinfo.ui.decorator.GridItemDecoration
import com.example.foodinfo.utils.appComponent
import com.example.foodinfo.utils.baseAnimation
import com.example.foodinfo.view_model.SearchCategoryViewModel


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
        observeData(
            dataFlow = viewModel.category,
            onInitStart = {
                binding.rvLabels.isVisible = false
                binding.tvHeader.isVisible = false
            },
            onInitComplete = {
                binding.tvHeader.baseAnimation()
                binding.rvLabels.baseAnimation()
            },
            loadingHandlerDelegate = {
                binding.pbContent.isVisible = true
            },
            successHandlerDelegate = { category ->
                binding.tvHeader.text = category.name
                recyclerAdapter.submitList(category.labels)
                binding.pbContent.isVisible = false
            }
        )
    }
}