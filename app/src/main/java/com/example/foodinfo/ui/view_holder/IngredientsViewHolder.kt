package com.example.foodinfo.ui.view_holder

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodinfo.R
import com.example.foodinfo.databinding.RvItemRecipeIngredientBinding
import com.example.foodinfo.repository.model.IngredientOfRecipeModel
import com.example.foodinfo.ui.base.BaseViewHolder
import com.example.foodinfo.utils.glide.GlideApp


class IngredientsViewHolder(
    private val binding: RvItemRecipeIngredientBinding,
    private val onGetWeight: (Float) -> String,
) : BaseViewHolder<RvItemRecipeIngredientBinding, IngredientOfRecipeModel>(binding) {

    override fun bind(newItem: IngredientOfRecipeModel) {
        super.bind(newItem)
        binding.tvIngredientName.text = item.text
        binding.tvIngredientWeight.text = onGetWeight.invoke(item.weight)
        GlideApp.with(binding.ivIngredientPreview.context)
            .load(item.previewURL)
            .error(R.drawable.ic_no_image)
            .placeholder(null)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.ivIngredientPreview)
    }
}