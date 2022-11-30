package com.example.foodinfo.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentFavoriteBinding
import com.example.foodinfo.ui.adapter.FavoriteAdapter
import com.example.foodinfo.ui.decorator.FavoriteItemDecoration
import com.example.foodinfo.utils.appComponent
import com.example.foodinfo.utils.repeatOn
import com.example.foodinfo.view_model.FavoriteViewModel
import kotlinx.coroutines.flow.collectLatest


class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(
    FragmentFavoriteBinding::inflate
) {

    private val viewModel: FavoriteViewModel by viewModels {
        requireActivity().appComponent.viewModelsFactory()
    }

    private lateinit var recyclerAdapter: FavoriteAdapter


    private val onHoldClickListener: (String) -> Unit = { id ->
        if (!viewModel.isEditMode.value) {
            viewModel.setEditMode(true)
            viewModel.updateSelectStatus(id)
        }
    }

    private val onSortClickListener: () -> Unit = {
        findNavController().navigate(
            FavoriteFragmentDirections.actionFFavoriteToFFavoriteSort()
        )
    }

    private val onDeleteClickListener: () -> Unit = {
        viewModel.delSelected()
        viewModel.unselectAll()
        viewModel.setEditMode(false)
    }

    private val onSelectAllClickListener: () -> Unit = {
        if (viewModel.selectedCount.value == viewModel.totalRecipesCount) {
            viewModel.unselectAll()
        } else {
            viewModel.selectAll()
        }
        recyclerAdapter.notifyDataSetChanged()
    }

    private val isSelected: (String) -> Boolean = { id ->
        viewModel.isSelected(id)
    }

    private val isEditMode: () -> Boolean = {
        viewModel.isEditMode.value
    }


    private val onReadyToSelect: (String) -> Unit = { id ->
        viewModel.updateSelectStatus(id)
    }

    private val onReadyToNavigate: (String) -> Unit = { id ->
        findNavController().navigate(
            FavoriteFragmentDirections.actionFFavoriteToFRecipeExtended(id)
        )
    }

    private val navigateBackCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (viewModel.isEditMode.value) {
                viewModel.setEditMode(false)
                viewModel.unselectAll()
                recyclerAdapter.notifyDataSetChanged()
            } else {
                findNavController().popBackStack()
            }
        }
    }

    private val navigateCallback = object : NavController.OnDestinationChangedListener {
        override fun onDestinationChanged(
            controller: NavController,
            destination: NavDestination,
            arguments: Bundle?
        ) {
            if (destination.id == R.id.f_favorite) {
                viewModel.setEditMode(false)
                viewModel.unselectAll()
            }
        }

    }

    override fun onStop() {
        // removing all callbacks to prevent memory leaks
        findNavController().removeOnDestinationChangedListener(navigateCallback)
        navigateBackCallback.remove()
        super.onStop()
    }


    override fun initUI() {
        with(binding) {
            btnDelete.setOnClickListener { onDeleteClickListener() }
            btnSort.setOnClickListener { onSortClickListener() }
            cbSelectAll.setOnClickListener { onSelectAllClickListener() }

            tvHeader.setOnLongClickListener {
                viewModel.setEditMode(true)
                true
            }
        }

        findNavController().addOnDestinationChangedListener(navigateCallback)
        requireActivity().onBackPressedDispatcher.addCallback(navigateBackCallback)

        recyclerAdapter = FavoriteAdapter(
            requireContext(),
            isEditMode,
            isSelected,
            onReadyToSelect,
            onReadyToNavigate,
            onHoldClickListener
        )

        with(binding.rvRecipes) {
            adapter = recyclerAdapter
            setHasFixedSize(true)
            addItemDecoration(
                FavoriteItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.favorite_item_space),
                )
            )
        }
    }

    override fun subscribeUI() {
        repeatOn(Lifecycle.State.STARTED) {
            viewModel.recipes.collectLatest(recyclerAdapter::submitData)
        }
        repeatOn(Lifecycle.State.STARTED) {
            viewModel.isEditMode.collectLatest { isSelected ->
                binding.llBaseMenu.isVisible = !isSelected
                binding.llEditMenu.isVisible = isSelected
            }
        }
        repeatOn(Lifecycle.State.STARTED) {
            viewModel.selectedCount.collectLatest { count ->
                binding.tvSelectedCount.text = getString(
                    R.string.selected_value,
                    count
                )
                binding.cbSelectAll.isChecked = count == viewModel.totalRecipesCount
            }
        }
    }
}