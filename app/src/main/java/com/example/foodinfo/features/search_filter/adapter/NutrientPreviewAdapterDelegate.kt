package com.example.foodinfo.features.search_filter.adapter

import com.example.foodinfo.databinding.RvItemNutrientOfSearchFilterPreviewBinding
import com.example.foodinfo.features.search_filter.model.NutrientPreviewVHModel
import com.example.foodinfo.ui.base.adapter.appAdapterDelegate


fun nutrientPreviewAdapterDelegate(
) = appAdapterDelegate<NutrientPreviewVHModel, RvItemNutrientOfSearchFilterPreviewBinding>(
    inflate = RvItemNutrientOfSearchFilterPreviewBinding::inflate,
    onBind = { binding, item, _ ->
        with(binding) {
            tvHeader.text = item.name
            tvRange.text = item.range
        }
    }
)