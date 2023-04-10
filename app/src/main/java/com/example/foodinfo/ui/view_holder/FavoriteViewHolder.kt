package com.example.foodinfo.ui.view_holder

import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodinfo.R
import com.example.foodinfo.databinding.RvItemBookmarkBinding
import com.example.foodinfo.repository.model.RecipeFavoriteModel
import com.example.foodinfo.ui.base.BaseViewHolder
import com.example.foodinfo.utils.glide.GlideApp
import com.example.foodinfo.utils.view_model.Selectable


class FavoriteViewHolder(
    val binding: RvItemBookmarkBinding,
    onItemHoldListener: (RecipeFavoriteModel) -> Unit,
    onItemClickListener: (RecipeFavoriteModel) -> Unit
) : BaseViewHolder<RvItemBookmarkBinding, Selectable<RecipeFavoriteModel>>(binding) {

    init {
        binding.root.apply {
            setOnClickListener {
                onItemClickListener(item.model)
            }
            setOnLongClickListener {
                onItemHoldListener(item.model)
                true
            }
        }
    }

    override fun bind(newItem: Selectable<RecipeFavoriteModel>) {
        super.bind(newItem)
        setSelected()
        binding.tvName.text = item.model.name
        binding.tvSource.text = item.model.source
        binding.tvServingsValue.text = item.model.servings
        binding.tvCaloriesValue.text = item.model.calories
        GlideApp.with(binding.ivPreview.context)
            .load(item.model.preview.toString())
            .error(R.drawable.ic_no_image)
            .placeholder(null)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.ivPreview)
    }

    override fun bind(newItem: Selectable<RecipeFavoriteModel>, payloads: List<Any>) {
        super.bind(newItem, payloads)
        setSelected()
    }


    private fun setSelected() {
        if (item.isSelected) {
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


    object ItemCallBack : DiffUtil.ItemCallback<Selectable<RecipeFavoriteModel>>() {
        override fun areItemsTheSame(
            oldItem: Selectable<RecipeFavoriteModel>,
            newItem: Selectable<RecipeFavoriteModel>
        ) =
            oldItem.model.ID == newItem.model.ID

        override fun areContentsTheSame(
            oldItem: Selectable<RecipeFavoriteModel>,
            newItem: Selectable<RecipeFavoriteModel>
        ) =
            oldItem.model.name == newItem.model.name &&
            oldItem.model.calories == newItem.model.calories &&
            oldItem.model.source == newItem.model.source &&
            oldItem.model.servings == newItem.model.servings &&
            oldItem.model.preview == newItem.model.preview &&
            oldItem.isSelected == newItem.isSelected


        override fun getChangePayload(
            oldItem: Selectable<RecipeFavoriteModel>,
            newItem: Selectable<RecipeFavoriteModel>
        ): Any? {
            if (oldItem.isSelected != newItem.isSelected) return newItem.isSelected
            return super.getChangePayload(oldItem, newItem)
        }
    }
}