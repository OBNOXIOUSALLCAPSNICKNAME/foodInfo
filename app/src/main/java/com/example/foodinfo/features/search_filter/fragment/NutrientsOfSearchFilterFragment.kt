package com.example.foodinfo.features.search_filter.fragment

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.example.foodinfo.databinding.FragmentNutrientsOfSearchFilterBinding
import com.example.foodinfo.features.search_filter.adapter.nutrientEditAdapterDelegate
import com.example.foodinfo.features.search_filter.model.NutrientEditVHModel
import com.example.foodinfo.features.search_filter.view_model.NutrientsOfSearchFilterViewModel


class NutrientsOfSearchFilterFragment : BaseFragment<FragmentNutrientsOfSearchFilterBinding>(
    FragmentNutrientsOfSearchFilterBinding::inflate
) {

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }

    private val onResetClickListener: () -> Unit = {
        viewModel.reset()
    }

    private val onValueChangedCallback: (NutrientEditVHModel, Float, Float) -> Unit =
        { nutrient, minValue, maxValue ->
            if (nutrient.minValue != minValue || nutrient.maxValue != maxValue) {
                viewModel.updateNutrient(
                    nutrient.ID,
                    if (minValue == nutrient.rangeMin) null else minValue,
                    if (maxValue == nutrient.rangeMax) null else maxValue
                )
            }
        }

    private val onHeaderClickCallback: (NutrientEditVHModel) -> Unit = { nutrient ->
        hintManager.showNutrient(this) { viewModel.getNutrientHint(nutrient.infoID) }
    }


    private val viewModel: NutrientsOfSearchFilterViewModel by appViewModels()

    private val recyclerAdapter: AppListAdapter by appListAdapter(
        nutrientEditAdapterDelegate(onHeaderClickCallback, onValueChangedCallback)
    )

    private val hintManager by appHintManager()


    override fun initUI() {
        binding.btnBack.setOnClickListener { onBackClickListener() }
        binding.btnReset.setOnClickListener { onResetClickListener() }

        with(binding.rvNutrients) {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context).also {
                it.orientation = LinearLayoutManager.VERTICAL
            }
            itemAnimator = null
            addItemDecoration(
                ListItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.filter_nutrients_edit_field_item_space),
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
