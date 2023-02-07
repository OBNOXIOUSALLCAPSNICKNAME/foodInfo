package com.example.foodinfo.ui.view_holder

import com.example.foodinfo.databinding.RvItemFilterInputNutrientsEditBinding
import com.example.foodinfo.repository.model.NutrientOfSearchFilterEditModel


class FilterNutrientFieldEditViewHolder(
    private val binding: RvItemFilterInputNutrientsEditBinding,
    onHeaderClickCallback: (Int) -> Unit
) : BaseViewHolder<RvItemFilterInputNutrientsEditBinding, NutrientOfSearchFilterEditModel>(binding) {

    private val onValueChangedCallback: (Float, Float) -> Unit = { minValue, maxValue ->
        item.minValue = minValue
        item.maxValue = maxValue
    }

    private val onHeaderClickCallback: () -> Unit = {
        onHeaderClickCallback.invoke(item.infoID)
    }

    init {
        binding.root.addStopTrackingCallback(this.onValueChangedCallback)
        binding.root.addHeaderClickCallback(this.onHeaderClickCallback)
    }


    override fun bind(newItem: NutrientOfSearchFilterEditModel) {
        super.bind(newItem)
        with(binding.root) {
            header = item.name
            measure = item.measure
            rangeMin = item.rangeMin
            rangeMax = item.rangeMax
            stepSize = item.stepSize
            maxValue = item.maxValue
            minValue = item.minValue
        }
    }
}