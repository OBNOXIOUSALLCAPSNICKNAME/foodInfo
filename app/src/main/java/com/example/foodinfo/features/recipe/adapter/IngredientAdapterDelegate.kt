package com.example.foodinfo.features.recipe.adapter

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodinfo.R
import com.example.foodinfo.core.ui.base.adapter.appAdapterDelegate
import com.example.foodinfo.core.utils.glide.GlideApp
import com.example.foodinfo.databinding.RvItemIngredientOfRecipeBinding
import com.example.foodinfo.features.recipe.model.IngredientVHModel


fun ingredientAdapterDelegate() = appAdapterDelegate<IngredientVHModel, RvItemIngredientOfRecipeBinding>(
    inflate = RvItemIngredientOfRecipeBinding::inflate,
    onBind = { binding, item, _ ->
        binding.tvIngredientName.text = item.text
        binding.tvIngredientWeight.text = item.weight
        GlideApp.with(binding.ivIngredientPreview.context)
            .load(item.previewURL)
            .error(R.drawable.ic_no_image)
            .placeholder(null)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.ivIngredientPreview)
    }
)