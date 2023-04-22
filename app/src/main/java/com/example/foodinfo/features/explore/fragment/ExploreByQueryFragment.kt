package com.example.foodinfo.features.explore.fragment

import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.core.ui.base.adapter.AppPageAdapter
import com.example.foodinfo.core.ui.base.adapter.appPageAdapter
import com.example.foodinfo.core.utils.extensions.appViewModels
import com.example.foodinfo.core.utils.extensions.observePages
import com.example.foodinfo.databinding.FragmentExploreByQueryBinding
import com.example.foodinfo.features.explore.adapter.recipeAdapterDelegate
import com.example.foodinfo.features.explore.model.RecipeVHModel
import com.example.foodinfo.features.explore.view_model.ExploreByQueryViewModel


class ExploreByQueryFragment : com.example.foodinfo.core.ui.base.BaseFragment<FragmentExploreByQueryBinding>(
    FragmentExploreByQueryBinding::inflate
) {

    private val onBackClickListener: () -> Unit = {
        findNavController().popBackStack(R.id.f_search_input, true)
    }

    private val onSearchClickListener: () -> Unit = {
        findNavController().navigate(
            ExploreByQueryFragmentDirections.actionFSearchQueryToFSearchInput()
        )
    }

    private val onItemClickListener: (RecipeVHModel) -> Unit = { recipe ->
        findNavController().navigate(
            ExploreByQueryFragmentDirections.actionFSearchQueryToFRecipeExtended(recipe.ID)
        )
    }

    private val onFavoriteClickListener: (RecipeVHModel) -> Unit = { recipe ->
        viewModel.invertFavoriteStatus(recipe.ID)
    }

    private val navigateBackCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack(R.id.f_search_input, true)
        }
    }


    private val args: ExploreByQueryFragmentArgs by navArgs()

    private val viewModel: ExploreByQueryViewModel by appViewModels()

    private val recyclerAdapter: AppPageAdapter<RecipeVHModel> by appPageAdapter(
        recipeAdapterDelegate(onItemClickListener, onFavoriteClickListener)
    )


    override fun onStop() {
        navigateBackCallback.remove()
        super.onStop()
    }


    override fun initUI() {
        viewModel.inputText = args.query

        binding.tvQuery.text = args.query
        binding.btnBack.setOnClickListener { onBackClickListener() }
        binding.btnSearch.setOnClickListener { onSearchClickListener() }

        requireActivity().onBackPressedDispatcher.addCallback(navigateBackCallback)

        with(binding.rvRecipes) {
            adapter = recyclerAdapter
            setHasFixedSize(true)
            addItemDecoration(
                com.example.foodinfo.core.ui.GridItemDecoration(
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
            onSuccess = viewModel::setHelper,
            onPageCollected = recyclerAdapter::submitData
        )
    }
}