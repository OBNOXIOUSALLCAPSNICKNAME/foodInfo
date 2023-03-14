package com.example.foodinfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.ItemRecipeCategoryBinding
import com.example.foodinfo.repository.model.CategoryOfRecipeModel
import com.example.foodinfo.ui.view_holder.RecipeCategoryViewHolder
import com.example.foodinfo.utils.AppListAdapter


class RecipeCategoriesAdapter(
    private val onLabelClickListener: (Int) -> Unit
) : AppListAdapter<CategoryOfRecipeModel>(CategoryOfRecipeModel.ItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return RecipeCategoryViewHolder(
            LayoutInflater.from(parent.context),
            ItemRecipeCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onLabelClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { (holder as RecipeCategoryViewHolder).bind(it) }
    }
}