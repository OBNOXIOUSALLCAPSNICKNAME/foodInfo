package com.example.foodinfo.features.search_filter.fragment

import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodinfo.databinding.FragmentCategoryOfSearchFilterBinding
import com.example.foodinfo.features.search_filter.adapter.categoryEditAdapterDelegate
import com.example.foodinfo.features.search_filter.model.LabelEditVHModel
import com.example.foodinfo.features.search_filter.view_model.CategoryOfSearchFilterViewModel
import com.example.foodinfo.ui.base.BaseFragment
import com.example.foodinfo.ui.base.adapter.AppListAdapter
import com.example.foodinfo.ui.base.adapter.appListAdapter
import com.example.foodinfo.utils.extensions.appViewModels
import com.example.foodinfo.utils.extensions.baseAnimation
import com.example.foodinfo.utils.extensions.observeState
import com.example.foodinfo.utils.extensions.showDescriptionDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val labelItem = viewModel.getLabelHint(label.infoID)
            withContext(Dispatchers.Main) {
                showDescriptionDialog(
                    labelItem.name,
                    labelItem.description,
                    labelItem.preview
                )
            }
        }
    }


    private val viewModel: CategoryOfSearchFilterViewModel by appViewModels()

    private val args: CategoryOfSearchFilterFragmentArgs by navArgs()

    private val recyclerAdapter: AppListAdapter by appListAdapter(
        categoryEditAdapterDelegate(onQuestionMarkClickListener, onItemClickListener)
    )


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