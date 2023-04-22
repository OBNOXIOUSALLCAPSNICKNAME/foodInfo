package com.example.foodinfo.features.category.fragment

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentLabelsOfCategoryBinding
import com.example.foodinfo.features.category.adapter.labelAdapterDelegate
import com.example.foodinfo.features.category.model.LabelVHModel
import com.example.foodinfo.features.category.viewm_model.LabelsOfCategoryViewModel
import com.example.foodinfo.ui.base.BaseFragment
import com.example.foodinfo.ui.base.adapter.AppListAdapter
import com.example.foodinfo.ui.base.adapter.appListAdapter
import com.example.foodinfo.ui.GridItemDecoration
import com.example.foodinfo.utils.extensions.appViewModels
import com.example.foodinfo.utils.extensions.baseAnimation
import com.example.foodinfo.utils.extensions.observeState


class LabelsOfCategoryFragment : BaseFragment<FragmentLabelsOfCategoryBinding>(
    FragmentLabelsOfCategoryBinding::inflate
) {

    private val onSearchClickListener: () -> Unit = {
        findNavController().navigate(
            LabelsOfCategoryFragmentDirections.actionFSearchCategoryToFSearchInput()
        )
    }

    private val onItemClickListener: (LabelVHModel) -> Unit = { label ->
        findNavController().navigate(
            LabelsOfCategoryFragmentDirections.actionFSearchCategoryToFSearchLabel(label.ID)
        )
    }

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }


    private val args: LabelsOfCategoryFragmentArgs by navArgs()

    private val viewModel: LabelsOfCategoryViewModel by appViewModels()

    private val recyclerAdapter: AppListAdapter by appListAdapter(
        labelAdapterDelegate(onItemClickListener)
    )


    override fun initUI() {
        viewModel.categoryID = args.categoryID
        binding.tvHeader.text = args.categoryName

        binding.btnBack.setOnClickListener { onBackClickListener() }
        binding.btnSearch.setOnClickListener { onSearchClickListener() }

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
        observeState(
            dataFlow = viewModel.categoryLabels,
            useLoadingData = true,
            onStart = {
                binding.rvLabels.isVisible = false
                binding.tvHeader.isVisible = false
                binding.pbContent.isVisible = true
            },
            onFinish = {
                binding.pbContent.isVisible = false
                binding.tvHeader.baseAnimation()
                binding.rvLabels.baseAnimation()
            },
            onSuccess = recyclerAdapter::submitList
        )
    }
}