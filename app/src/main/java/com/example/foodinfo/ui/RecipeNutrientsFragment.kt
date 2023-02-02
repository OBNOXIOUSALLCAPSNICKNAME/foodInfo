package com.example.foodinfo.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentRecipeNutrientsBinding
import com.example.foodinfo.ui.adapter.RecipeNutrientsAdapter
import com.example.foodinfo.ui.decorator.ListItemDecoration
import com.example.foodinfo.utils.appComponent
import com.example.foodinfo.utils.showDescriptionDialog
import com.example.foodinfo.view_model.RecipeNutrientsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RecipeNutrientsFragment : BaseFragment<FragmentRecipeNutrientsBinding>(
    FragmentRecipeNutrientsBinding::inflate
) {

    private val args: RecipeNutrientsFragmentArgs by navArgs()

    private lateinit var recyclerAdapter: RecipeNutrientsAdapter

    private val viewModel: RecipeNutrientsViewModel by viewModels {
        requireActivity().appComponent.viewModelsFactory()
    }

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }

    private val onGetNutrientWeight: (
        Float, Float, String
    ) -> String = { totalWeight, dailyWeight, measure ->
        getString(
            R.string.rv_item_nutrient_value,
            totalWeight,
            dailyWeight,
            measure
        )
    }

    private val onGetNutrientPercent: (Int) -> String = { dailyPercent ->
        getString(
            R.string.percent_value,
            dailyPercent
        )
    }

    private val onNutrientClickListener: (Int) -> Unit = { infoID ->
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val nutrientItem = viewModel.getNutrient(infoID)
            withContext(Dispatchers.Main) {
                showDescriptionDialog(
                    nutrientItem.label,
                    nutrientItem.description,
                    nutrientItem.preview
                )
            }
        }
    }


    override fun initUI() {
        viewModel.recipeId = args.recipeId
        binding.btnBack.setOnClickListener { onBackClickListener() }

        recyclerAdapter = RecipeNutrientsAdapter(
            requireContext(),
            onGetNutrientWeight,
            onGetNutrientPercent,
            onNutrientClickListener
        )

        with(binding.rvIngredients) {
            adapter = recyclerAdapter
            setHasFixedSize(true)
            addItemDecoration(
                ListItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.nutrients_item_space),
                    RecyclerView.VERTICAL
                )
            )
        }
    }

    override fun subscribeUI() {
        observeData(
            dataFlow = viewModel.nutrients,
            successHandlerDelegate = { nutrients ->
                recyclerAdapter.submitList(nutrients)
            }
        )
    }
}