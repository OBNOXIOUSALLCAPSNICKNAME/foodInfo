package com.example.foodinfo.ui.view_holder

import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodinfo.R
import com.example.foodinfo.databinding.RvItemSearchTargetBinding
import com.example.foodinfo.repository.model.RecipeShortModel
import com.example.foodinfo.ui.base.BaseViewHolder
import com.example.foodinfo.utils.extensions.setFavorite
import com.example.foodinfo.utils.glide.GlideApp


class SearchRecipeViewHolder(
    private val binding: RvItemSearchTargetBinding,
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
        binding.tvTimeValue.text = item.cookingTime.toString()
        binding.tvServingsValue.text = item.servings
        binding.tvCaloriesValue.text = item.calories
        GlideApp.with(binding.ivPreview.context)
            .load(item.preview.toString())
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


    object ItemCallBack : DiffUtil.ItemCallback<RecipeShortModel>() {
        override fun areItemsTheSame(
            oldItem: RecipeShortModel,
            newItem: RecipeShortModel
        ) =
            oldItem.ID == newItem.ID

        override fun areContentsTheSame(
            oldItem: RecipeShortModel,
            newItem: RecipeShortModel
        ) =
            oldItem.name == newItem.name &&
            oldItem.calories == newItem.calories &&
            oldItem.servings == newItem.servings &&
            oldItem.cookingTime == newItem.cookingTime &&
            oldItem.ingredientsCount == newItem.ingredientsCount &&
            oldItem.preview == newItem.preview &&
            oldItem.isFavorite == newItem.isFavorite


        override fun getChangePayload(
            oldItem: RecipeShortModel,
            newItem: RecipeShortModel
        ): Any? {
            if (oldItem.isFavorite != newItem.isFavorite) return newItem.isFavorite
            return super.getChangePayload(oldItem, newItem)
        }
    }
}