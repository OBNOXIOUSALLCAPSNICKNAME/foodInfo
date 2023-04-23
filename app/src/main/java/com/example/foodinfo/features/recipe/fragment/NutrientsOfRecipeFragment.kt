package com.example.foodinfo.features.recipe.fragment

import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.core.ui.base.BaseFragment
import com.example.foodinfo.core.ui.base.adapter.AppListAdapter
import com.example.foodinfo.core.ui.base.adapter.appListAdapter
import com.example.foodinfo.core.utils.extensions.appViewModels
import com.example.foodinfo.core.utils.extensions.baseAnimation
import com.example.foodinfo.core.utils.extensions.observeState
import com.example.foodinfo.core.utils.extensions.showDescriptionDialog
import com.example.foodinfo.databinding.FragmentNutrientsOfRecipeBinding
import com.example.foodinfo.features.recipe.adapter.nutrientAdapterDelegate
import com.example.foodinfo.features.recipe.model.NutrientVHModel
import com.example.foodinfo.features.recipe.view_model.NutrientsOfRecipeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NutrientsOfRecipeFragment : BaseFragment<FragmentNutrientsOfRecipeBinding>(
    FragmentNutrientsOfRecipeBinding::inflate
) {

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }

    private val onNutrientClickListener: (NutrientVHModel) -> Unit = { nutrient ->
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val nutrientItem = viewModel.getNutrientHint(nutrient.infoID)
            withContext(Dispatchers.Main) {
                showDescriptionDialog(
                    nutrientItem.name,
                    nutrientItem.description,
                    nutrientItem.preview
                )
            }
        }
    }


    private val args: NutrientsOfRecipeFragmentArgs by navArgs()

    private val recyclerAdapter: AppListAdapter by appListAdapter(
        nutrientAdapterDelegate(onNutrientClickListener)
    )

    private val viewModel: NutrientsOfRecipeViewModel by appViewModels()


    override fun initUI() {
        viewModel.recipeID = args.recipeID
        binding.btnBack.setOnClickListener { onBackClickListener() }

        with(binding.rvNutrients) {
            adapter = recyclerAdapter
            setHasFixedSize(true)
            addItemDecoration(
                com.example.foodinfo.core.ui.ListItemDecoration(
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