package com.example.foodinfo.ui

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodinfo.databinding.FragmentSearchFilterCategoryBinding
import com.example.foodinfo.ui.adapter.FilterCategoryEditAdapter
import com.example.foodinfo.utils.appComponent
import com.example.foodinfo.utils.baseAnimation
import com.example.foodinfo.utils.showDescriptionDialog
import com.example.foodinfo.view_model.SearchFilterCategoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchFilterCategoryFragment : BaseFragment<FragmentSearchFilterCategoryBinding>(
    FragmentSearchFilterCategoryBinding::inflate
) {
    private val viewModel: SearchFilterCategoryViewModel by viewModels {
        requireActivity().appComponent.viewModelsFactory()
    }

    private val args: SearchFilterCategoryFragmentArgs by navArgs()

    private lateinit var recyclerAdapter: FilterCategoryEditAdapter

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }

    private val onResetClickListener: () -> Unit = {
        viewModel.reset()
        recyclerAdapter.notifyDataSetChanged()
    }

    private val onQuestionMarkClickListener: (Int) -> Unit = { infoID ->
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val labelItem = viewModel.getLabelHint(infoID)
            withContext(Dispatchers.Main) {
                showDescriptionDialog(
                    labelItem.name,
                    labelItem.description,
                    labelItem.preview
                )
            }
        }
    }


    override fun initUI() {
        viewModel.categoryID = args.categoryID

        binding.btnBack.setOnClickListener { onBackClickListener() }
        binding.btnReset.setOnClickListener { onResetClickListener() }

        recyclerAdapter = FilterCategoryEditAdapter(
            requireContext(),
            onQuestionMarkClickListener
        )

        with(binding.rvLabels) {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context).also {
                it.orientation = LinearLayoutManager.VERTICAL
            }
            itemAnimator = null
        }
    }

    override fun subscribeUI() {
        observeData(
            dataFlow = viewModel.labels,
            onInitStart = {
                binding.rvLabels.isVisible = false
            },
            onInitComplete = {
                binding.rvLabels.isVisible = true
                binding.rvLabels.baseAnimation()
            },
            loadingHandlerDelegate = {
                binding.pbContent.isVisible = true
            },
            successHandlerDelegate = { category ->
                recyclerAdapter.submitList(category.labels)
                binding.pbContent.isVisible = false
            }
        )

        observeData(
            dataFlow = viewModel.category,
            successHandlerDelegate = { categoryInfo ->
                binding.tvHeader.text = categoryInfo.name
            }
        )
    }
}