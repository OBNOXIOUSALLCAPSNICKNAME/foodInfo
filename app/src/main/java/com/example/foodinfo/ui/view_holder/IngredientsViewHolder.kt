package com.example.foodinfo.ui.view_holder

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodinfo.R
import com.example.foodinfo.databinding.RvItemRecipeIngredientBinding
import com.example.foodinfo.repository.model.RecipeIngredientModel
import com.example.foodinfo.ui.base.BaseViewHolder
import com.example.foodinfo.utils.glide.GlideApp


class IngredientsViewHolder(
    private val binding: RvItemRecipeIngredientBinding,
    private val onGetWeight: (Float) -> String,
    private val onGetQuantity: (Float, String) -> String,
) : BaseViewHolder<RvItemRecipeIngredientBinding, RecipeIngredientModel>(binding) {

    override fun bind(newItem: RecipeIngredientModel) {
        super.bind(newItem)
        binding.tvIngredientName.text = item.text
        binding.tvIngredientValue.text = onGetQuantity.invoke(item.quantity, item.measure)
        binding.tvIngredientWeight.text = onGetWeight.invoke(item.weight)
        GlideApp.with(binding.ivIngredientPreview.context)
            .load(item.previewURL)
            .error(R.drawable.ic_no_image)
            .placeholder(null)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.ivIngredientPreview)
    }
}