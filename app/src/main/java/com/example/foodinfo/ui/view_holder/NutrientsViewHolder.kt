package com.example.foodinfo.ui.view_holder

import androidx.recyclerview.widget.DiffUtil
import com.example.foodinfo.databinding.RvItemRecipeNutrientBinding
import com.example.foodinfo.domain.model.NutrientOfRecipeModel
import com.example.foodinfo.ui.base.BaseViewHolder


class NutrientsViewHolder(
    private val binding: RvItemRecipeNutrientBinding,
    private val onGetNutrientWeight: (Float, Float, String) -> String,
    private val onGetNutrientPercent: (Int) -> String,
    private val onNutrientClickListener: (Int) -> Unit,
) : BaseViewHolder<RvItemRecipeNutrientBinding, NutrientOfRecipeModel>(binding) {

    init {
        binding.clNutrient.setOnClickListener { onNutrientClickListener(item.infoID) }
    }


    override fun bind(newItem: NutrientOfRecipeModel) {
        super.bind(newItem)
        binding.tvName.text = item.name
        binding.tvWeight.text = onGetNutrientWeight.invoke(
            item.totalWeight,
            item.dailyWeight,
            item.measure
        )
        binding.tvPercent.text = onGetNutrientPercent.invoke(item.dailyPercent)
        binding.progressBar.progress = item.dailyPercent
    }


    object ItemCallBack : DiffUtil.ItemCallback<NutrientOfRecipeModel>() {
        override fun areItemsTheSame(
            oldItem: NutrientOfRecipeModel,
            newItem: NutrientOfRecipeModel
        ) =
            oldItem.ID == newItem.ID

        override fun areContentsTheSame(
            oldItem: NutrientOfRecipeModel,
            newItem: NutrientOfRecipeModel
        ) =
            oldItem.infoID == newItem.infoID &&
            oldItem.name == newItem.name &&
            oldItem.measure == newItem.measure &&
            oldItem.totalWeight == newItem.totalWeight &&
            oldItem.dailyWeight == newItem.dailyWeight &&
            oldItem.dailyPercent == newItem.dailyPercent
    }
}