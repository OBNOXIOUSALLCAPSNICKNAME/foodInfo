package com.example.foodinfo.ui.fragment

import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentFavoriteBinding
import com.example.foodinfo.repository.model.RecipeFavoriteModel
import com.example.foodinfo.ui.adapter.FavoriteAdapter
import com.example.foodinfo.ui.base.BaseFragment
import com.example.foodinfo.ui.decorator.ListItemDecoration
import com.example.foodinfo.utils.extensions.appViewModels
import com.example.foodinfo.utils.extensions.observe
import com.example.foodinfo.view_model.FavoriteViewModel
import kotlinx.coroutines.flow.collectLatest


class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(
    FragmentFavoriteBinding::inflate
) {

    private val viewModel: FavoriteViewModel by appViewModels()

    private lateinit var recyclerAdapter: FavoriteAdapter


    private val onItemHoldListener: (RecipeFavoriteModel) -> Unit = { model ->
        if (!viewModel.selectManager.isSelectMode) {
            viewModel.selectManager.isSelectMode = true
            viewModel.selectManager.toggleItem(model)
        }
    }

    private val onItemClickListener: (RecipeFavoriteModel) -> Unit = { model ->
        if (viewModel.selectManager.isSelectMode) {
            viewModel.selectManager.toggleItem(model)
        } else {
            findNavController().navigate(
                FavoriteFragmentDirections.actionFFavoriteToFRecipeExtended(model.ID)
            )
        }
    }

    private val onSortClickListener: () -> Unit = {
        findNavController().navigate(
            FavoriteFragmentDirections.actionFFavoriteToFFavoriteSort()
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

        recyclerAdapter = FavoriteAdapter(
            onItemHoldListener,
            onItemClickListener
        )

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
                binding.llBaseMenu.isVisible = !modeState.isSelectedMode
                binding.llEditMenu.isVisible = modeState.isSelectedMode
                binding.cbSelectAll.isChecked = modeState.selected == modeState.total
                if (modeState.isSelectedMode) {
                    binding.tvSelectedCount.text = getString(
                        R.string.selected_value,
                        modeState.selected
                    )
                }
            }
        }
    }
}