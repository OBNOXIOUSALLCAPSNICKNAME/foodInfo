package com.example.foodinfo.ui.fragment

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentSearchFilterBinding
import com.example.foodinfo.repository.model.SearchFilterEditModel
import com.example.foodinfo.ui.adapter.FilterBaseFieldAdapter
import com.example.foodinfo.ui.adapter.FilterCategoriesAdapter
import com.example.foodinfo.ui.adapter.FilterNutrientsAdapter
import com.example.foodinfo.ui.base.DataObserverFragment
import com.example.foodinfo.ui.custom_view.NonScrollLinearLayoutManager
import com.example.foodinfo.ui.decorator.ListItemDecoration
import com.example.foodinfo.utils.extensions.appComponent
import com.example.foodinfo.utils.extensions.baseAnimation
import com.example.foodinfo.view_model.SearchFilterViewModel


class SearchFilterFragment : DataObserverFragment<FragmentSearchFilterBinding>(
    FragmentSearchFilterBinding::inflate
) {

    private val viewModel: SearchFilterViewModel by viewModels {
        requireActivity().appComponent.viewModelsFactory()
    }

    private lateinit var recyclerAdapterBaseFields: FilterBaseFieldAdapter
    private lateinit var recyclerAdapterCategories: FilterCategoriesAdapter
    private lateinit var recyclerAdapterNutrients: FilterNutrientsAdapter

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }

    private val onResetClickListener: () -> Unit = {
        viewModel.reset()
    }

    private val onValueChangedCallback: (Int, Float?, Float?) -> Unit = { id, minValue, maxValue ->
        viewModel.update(id, minValue, maxValue)
    }

    private val onNutrientsEditClickListener: () -> Unit = {
        findNavController().navigate(
            SearchFilterFragmentDirections.actionFSearchFilterToFSearchFilterNutrients()
        )
    }


    private val onCategoryChangedCallback: (Int) -> Unit = { categoryID ->
        findNavController().navigate(
            SearchFilterFragmentDirections.actionFSearchFilterToFSearchFilterCategory(categoryID)
        )
    }

    private val getFormattedRange: (Float?, Float?, String) -> String =
        { minValue, maxValue, measure ->
            when {
                minValue != null && maxValue != null -> {
                    "$minValue$measure - $maxValue$measure"
                }
                minValue != null                     -> {
                    "≥$minValue$measure"
                }
                maxValue != null                     -> {
                    "≤$maxValue$measure"
                }
                else                                 -> {
                    ""
                }
            }
        }


    override fun initUI() {
        binding.btnBack.setOnClickListener { onBackClickListener() }
        binding.btnReset.setOnClickListener { onResetClickListener() }
        binding.ivNutrientsEdit.setOnClickListener {
            onNutrientsEditClickListener()
        }

        recyclerAdapterBaseFields = FilterBaseFieldAdapter(
            onValueChangedCallback
        )
        with(binding.rvBaseFields) {
            adapter = recyclerAdapterBaseFields
            layoutManager = NonScrollLinearLayoutManager(context).also {
                it.orientation = LinearLayoutManager.VERTICAL
            }
            itemAnimator = null
            addItemDecoration(
                ListItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.filter_base_range_field_item_space),
                    RecyclerView.VERTICAL
                )

            )
        }

        recyclerAdapterCategories = FilterCategoriesAdapter(
            onCategoryChangedCallback
        )
        with(binding.rvCategories) {
            adapter = recyclerAdapterCategories
            layoutManager = NonScrollLinearLayoutManager(context).also {
                it.orientation = LinearLayoutManager.VERTICAL
            }
            itemAnimator = null
            addItemDecoration(
                ListItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.filter_category_item_space),
                    RecyclerView.VERTICAL
                )
            )
        }

        recyclerAdapterNutrients = FilterNutrientsAdapter(
            getFormattedRange
        )
        with(binding.rvNutrients) {
            adapter = recyclerAdapterNutrients
            layoutManager = NonScrollLinearLayoutManager(context).also {
                it.orientation = LinearLayoutManager.VERTICAL
            }
            itemAnimator = null
            addItemDecoration(
                ListItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.filter_category_item_space),
                    RecyclerView.VERTICAL
                )
            )
        }
    }

    override fun subscribeUI() {
        observeData(
            dataFlow = viewModel.filter,
            useLoadingData = false,
            onStart = {
                binding.svContent.isVisible = false
                binding.pbContent.isVisible = true
            },
            onInitUI = { filter ->
                initFilter(filter)
                binding.pbContent.isVisible = false
                binding.svContent.baseAnimation()
            },
            onRefreshUI = { filter ->
                initFilter(filter)
            }
        )
    }

    private fun initFilter(filter: SearchFilterEditModel) {
        binding.tvFilterName.text = filter.name
        recyclerAdapterCategories.submitList(filter.categories)
        recyclerAdapterBaseFields.submitList(filter.basics)
        if (filter.nutrients.isEmpty()) {
            binding.rvNutrients.isVisible = false
            binding.tvNutrientsNoData.isVisible = true
        } else {
            binding.rvNutrients.isVisible = true
            binding.tvNutrientsNoData.isVisible = false
            recyclerAdapterNutrients.submitList(filter.nutrients)
        }
    }
}