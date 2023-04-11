package com.example.foodinfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemSearchTargetBinding
import com.example.foodinfo.databinding.RvItemSearchTargetPlaceholderBinding
import com.example.foodinfo.domain.model.RecipeShortModel
import com.example.foodinfo.ui.view_holder.SearchRecipePlaceholder
import com.example.foodinfo.ui.view_holder.SearchRecipeViewHolder
import com.example.foodinfo.utils.AppPageAdapter


class SearchRecipeAdapter(
    private val onItemClickListener: (String) -> Unit,
    private val onFavoriteClickListener: (String) -> Unit,
) : AppPageAdapter<RecipeShortModel>(SearchRecipeViewHolder.ItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ViewTypes.LOADED_VIEW.ordinal -> {
                SearchRecipeViewHolder(
                    RvItemSearchTargetBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                    onItemClickListener,
                    onFavoriteClickListener
                )
            }
            else                          -> {
                SearchRecipePlaceholder(
                    RvItemSearchTargetPlaceholderBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { (holder as SearchRecipeViewHolder).bind(it) }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            getItem(position)?.let { (holder as SearchRecipeViewHolder).bind(it, payloads) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        getItem(position) ?: return ViewTypes.PROGRESS_VIEW.ordinal
        return ViewTypes.LOADED_VIEW.ordinal
    }


    enum class ViewTypes {
        PROGRESS_VIEW,
        LOADED_VIEW
    }
}