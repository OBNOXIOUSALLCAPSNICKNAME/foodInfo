package com.example.foodinfo.ui.fragment

import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentRecipeNutrientsBinding
import com.example.foodinfo.ui.adapter.RecipeNutrientsAdapter
import com.example.foodinfo.ui.base.BaseFragment
import com.example.foodinfo.ui.decorator.ListItemDecoration
import com.example.foodinfo.utils.extensions.appViewModels
import com.example.foodinfo.utils.extensions.baseAnimation
import com.example.foodinfo.utils.extensions.observeState
import com.example.foodinfo.utils.extensions.showDescriptionDialog
import com.example.foodinfo.view_model.RecipeNutrientsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RecipeNutrientsFragment : BaseFragment<FragmentRecipeNutrientsBinding>(
    FragmentRecipeNutrientsBinding::inflate
) {

    private val args: RecipeNutrientsFragmentArgs by navArgs()

    private lateinit var recyclerAdapter: RecipeNutrientsAdapter

    private val viewModel: RecipeNutrientsViewModel by appViewModels()

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
            onGetNutrientWeight,
            onGetNutrientPercent,
            onNutrientClickListener
        )

        with(binding.rvNutrients) {
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
        observeState(
            dataFlow = viewModel.nutrients,
            useLoadingData = true,
            onStart = {
                binding.rvNutrients.isVisible = false
                binding.pbContent.isVisible = true
            },
            onFinish = {
                binding.pbContent.isVisible = false
                binding.rvNutrients.baseAnimation()
            },
            onSuccess = recyclerAdapter::submitList
        )
    }
}