package com.example.foodinfo.features.favorite.fragment

import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentFavoriteRecipesBinding
import com.example.foodinfo.features.favorite.adapter.recipeAdapterDelegate
import com.example.foodinfo.features.favorite.model.RecipeVHModel
import com.example.foodinfo.features.favorite.view_model.FavoriteRecipesViewModel
import com.example.foodinfo.ui.base.BaseFragment
import com.example.foodinfo.ui.base.adapter.AppPageAdapter
import com.example.foodinfo.ui.base.adapter.appPageAdapter
import com.example.foodinfo.ui.ListItemDecoration
import com.example.foodinfo.utils.extensions.appViewModels
import com.example.foodinfo.utils.extensions.observe
import kotlinx.coroutines.flow.collectLatest


class FavoriteRecipesFragment : BaseFragment<FragmentFavoriteRecipesBinding>(
    FragmentFavoriteRecipesBinding::inflate
) {

    private val onItemHoldListener: (RecipeVHModel) -> Unit = { model ->
        if (!viewModel.selectManager.isSelectMode) {
            viewModel.selectManager.isSelectMode = true
            viewModel.selectManager.toggleItem(model)
        }
    }

    private val onItemClickListener: (RecipeVHModel) -> Unit = { model ->
        if (viewModel.selectManager.isSelectMode) {
            viewModel.selectManager.toggleItem(model)
        } else {
            findNavController().navigate(
                FavoriteRecipesFragmentDirections.actionFFavoriteToFRecipeExtended(model.ID)
            )
        }
    }

    private val onSortClickListener: () -> Unit = {
        findNavController().navigate(
            FavoriteRecipesFragmentDirections.actionFFavoriteToFFavoriteSort()
        )
    }

    private val navigateBackCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (viewModel.selectManager.isSelectMode) {
                viewModel.selectManager.isSelectMode = false
            } else {
                findNavController().popBackStack()
            }
        }
    }

    private val navigateCallback = NavController.OnDestinationChangedListener { _, destination, _ ->
        if (destination.id == R.id.f_favorite) {
            viewModel.selectManager.isSelectMode = false
        }
    }


    private val viewModel: FavoriteRecipesViewModel by appViewModels()

    private val recyclerAdapter: AppPageAdapter<RecipeVHModel> by appPageAdapter(
        recipeAdapterDelegate(onItemHoldListener, onItemClickListener)
    )


    override fun onDestroyView() {
        // removing all callbacks to prevent memory leaks
        findNavController().removeOnDestinationChangedListener(navigateCallback)
        navigateBackCallback.remove()
        super.onDestroyView()
    }


    override fun initUI() {
        with(binding) {
            btnSort.setOnClickListener { onSortClickListener() }
            btnDelete.setOnClickListener { viewModel.delRecipesFromFavorite() }
            cbSelectAll.setOnClickListener { viewModel.selectManager.toggleAll() }
        }

        findNavController().addOnDestinationChangedListener(navigateCallback)
        requireActivity().onBackPressedDispatcher.addCallback(navigateBackCallback)

        with(binding.rvRecipes) {
            adapter = recyclerAdapter
            setHasFixedSize(true)
            addItemDecoration(
                ListItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.favorite_item_space),
                    RecyclerView.VERTICAL
                )
            )
        }
    }

    override fun subscribeUI() {
        observe(Lifecycle.State.STARTED) {
            viewModel.selectManager.data.collectLatest(recyclerAdapter::submitData)
        }
        observe(Lifecycle.State.STARTED) {
            viewModel.selectManager.modeState.collectLatest { modeState ->
                with(binding) {
                    llBaseMenu.isVisible = !modeState.isSelectedMode
                    llEditMenu.isVisible = modeState.isSelectedMode
                    cbSelectAll.isChecked = modeState.selected == modeState.total
                    if (modeState.isSelectedMode) {
                        tvSelectedCount.text = getString(
                            R.string.selected_value,
                            modeState.selected
                        )
                    }
                }
            }
        }
    }
}