package com.example.foodinfo.features.explore.adapter

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodinfo.R
import com.example.foodinfo.core.ui.base.adapter.appAdapterDelegate
import com.example.foodinfo.core.utils.extensions.setFavorite
import com.example.foodinfo.core.utils.glide.GlideApp
import com.example.foodinfo.databinding.RvItemRecipeExploreBinding
import com.example.foodinfo.features.explore.model.RecipeVHModel


fun recipeAdapterDelegate(
    onItemClickListener: (RecipeVHModel) -> Unit,
    onFavoriteClickListener: (RecipeVHModel) -> Unit,
) = appAdapterDelegate(
    inflate = RvItemRecipeExploreBinding::inflate,
    onInit = { binding, itemProvider ->
        with(binding) {
            clContent.setOnClickListener {
                onItemClickListener(itemProvider())
            }
            btnFavorite.setOnClickListener {
                onFavoriteClickListener(itemProvider())
            }
        }
    },
    onBind = { binding, item, payloads ->
        with(binding) {
            if (payloads.isEmpty()) {
                tvName.text = item.name
                tvTimeValue.text = item.cookingTime
                tvServingsValue.text = item.servings
                tvCaloriesValue.text = item.calories
                GlideApp.with(ivPreview.context)
                    .load(item.preview)
                    .error(R.drawable.ic_no_image)
                    .placeholder(null)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivPreview)
            }
            btnFavorite.setFavorite(item.isFavorite)
        }
    }
)