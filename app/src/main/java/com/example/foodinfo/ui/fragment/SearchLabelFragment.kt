package com.example.foodinfo.ui.fragment

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentSearchLabelBinding
import com.example.foodinfo.ui.adapter.SearchRecipeAdapter
import com.example.foodinfo.ui.base.BaseFragment
import com.example.foodinfo.ui.decorator.GridItemDecoration
import com.example.foodinfo.utils.extensions.appViewModels
import com.example.foodinfo.utils.extensions.observePages
import com.example.foodinfo.utils.extensions.showDescriptionDialog
import com.example.foodinfo.view_model.SearchLabelViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchLabelFragment : BaseFragment<FragmentSearchLabelBinding>(
    FragmentSearchLabelBinding::inflate
) {

    private val args: SearchLabelFragmentArgs by navArgs()

    private lateinit var recyclerAdapter: SearchRecipeAdapter

    private val viewModel: SearchLabelViewModel by appViewModels()

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }

    private val onSearchClickListener: () -> Unit = {
        findNavController().navigate(
            SearchLabelFragmentDirections.actionFSearchLabelToFSearchInput()
        )
    }

    private val onHeaderClickListener: () -> Unit = {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val labelItem = viewModel.getLabel(args.labelID)
            withContext(Dispatchers.Main) {
                showDescriptionDialog(
                    labelItem.name,
                    labelItem.description,
                    labelItem.preview
                )
            }
        }
    }

    private val onItemClickListener: (String) -> Unit = { id ->
        findNavController().navigate(
            SearchLabelFragmentDirections.actionFSearchLabelToFRecipeExtended(id)
        )
    }

    private val onFavoriteClickListener: (String) -> Unit = { id ->
        viewModel.invertFavoriteStatus(id)
    }


    override fun initUI() {
        viewModel.labelID = args.labelID

        binding.tvLabel.setOnClickListener { onHeaderClickListener() }
        binding.btnBack.setOnClickListener { onBackClickListener() }
        binding.btnSearch.setOnClickListener { onSearchClickListener() }

        recyclerAdapter = SearchRecipeAdapter(
            onItemClickListener,
            onFavoriteClickListener
        )

        with(binding.rvRecipes) {
            adapter = recyclerAdapter
            setHasFixedSize(true)
            addItemDecoration(
                GridItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.search_recipes_item_horizontal),
                    resources.getDimensionPixelSize(R.dimen.search_recipes_item_vertical),
                    2,
                    RecyclerView.VERTICAL
                )
            )
            itemAnimator = null
        }
    }

    override fun subscribeUI() {
        observePages(
            useLoadingData = true,
            dataFlow = viewModel.pagingHelper,
            pageFlow = viewModel.recipes,
            onSuccess = viewModel::setPreset,
            onPageCollected = recyclerAdapter::submitData
        )
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            binding.tvLabel.text = viewModel.getLabel(args.labelID).name
        }
    }
}