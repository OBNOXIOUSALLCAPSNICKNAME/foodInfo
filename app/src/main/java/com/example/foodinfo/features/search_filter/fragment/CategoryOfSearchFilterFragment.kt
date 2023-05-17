package com.example.foodinfo.features.search_filter.fragment

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodinfo.core.ui.base.BaseFragment
import com.example.foodinfo.core.ui.base.adapter.AppListAdapter
import com.example.foodinfo.core.ui.base.adapter.appListAdapter
import com.example.foodinfo.core.utils.extensions.appHintManager
import com.example.foodinfo.core.utils.extensions.appViewModels
import com.example.foodinfo.core.utils.extensions.baseAnimation
import com.example.foodinfo.core.utils.extensions.observeState
import com.example.foodinfo.databinding.FragmentCategoryOfSearchFilterBinding
import com.example.foodinfo.features.search_filter.adapter.categoryEditAdapterDelegate
import com.example.foodinfo.features.search_filter.model.LabelEditVHModel
import com.example.foodinfo.features.search_filter.view_model.CategoryOfSearchFilterViewModel


class CategoryOfSearchFilterFragment : BaseFragment<FragmentCategoryOfSearchFilterBinding>(
    FragmentCategoryOfSearchFilterBinding::inflate
) {

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }

    private val onResetClickListener: () -> Unit = {
        viewModel.reset()
    }

    private val onItemClickListener: (LabelEditVHModel) -> Unit = { label ->
        viewModel.updateLabel(label.ID, !label.isSelected)
    }

    private val onQuestionMarkClickListener: (LabelEditVHModel) -> Unit = { label ->
        hintManager.showLabel(this) { viewModel.getLabelHint(label.infoID) }
    }


    private val viewModel: CategoryOfSearchFilterViewModel by appViewModels()

    private val args: CategoryOfSearchFilterFragmentArgs by navArgs()

    private val recyclerAdapter: AppListAdapter by appListAdapter(
        categoryEditAdapterDelegate(onQuestionMarkClickListener, onItemClickListener)
    )

    private val hintManager by appHintManager()


    override fun initUI() {
        viewModel.categoryID = args.categoryID
        binding.tvHeader.text = args.categoryName

        binding.btnBack.setOnClickListener { onBackClickListener() }
        binding.btnReset.setOnClickListener { onResetClickListener() }

        with(binding.rvLabels) {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context).also {
                it.orientation = LinearLayoutManager.VERTICAL
            }
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