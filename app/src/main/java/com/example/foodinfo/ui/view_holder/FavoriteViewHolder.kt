package com.example.foodinfo.ui.view_holder

import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodinfo.R
import com.example.foodinfo.databinding.RvItemBookmarkBinding
import com.example.foodinfo.repository.model.RecipeFavoriteModel
import com.example.foodinfo.ui.base.BaseViewHolder
import com.example.foodinfo.utils.glide.GlideApp


class FavoriteViewHolder(
    val binding: RvItemBookmarkBinding,
    private val isSelected: (String) -> Boolean
) : BaseViewHolder<RvItemBookmarkBinding, RecipeFavoriteModel>(binding) {

    override fun bind(newItem: RecipeFavoriteModel) {
        super.bind(newItem)
        setSelected()
        binding.tvName.text = item.name
        binding.tvSource.text = item.source
        binding.tvServingsValue.text = item.servings
        binding.tvCaloriesValue.text = item.calories
        GlideApp.with(binding.ivPreview.context)
            .load(item.preview.toString())
            .error(R.drawable.ic_no_image)
            .placeholder(null)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.ivPreview)
    }

    override fun bind(newItem: RecipeFavoriteModel, payloads: List<Any>) {
        super.bind(newItem, payloads)
        setSelected()
    }


    private fun setSelected() {
        if (isSelected.invoke(item.ID)) {
            binding.clItem.background = AppCompatResources.getDrawable(
                binding.clItem.context,
                R.drawable.background_item_favorite_selected
            )
        } else {
            binding.clItem.background = AppCompatResources.getDrawable(
                binding.clItem.context,
                R.drawable.background_item_favorite_unselected
            )
        }
    }
}