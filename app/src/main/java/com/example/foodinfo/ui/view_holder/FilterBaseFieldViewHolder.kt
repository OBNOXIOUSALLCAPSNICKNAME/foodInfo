package com.example.foodinfo.ui.view_holder

import com.example.foodinfo.databinding.RvItemFilterInputBaseFieldBinding
import com.example.foodinfo.repository.model.BasicOfSearchFilterEditModel


class FilterBaseFieldViewHolder(
    private val binding: RvItemFilterInputBaseFieldBinding
) : BaseViewHolder<RvItemFilterInputBaseFieldBinding, BasicOfSearchFilterEditModel>(binding) {

    private val onValueChangedCallback: (Float, Float) -> Unit = { minValue, maxValue ->
        item.maxValue = maxValue
        item.minValue = minValue
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