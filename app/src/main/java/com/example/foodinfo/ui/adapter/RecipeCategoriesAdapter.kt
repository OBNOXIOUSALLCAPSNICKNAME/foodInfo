package com.example.foodinfo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.ItemRecipeCategoryBinding
import com.example.foodinfo.repository.model.CategoryOfRecipeModel
import com.example.foodinfo.ui.view_holder.RecipeCategoryViewHolder


class RecipeCategoriesAdapter(
    context: Context,
    private val onLabelClickListener: (Int) -> Unit
) : ListAdapter<CategoryOfRecipeModel, ViewHolder>(
    CategoryOfRecipeModel.ItemCallBack
) {

    private val layoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return RecipeCategoryViewHolder(
            layoutInflater,
            ItemRecipeCategoryBinding.inflate(layoutInflater, parent, false),
            onLabelClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { category ->
            holder as RecipeCategoryViewHolder
            holder.bind(category)
        }
    }
}