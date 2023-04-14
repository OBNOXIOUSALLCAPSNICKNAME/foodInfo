package com.example.foodinfo.ui.base.adapter

import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodinfo.R
import com.example.foodinfo.databinding.RvItemBookmarkBinding
import com.example.foodinfo.utils.edamam.EdamamImageURL
import com.example.foodinfo.utils.glide.GlideApp


interface Selectable<Key> {
    val ID: Key
    var isSelected: Boolean
}

data class RecipeFavoriteVHModel(
    override val ID: String,
    override var isSelected: Boolean = false,
    val name: String,
    val calories: String,
    val source: String,
    val servings: String,
    val preview: EdamamImageURL
) : Selectable<String>, AppViewHolderModel {

    override fun areItemsTheSame(other: AppViewHolderModel): Boolean =
        other is RecipeFavoriteVHModel &&
        this.ID == other.ID

    override fun areContentsTheSame(other: AppViewHolderModel): Boolean =
        other is RecipeFavoriteVHModel &&
        this.name == other.name &&
        this.calories == other.calories &&
        this.source == other.source &&
        this.servings == other.servings &&
        this.preview == other.preview &&
        this.isSelected == other.isSelected

    override fun getChangePayload(other: AppViewHolderModel): Any? {
        other as RecipeFavoriteVHModel
        return if (this.isSelected != other.isSelected) other.isSelected else null
    }
}


fun recipeFavoriteAdapterDelegate(
    onItemHoldListener: (RecipeFavoriteVHModel) -> Unit,
    onItemClickListener: (RecipeFavoriteVHModel) -> Unit
) = appAdapterDelegate<RecipeFavoriteVHModel, RvItemBookmarkBinding>(
    inflate = RvItemBookmarkBinding::inflate,
    onInit = { binding, item ->
        binding.root.apply {
            setOnClickListener {
                onItemClickListener(item)
            }
            setOnLongClickListener {
                onItemHoldListener(item)
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
                    .load(item.preview.toString())
                    .error(R.drawable.ic_no_image)
                    .placeholder(null)
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

val recipeFavoriteAdapter: AppListAdapter by appListAdapter(
    placeholderAdapterDelegate(RvItemBookmarkBinding::inflate),
    recipeFavoriteAdapterDelegate(
        onItemHoldListener = { throw NotImplementedError() },
        onItemClickListener = { throw NotImplementedError() },
    )
)