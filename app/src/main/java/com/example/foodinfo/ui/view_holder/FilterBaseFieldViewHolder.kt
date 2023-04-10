package com.example.foodinfo.ui.view_holder

import androidx.recyclerview.widget.DiffUtil
import com.example.foodinfo.databinding.RvItemFilterInputBaseFieldBinding
import com.example.foodinfo.repository.model.BasicOfSearchFilterEditModel
import com.example.foodinfo.ui.base.BaseViewHolder


class FilterBaseFieldViewHolder(
    private val binding: RvItemFilterInputBaseFieldBinding,
    onValueChangedCallback: (Int, Float?, Float?) -> Unit
) : BaseViewHolder<RvItemFilterInputBaseFieldBinding, BasicOfSearchFilterEditModel>(binding) {

    private val onValueChangedCallback: (Float, Float) -> Unit = { minValue, maxValue ->
        if (item.minValue != minValue || item.maxValue != maxValue) {
            onValueChangedCallback.invoke(
                item.ID,
                if (minValue == item.rangeMin) null else minValue,
                if (maxValue == item.rangeMax) null else maxValue
            )
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
            maxValue = if (item.maxValue != null) item.maxValue!! else item.rangeMax
            minValue = if (item.minValue != null) item.minValue!! else item.rangeMin
        }
    }


    object ItemCallBack : DiffUtil.ItemCallback<BasicOfSearchFilterEditModel>() {
        override fun areItemsTheSame(
            oldItem: BasicOfSearchFilterEditModel,
            newItem: BasicOfSearchFilterEditModel
        ) =
            oldItem.ID == newItem.ID

        override fun areContentsTheSame(
            oldItem: BasicOfSearchFilterEditModel,
            newItem: BasicOfSearchFilterEditModel
        ) =
            oldItem.infoID == newItem.infoID &&
            oldItem.name == newItem.name &&
            oldItem.measure == newItem.measure &&
            oldItem.stepSize == newItem.stepSize &&
            oldItem.rangeMin == newItem.rangeMin &&
            oldItem.rangeMax == newItem.rangeMax &&
            oldItem.minValue == newItem.minValue &&
            oldItem.maxValue == newItem.maxValue
    }
}