package com.example.foodinfo.features.favorite.adapter

import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodinfo.R
import com.example.foodinfo.core.ui.base.adapter.appAdapterDelegate
import com.example.foodinfo.core.utils.glide.GlideApp
import com.example.foodinfo.databinding.RvItemRecipeFavoriteBinding
import com.example.foodinfo.features.favorite.model.RecipeVHModel


fun recipeAdapterDelegate(
    onItemHoldListener: (RecipeVHModel) -> Unit,
    onItemClickListener: (RecipeVHModel) -> Unit
) = appAdapterDelegate(
    inflate = RvItemRecipeFavoriteBinding::inflate,
    onInit = { binding, itemProvider ->
        binding.root.apply {
            setOnClickListener {
                onItemClickListener(itemProvider())
            }
            setOnLongClickListener {
                onItemHoldListener(itemProvider())
                true
            }
        }
    },
    onBind = { binding, item, payloads ->
        if (payloads.isEmpty()) {
            with(binding) {
                tvName.text = item.name
                tvSource.text = item.source
                tvServingsValue.text = item.servings
                tvCaloriesValue.text = item.calories
                GlideApp
                    .with(ivPreview.context)
                    .load(item.preview)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivPreview)
            }
        }
        binding.clItem.background = if (item.isSelected) {
            AppCompatResources.getDrawable(
                binding.clItem.context,
                R.drawable.background_item_favorite_selected
            )
        } else {
            AppCompatResources.getDrawable(
                binding.clItem.context,
                R.drawable.background_item_favorite_unselected
            )
        }
    }
)