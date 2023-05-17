package com.example.foodinfo.features.search_filter.fragment

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.core.ui.ListItemDecoration
import com.example.foodinfo.core.ui.NonScrollLinearLayoutManager
import com.example.foodinfo.core.ui.base.BaseFragment
import com.example.foodinfo.core.ui.base.adapter.AppListAdapter
import com.example.foodinfo.core.ui.base.adapter.appListAdapter
import com.example.foodinfo.core.utils.extensions.appViewModels
import com.example.foodinfo.core.utils.extensions.baseAnimation
import com.example.foodinfo.core.utils.extensions.observeState
import com.example.foodinfo.databinding.FragmentSearchFilterBinding
import com.example.foodinfo.features.search_filter.adapter.basicEditAdapterDelegate
import com.example.foodinfo.features.search_filter.adapter.categoryPreviewAdapterDelegate
import com.example.foodinfo.features.search_filter.adapter.nutrientPreviewAdapterDelegate
import com.example.foodinfo.features.search_filter.model.BasicEditVHModel
import com.example.foodinfo.features.search_filter.model.CategoryPreviewVHModel
import com.example.foodinfo.features.search_filter.model.SearchFilterModel
import com.example.foodinfo.features.search_filter.view_model.SearchFilterViewModel


class SearchFilterFragment : BaseFragment<FragmentSearchFilterBinding>(
    FragmentSearchFilterBinding::inflate
) {

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }

    private val onValueChangedCallback: (BasicEditVHModel, Float, Float) -> Unit =
        { basic, minValue, maxValue ->
            if (basic.minValue != minValue || basic.maxValue != maxValue) {
                viewModel.updateBasic(
                    basic.ID,
                    if (minValue == basic.rangeMin) null else minValue,
                    if (maxValue == basic.rangeMax) null else maxValue
                )
            }
        }

    private val onNutrientsEditClickListener: () -> Unit = {
        findNavController().navigate(
            SearchFilterFragmentDirections.actionFSearchFilterToFSearchFilterNutrients()
        )
    }

    private val onCategorySelectedCallback: (CategoryPreviewVHModel) -> Unit = { category ->
        findNavController().navigate(
            SearchFilterFragmentDirections.actionFSearchFilterToFSearchFilterCategory(
                category.ID, category.name
            )
        )
    }


    private val viewModel: SearchFilterViewModel by appViewModels()

    private val basicsRecyclerAdapter: AppListAdapter by appListAdapter(
        basicEditAdapterDelegate(onValueChangedCallback)
    )
    private val categoriesRecyclerAdapter: AppListAdapter by appListAdapter(
        categoryPreviewAdapterDelegate(onCategorySelectedCallback)
    )
    private val nutrientsRecyclerAdapter: AppListAdapter by appListAdapter(
        nutrientPreviewAdapterDelegate()
    )


    override fun initUI() {
        binding.btnBack.setOnClickListener { onBackClickListener() }
        binding.btnReset.setOnClickListener { viewModel.reset() }
        binding.ivNutrientsEdit.setOnClickListener {
            onNutrientsEditClickListener()
        }

        with(binding.rvBaseFields) {
            adapter = basicsRecyclerAdapter
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

        with(binding.rvCategories) {
            adapter = categoriesRecyclerAdapter
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

        with(binding.rvNutrients) {
            adapter = nutrientsRecyclerAdapter
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
        observeState(
            dataFlow = viewModel.filter,
            useLoadingData = true,
            onStart = {
                binding.svContent.isVisible = false
                binding.pbContent.isVisible = true
            },
            onFinish = {
                binding.pbContent.isVisible = false
                binding.svContent.baseAnimation()
            },
            onSuccess = ::initFilter
        )
    }

    private fun initFilter(filter: SearchFilterModel) {
        binding.tvFilterName.text = filter.name
        categoriesRecyclerAdapter.submitList(filter.categories)
        basicsRecyclerAdapter.submitList(filter.basics)
        if (filter.nutrients.isEmpty()) {
            binding.rvNutrients.isVisible = false
            binding.tvNutrientsNoData.isVisible = true
        } else {
            binding.rvNutrients.isVisible = true
            binding.tvNutrientsNoData.isVisible = false
            nutrientsRecyclerAdapter.submitList(filter.nutrients)
        }
    }
}