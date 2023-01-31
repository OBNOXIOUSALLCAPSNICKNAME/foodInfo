package com.example.foodinfo.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodinfo.databinding.FragmentSearchFilterCategoryBinding
import com.example.foodinfo.ui.adapter.FilterCategoryEditAdapter
import com.example.foodinfo.utils.appComponent
import com.example.foodinfo.utils.repeatOn
import com.example.foodinfo.utils.showDescriptionDialog
import com.example.foodinfo.view_model.SearchFilterCategoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
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

    private val onItemClickListener: (Int, Boolean) -> Unit = { ID, isSelected ->
        viewModel.updateLabel(ID, isSelected)
    }

    override fun initUI() {
        viewModel.categoryID = args.categoryID

        binding.btnBack.setOnClickListener { onBackClickListener() }
        binding.btnReset.setOnClickListener { onResetClickListener() }
        binding.tvHeader.text = viewModel.category.name

        recyclerAdapter = FilterCategoryEditAdapter(
            requireContext(),
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
        repeatOn(Lifecycle.State.STARTED) {
            viewModel.labels.collectLatest {
                recyclerAdapter.submitList(it.labels)
            }
        }
    }
}