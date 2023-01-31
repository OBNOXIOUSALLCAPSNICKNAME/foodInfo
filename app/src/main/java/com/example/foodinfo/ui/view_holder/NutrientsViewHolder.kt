package com.example.foodinfo.ui.view_holder

import com.example.foodinfo.databinding.RvItemRecipeNutrientBinding
import com.example.foodinfo.repository.model.NutrientOfRecipeModel


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
}