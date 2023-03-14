package com.example.foodinfo.ui.fragment

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodinfo.databinding.FragmentSearchFilterCategoryBinding
import com.example.foodinfo.ui.adapter.FilterCategoryEditAdapter
import com.example.foodinfo.ui.base.DataObserverFragment
import com.example.foodinfo.utils.extensions.appComponent
import com.example.foodinfo.utils.extensions.baseAnimation
import com.example.foodinfo.utils.extensions.showDescriptionDialog
import com.example.foodinfo.view_model.SearchFilterCategoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchFilterCategoryFragment : DataObserverFragment<FragmentSearchFilterCategoryBinding>(
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
    }

    private val onItemClickListener: (Int, Boolean) -> Unit = { ID, isSelected ->
        viewModel.update(ID, isSelected)
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
            onQuestionMarkClickListener,
            onItemClickListener
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
            dataFlow = viewModel.category,
            useLoadingData = false,
            onStart = {
                binding.rvLabels.isVisible = false
                binding.tvHeader.isVisible = false
                binding.pbContent.isVisible = true
            },
            onInitUI = { category ->
                binding.tvHeader.text = category.name
                recyclerAdapter.submitList(category.labels)
                binding.pbContent.isVisible = false
                binding.tvHeader.baseAnimation()
                binding.rvLabels.baseAnimation()
            },
            onRefreshUI = { category ->
                binding.tvHeader.text = category.name
                recyclerAdapter.submitList(category.labels)
            }
        )
    }
}