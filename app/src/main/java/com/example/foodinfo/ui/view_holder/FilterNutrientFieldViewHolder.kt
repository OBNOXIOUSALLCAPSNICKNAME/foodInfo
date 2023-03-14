package com.example.foodinfo.ui.view_holder

import com.example.foodinfo.databinding.RvItemFilterInputNutrientsBinding
import com.example.foodinfo.repository.model.NutrientOfSearchFilterPreviewModel
import com.example.foodinfo.ui.base.BaseViewHolder


class FilterNutrientFieldViewHolder(
    private val binding: RvItemFilterInputNutrientsBinding,
    private val getFormattedRange: (Float?, Float?, String) -> String
) : BaseViewHolder<RvItemFilterInputNutrientsBinding, NutrientOfSearchFilterPreviewModel>(binding) {


    override fun bind(newItem: NutrientOfSearchFilterPreviewModel) {
        super.bind(newItem)
        with(binding) {
            tvHeader.text = item.name
            tvRange.text = getFormattedRange(
                item.minValue,
                item.maxValue,
                item.measure
            )
        }
    }
}