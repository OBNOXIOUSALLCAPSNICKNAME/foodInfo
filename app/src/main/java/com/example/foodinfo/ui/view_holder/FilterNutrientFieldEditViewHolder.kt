package com.example.foodinfo.ui.view_holder

import com.example.foodinfo.databinding.RvItemFilterInputNutrientsEditBinding
import com.example.foodinfo.repository.model.NutrientOfSearchFilterEditModel
import com.example.foodinfo.ui.base.BaseViewHolder


class FilterNutrientFieldEditViewHolder(
    private val binding: RvItemFilterInputNutrientsEditBinding,
    onHeaderClickCallback: (Int) -> Unit,
    onValueChangedCallback: (Int, Float?, Float?) -> Unit
) : BaseViewHolder<RvItemFilterInputNutrientsEditBinding, NutrientOfSearchFilterEditModel>(binding) {

    private val onValueChangedCallback: (Float, Float) -> Unit = { minValue, maxValue ->
        if (item.minValue != minValue || item.maxValue != maxValue) {
            onValueChangedCallback.invoke(
                item.ID,
                if (minValue == item.rangeMin) null else minValue,
                if (maxValue == item.rangeMax) null else maxValue
            )
        }
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
            maxValue = if (item.maxValue != null) item.maxValue!! else item.rangeMax
            minValue = if (item.minValue != null) item.minValue!! else item.rangeMin
        }
    }
}