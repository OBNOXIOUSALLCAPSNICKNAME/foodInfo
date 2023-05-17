package com.example.foodinfo.features.recipe.fragment

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.core.ui.ListItemDecoration
import com.example.foodinfo.core.ui.base.BaseFragment
import com.example.foodinfo.core.ui.base.adapter.AppListAdapter
import com.example.foodinfo.core.ui.base.adapter.appListAdapter
import com.example.foodinfo.core.utils.extensions.appViewModels
import com.example.foodinfo.core.utils.extensions.baseAnimation
import com.example.foodinfo.core.utils.extensions.observeState
import com.example.foodinfo.databinding.FragmentIngredientsOfRecipeBinding
import com.example.foodinfo.features.recipe.adapter.ingredientAdapterDelegate
import com.example.foodinfo.features.recipe.view_model.IngredientsOfRecipeViewModel


class IngredientsOfRecipeFragment : BaseFragment<FragmentIngredientsOfRecipeBinding>(
    FragmentIngredientsOfRecipeBinding::inflate
) {

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }


    private val args: IngredientsOfRecipeFragmentArgs by navArgs()

    private val recyclerAdapter: AppListAdapter by appListAdapter(
        ingredientAdapterDelegate()
    )

    private val viewModel: IngredientsOfRecipeViewModel by appViewModels()


    override fun initUI() {
        viewModel.recipeID = args.recipeID
        binding.btnBack.setOnClickListener { onBackClickListener() }

        with(binding.rvIngredients) {
            adapter = recyclerAdapter
            setHasFixedSize(true)
            addItemDecoration(
                ListItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.ingredients_item_space),
                    RecyclerView.VERTICAL
                )
            )
        }
    }

    override fun subscribeUI() {
        observeState(
            dataFlow = viewModel.ingredients,
            useLoadingData = true,
            onStart = {
                binding.rvIngredients.isVisible = false
                binding.pbContent.isVisible = true
            },
            onFinish = {
                binding.pbContent.isVisible = false
                binding.rvIngredients.baseAnimation()
            },
            onSuccess = recyclerAdapter::submitList
        )
    }
}