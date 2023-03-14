package com.example.foodinfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemBookmarkBinding
import com.example.foodinfo.repository.model.RecipeFavoriteModel
import com.example.foodinfo.ui.view_holder.FavoriteViewHolder
import com.example.foodinfo.utils.AppPageAdapter


class FavoriteAdapter(
    private val isEditMode: () -> Boolean,
    private val isSelected: (String) -> Boolean,
    private val onReadyToSelect: (String) -> Unit,
    private val onReadyToNavigate: (String) -> Unit,
    private val onHoldClickListener: (String) -> Unit
) : AppPageAdapter<RecipeFavoriteModel>(RecipeFavoriteModel.ItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return FavoriteViewHolder(
            RvItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            isSelected
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { recipe ->
            (holder as FavoriteViewHolder).also { holder ->
                holder.bind(recipe)
                holder.binding.clItem.setOnClickListener {
                    if (isEditMode.invoke()) {
                        onReadyToSelect.invoke(recipe.ID)
                        this.notifyItemChanged(holder.bindingAdapterPosition, listOf(true))
                    } else {
                        onReadyToNavigate.invoke(recipe.ID)
                    }
                }
                holder.binding.clItem.setOnLongClickListener {
                    onHoldClickListener(recipe.ID)
                    this.notifyItemChanged(holder.bindingAdapterPosition, listOf(true))
                    true
                }
            }
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            getItem(position)?.let { (holder as FavoriteViewHolder).bind(it, payloads) }
        }
    }
}