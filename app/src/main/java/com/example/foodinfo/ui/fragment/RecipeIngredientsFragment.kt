package com.example.foodinfo.ui.fragment

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentRecipeIngredientsBinding
import com.example.foodinfo.ui.adapter.RecipeIngredientsAdapter
import com.example.foodinfo.ui.base.BaseFragment
import com.example.foodinfo.ui.decorator.ListItemDecoration
import com.example.foodinfo.utils.extensions.appViewModels
import com.example.foodinfo.utils.extensions.baseAnimation
import com.example.foodinfo.utils.extensions.observeState
import com.example.foodinfo.view_model.RecipeIngredientsViewModel


class RecipeIngredientsFragment : BaseFragment<FragmentRecipeIngredientsBinding>(
    FragmentRecipeIngredientsBinding::inflate
) {

    private val args: RecipeIngredientsFragmentArgs by navArgs()

    private lateinit var recyclerAdapter: RecipeIngredientsAdapter

    private val viewModel: RecipeIngredientsViewModel by appViewModels()

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }

    private val onGetWeight: (Float) -> String = { weight ->
        getString(R.string.gram_float_value, weight)
    }


    override fun initUI() {
        viewModel.recipeId = args.recipeId
        binding.btnBack.setOnClickListener { onBackClickListener() }

        recyclerAdapter = RecipeIngredientsAdapter(
            onGetWeight
        )

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