package com.example.foodinfo.ui.view_holder

import com.example.foodinfo.databinding.RvItemFilterInputBaseFieldBinding
import com.example.foodinfo.repository.model.BasicOfSearchFilterEditModel


class FilterBaseFieldViewHolder(
    private val binding: RvItemFilterInputBaseFieldBinding,
    onValueChangedCallback: (Int, Float, Float) -> Unit
) : BaseViewHolder<RvItemFilterInputBaseFieldBinding, BasicOfSearchFilterEditModel>(binding) {

    private val onValueChangedCallback: (Float, Float) -> Unit = { minValue, maxValue ->
        if (item.minValue != minValue || item.maxValue != maxValue) {
            onValueChangedCallback.invoke(item.ID, minValue, maxValue)
        }
    }

    init {
        binding.root.addStopTrackingCallback(this.onValueChangedCallback)
    }


    override fun bind(newItem: BasicOfSearchFilterEditModel) {
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