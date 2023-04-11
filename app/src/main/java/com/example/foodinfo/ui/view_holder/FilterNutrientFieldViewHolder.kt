package com.example.foodinfo.ui.view_holder

import androidx.recyclerview.widget.DiffUtil
import com.example.foodinfo.databinding.RvItemFilterInputNutrientsBinding
import com.example.foodinfo.domain.model.NutrientOfSearchFilterPreviewModel
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


    object ItemCallBack : DiffUtil.ItemCallback<NutrientOfSearchFilterPreviewModel>() {
        override fun areItemsTheSame(
            oldItem: NutrientOfSearchFilterPreviewModel,
            newItem: NutrientOfSearchFilterPreviewModel
        ) =
            oldItem.ID == newItem.ID

        override fun areContentsTheSame(
            oldItem: NutrientOfSearchFilterPreviewModel,
            newItem: NutrientOfSearchFilterPreviewModel
        ) =
            oldItem.name == newItem.name &&
            oldItem.measure == newItem.measure &&
            oldItem.minValue == newItem.minValue &&
            oldItem.maxValue == newItem.maxValue
    }
}