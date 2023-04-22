package com.example.foodinfo.features.search_filter.adapter

import com.example.foodinfo.databinding.RvItemBasicOfSearchFilterEditBinding
import com.example.foodinfo.features.search_filter.model.BasicEditVHModel
import com.example.foodinfo.ui.base.adapter.appAdapterDelegate


fun basicEditAdapterDelegate(
    onValueChangedCallback: (BasicEditVHModel, Float, Float) -> Unit
) = appAdapterDelegate(
    inflate = RvItemBasicOfSearchFilterEditBinding::inflate,
    onInit = { binding, itemProvider ->
        binding.root.addStopTrackingCallback { minValue, maxValue ->
            onValueChangedCallback(itemProvider(), minValue, maxValue)
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