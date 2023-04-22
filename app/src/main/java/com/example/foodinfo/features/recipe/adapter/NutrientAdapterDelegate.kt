package com.example.foodinfo.features.recipe.adapter

import com.example.foodinfo.databinding.RvItemRecipeNutrientBinding
import com.example.foodinfo.features.recipe.model.NutrientVHModel
import com.example.foodinfo.ui.base.adapter.appAdapterDelegate


fun nutrientAdapterDelegate(
    onNutrientClickListener: (NutrientVHModel) -> Unit
) = appAdapterDelegate(
    inflate = RvItemRecipeNutrientBinding::inflate,
    onInit = { binding, itemProvider ->
        binding.clNutrient.setOnClickListener { onNutrientClickListener(itemProvider()) }
    },
    onBind = { binding, item, _ ->
        binding.tvName.text = item.name
        binding.tvWeight.text = item.weight
        binding.tvPercent.text = item.dailyPercent
        binding.progressBar.progress = item.dailyPercentValue
    }
)