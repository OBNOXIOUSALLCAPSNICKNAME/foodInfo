package com.example.foodinfo.ui.view_holder

import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodinfo.R
import com.example.foodinfo.databinding.RvItemRecipeIngredientBinding
import com.example.foodinfo.domain.model.IngredientOfRecipeModel
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


    object ItemCallBack : DiffUtil.ItemCallback<IngredientOfRecipeModel>() {
        override fun areItemsTheSame(
            oldItem: IngredientOfRecipeModel,
            newItem: IngredientOfRecipeModel
        ) =
            oldItem.ID == newItem.ID

        override fun areContentsTheSame(
            oldItem: IngredientOfRecipeModel,
            newItem: IngredientOfRecipeModel
        ) =
            oldItem.text == newItem.text &&
            oldItem.measure == newItem.measure &&
            oldItem.quantity == newItem.quantity &&
            oldItem.weight == newItem.weight &&
            oldItem.previewURL == newItem.previewURL
    }
}