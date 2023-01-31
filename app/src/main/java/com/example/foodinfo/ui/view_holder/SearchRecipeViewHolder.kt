package com.example.foodinfo.ui.view_holder

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodinfo.R
import com.example.foodinfo.databinding.RvItemSearchTargetBinding
import com.example.foodinfo.repository.model.RecipeShortModel
import com.example.foodinfo.utils.glide.GlideApp
import com.example.foodinfo.utils.setFavorite


class SearchRecipeViewHolder(
    private val binding: RvItemSearchTargetBinding,
    private val onGetTime: (Int) -> String,
    onItemClickListener: (String) -> Unit,
    onFavoriteClickListener: (String) -> Unit,
) : BaseViewHolder<RvItemSearchTargetBinding, RecipeShortModel>(binding) {

    init {
        binding.clContent.setOnClickListener {
            onItemClickListener(item.ID)
        }

        binding.btnFavorite.setOnClickListener {
            onFavoriteClickListener(item.ID)
        }
    }


    override fun bind(newItem: RecipeShortModel) {
        super.bind(newItem)
        binding.tvName.text = item.name
        binding.tvTimeValue.text = onGetTime.invoke(item.cookingTime)
        binding.tvServingsValue.text = item.servings
        binding.tvCaloriesValue.text = item.calories
        GlideApp.with(binding.ivPreview.context)
            .load(item.previewURL)
            .error(R.drawable.ic_no_image)
            .placeholder(null)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.ivPreview)
        binding.btnFavorite.setFavorite(item.isFavorite)
    }

    override fun bind(newItem: RecipeShortModel, payloads: List<Any>) {
        super.bind(newItem, payloads)
        binding.btnFavorite.setFavorite(item.isFavorite)
    }
}