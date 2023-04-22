package com.example.foodinfo.features.explore.fragment

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentExploreByLabelBinding
import com.example.foodinfo.features.explore.adapter.recipeAdapterDelegate
import com.example.foodinfo.features.explore.model.RecipeVHModel
import com.example.foodinfo.features.explore.view_model.ExploreByLabelViewModel
import com.example.foodinfo.ui.base.BaseFragment
import com.example.foodinfo.ui.base.adapter.AppPageAdapter
import com.example.foodinfo.ui.base.adapter.appPageAdapter
import com.example.foodinfo.ui.GridItemDecoration
import com.example.foodinfo.utils.extensions.appViewModels
import com.example.foodinfo.utils.extensions.observePages
import com.example.foodinfo.utils.extensions.showDescriptionDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ExploreByLabelFragment : BaseFragment<FragmentExploreByLabelBinding>(
    FragmentExploreByLabelBinding::inflate
) {

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }

    private val onSearchClickListener: () -> Unit = {
        findNavController().navigate(
            ExploreByLabelFragmentDirections.actionFSearchLabelToFSearchInput()
        )
    }

    private val onHeaderClickListener: () -> Unit = {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val labelItem = viewModel.getLabelHint(args.labelID)
            withContext(Dispatchers.Main) {
                showDescriptionDialog(
                    labelItem.name,
                    labelItem.description,
                    labelItem.preview
                )
            }
        }
    }

    private val onItemClickListener: (RecipeVHModel) -> Unit = { recipe ->
        findNavController().navigate(
            ExploreByLabelFragmentDirections.actionFSearchLabelToFRecipeExtended(recipe.ID)
        )
    }

    private val onFavoriteClickListener: (RecipeVHModel) -> Unit = { recipe ->
        viewModel.invertFavoriteStatus(recipe.ID)
    }


    private val args: ExploreByLabelFragmentArgs by navArgs()

    private val viewModel: ExploreByLabelViewModel by appViewModels()

    private val recyclerAdapter: AppPageAdapter<RecipeVHModel> by appPageAdapter(
        recipeAdapterDelegate(onItemClickListener, onFavoriteClickListener)
    )


    override fun initUI() {
        viewModel.labelID = args.labelID

        binding.tvLabel.setOnClickListener { onHeaderClickListener() }
        binding.btnBack.setOnClickListener { onBackClickListener() }
        binding.btnSearch.setOnClickListener { onSearchClickListener() }

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
            binding.tvLabel.text = viewModel.getLabelHint(args.labelID).name
        }
    }
}