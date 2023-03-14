package com.example.foodinfo.ui.fragment

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentSearchFilterNutrientsBinding
import com.example.foodinfo.ui.adapter.FilterNutrientFieldEditAdapter
import com.example.foodinfo.ui.base.DataObserverFragment
import com.example.foodinfo.ui.decorator.ListItemDecoration
import com.example.foodinfo.utils.extensions.appComponent
import com.example.foodinfo.utils.extensions.baseAnimation
import com.example.foodinfo.utils.extensions.showDescriptionDialog
import com.example.foodinfo.view_model.SearchFilterNutrientsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchFilterNutrientsFragment : DataObserverFragment<FragmentSearchFilterNutrientsBinding>(
    FragmentSearchFilterNutrientsBinding::inflate
) {

    private val viewModel: SearchFilterNutrientsViewModel by viewModels {
        requireActivity().appComponent.viewModelsFactory()
    }

    private lateinit var recyclerAdapter: FilterNutrientFieldEditAdapter

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }

    private val onResetClickListener: () -> Unit = {
        viewModel.reset()
    }

    private val onValueChangedCallback: (Int, Float?, Float?) -> Unit = { id, minValue, maxValue ->
        viewModel.update(id, minValue, maxValue)
    }

    private val onHeaderClickCallback: (Int) -> Unit = { infoID ->
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val labelItem = viewModel.getNutrient(infoID)
            withContext(Dispatchers.Main) {
                showDescriptionDialog(
                    labelItem.label,
                    labelItem.description,
                    labelItem.preview
                )
            }
        }
    }


    override fun initUI() {
        recyclerAdapter = FilterNutrientFieldEditAdapter(
            onHeaderClickCallback,
            onValueChangedCallback
        )

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
        observeData(
            dataFlow = viewModel.nutrients,
            useLoadingData = false,
            onStart = {
                binding.rvNutrients.isVisible = false
                binding.pbContent.isVisible = true
            },
            onInitUI = { nutrients ->
                recyclerAdapter.submitList(nutrients)
                binding.pbContent.isVisible = false
                binding.rvNutrients.baseAnimation()
            },
            onRefreshUI = { nutrients ->
                recyclerAdapter.submitList(nutrients)
            }
        )
    }
}
