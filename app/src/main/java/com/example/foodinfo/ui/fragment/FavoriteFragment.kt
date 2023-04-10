package com.example.foodinfo.ui.fragment

import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentFavoriteBinding
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
        viewModel.delRecipesFromFavorite()
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

    private val navigateCallback = NavController.OnDestinationChangedListener { _, destination, _ ->
        if (destination.id == R.id.f_favorite) {
            viewModel.setEditMode(false)
            viewModel.unselectAll()
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
            btnDelete.setOnClickListener { onDeleteClickListener() }
            btnSort.setOnClickListener { onSortClickListener() }
            cbSelectAll.setOnClickListener { onSelectAllClickListener() }
        }

        findNavController().addOnDestinationChangedListener(navigateCallback)
        requireActivity().onBackPressedDispatcher.addCallback(navigateBackCallback)

        recyclerAdapter = FavoriteAdapter(
            viewModel.isEditMode::value,
            viewModel::isSelected,
            onReadyToSelect,
            onReadyToNavigate,
            onHoldClickListener
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
            viewModel.recipes.collectLatest(recyclerAdapter::submitData)
        }
        observe(Lifecycle.State.STARTED) {
            viewModel.isEditMode.collectLatest { isSelected ->
                binding.llBaseMenu.isVisible = !isSelected
                binding.llEditMenu.isVisible = isSelected
            }
        }
        observe(Lifecycle.State.STARTED) {
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