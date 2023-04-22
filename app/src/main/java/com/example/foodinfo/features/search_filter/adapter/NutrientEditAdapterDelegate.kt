package com.example.foodinfo.features.search_filter.adapter

import com.example.foodinfo.databinding.RvItemNutrientOfSearchFilterEditBinding
import com.example.foodinfo.features.search_filter.model.NutrientEditVHModel
import com.example.foodinfo.ui.base.adapter.appAdapterDelegate


fun nutrientEditAdapterDelegate(
    onHeaderClickCallback: (NutrientEditVHModel) -> Unit,
    onValueChangedCallback: (NutrientEditVHModel, Float, Float) -> Unit
) = appAdapterDelegate(
    inflate = RvItemNutrientOfSearchFilterEditBinding::inflate,
    onInit = { binding, itemProvider ->
        binding.root.addStopTrackingCallback { minValue, maxValue ->
            onValueChangedCallback(itemProvider(), minValue, maxValue)
        }
        binding.root.addHeaderClickCallback {
            onHeaderClickCallback(itemProvider())
        }
    },
    onBind = { binding, item, _ ->
        with(binding.root) {
            header = item.name
            measure = item.measure
            rangeMin = item.rangeMin
            rangeMax = item.rangeMax
            stepSize = item.stepSize
            maxValue = item.maxValue ?: item.rangeMax
            minValue = item.minValue ?: item.rangeMin
        }
    }
)