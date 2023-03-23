package com.example.foodinfo.ui.fragment

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentRecipeIngredientsBinding
import com.example.foodinfo.ui.adapter.RecipeIngredientsAdapter
import com.example.foodinfo.ui.base.DataObserverFragment
import com.example.foodinfo.ui.decorator.ListItemDecoration
import com.example.foodinfo.utils.extensions.appComponent
import com.example.foodinfo.utils.extensions.baseAnimation
import com.example.foodinfo.utils.extensions.measureSpacer
import com.example.foodinfo.view_model.RecipeIngredientsViewModel


class RecipeIngredientsFragment : DataObserverFragment<FragmentRecipeIngredientsBinding>(
    FragmentRecipeIngredientsBinding::inflate
) {

    private val args: RecipeIngredientsFragmentArgs by navArgs()

    private lateinit var recyclerAdapter: RecipeIngredientsAdapter

    private val viewModel: RecipeIngredientsViewModel by viewModels {
        requireActivity().appComponent.viewModelsFactory()
    }

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }

    private val onGetWeight: (Float) -> String = { weight ->
        getString(R.string.gram_float_value, weight)
    }

    private val onGetQuantity: (Float, String) -> String = { quantity, measure ->
        getString(
            R.string.float_measure_value,
            quantity,
            measure.measureSpacer,
            measure
        )
    }


    override fun initUI() {
        viewModel.recipeId = args.recipeId
        binding.btnBack.setOnClickListener { onBackClickListener() }

        recyclerAdapter = RecipeIngredientsAdapter(
            onGetWeight,
            onGetQuantity
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
        observeData(
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