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
import com.example.foodinfo.core.utils.extensions.appHintManager
import com.example.foodinfo.core.utils.extensions.appViewModels
import com.example.foodinfo.core.utils.extensions.baseAnimation
import com.example.foodinfo.core.utils.extensions.observeState
import com.example.foodinfo.databinding.FragmentNutrientsOfRecipeBinding
import com.example.foodinfo.features.recipe.adapter.nutrientAdapterDelegate
import com.example.foodinfo.features.recipe.model.NutrientVHModel
import com.example.foodinfo.features.recipe.view_model.NutrientsOfRecipeViewModel


class NutrientsOfRecipeFragment : BaseFragment<FragmentNutrientsOfRecipeBinding>(
    FragmentNutrientsOfRecipeBinding::inflate
) {

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }

    private val onNutrientClickListener: (NutrientVHModel) -> Unit = { nutrient ->
        hintManager.showNutrient(this) { viewModel.getNutrientHint(nutrient.infoID) }
    }


    private val args: NutrientsOfRecipeFragmentArgs by navArgs()

    private val recyclerAdapter: AppListAdapter by appListAdapter(
        nutrientAdapterDelegate(onNutrientClickListener)
    )

    private val viewModel: NutrientsOfRecipeViewModel by appViewModels()

    private val hintManager by appHintManager()


    override fun initUI() {
        viewModel.recipeID = args.recipeID
        binding.btnBack.setOnClickListener { onBackClickListener() }

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