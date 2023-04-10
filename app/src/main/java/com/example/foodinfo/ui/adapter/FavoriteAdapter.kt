package com.example.foodinfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemBookmarkBinding
import com.example.foodinfo.repository.model.RecipeFavoriteModel
import com.example.foodinfo.ui.view_holder.FavoriteViewHolder
import com.example.foodinfo.utils.AppPageAdapter
import com.example.foodinfo.utils.view_model.Selectable


class FavoriteAdapter(
    private val onItemHoldListener: (RecipeFavoriteModel) -> Unit,
    private val onItemClickListener: (RecipeFavoriteModel) -> Unit
) : AppPageAdapter<Selectable<RecipeFavoriteModel>>(FavoriteViewHolder.ItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return FavoriteViewHolder(
            RvItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemHoldListener,
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { (holder as FavoriteViewHolder).bind(it) }
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