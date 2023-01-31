package com.example.foodinfo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemCategoryBinding
import com.example.foodinfo.repository.model.CategorySearchModel
import com.example.foodinfo.ui.view_holder.HomeCategoriesViewHolder


class HomeCategoriesAdapter(
    context: Context,
    private val onItemClickListener: (Int) -> Unit
) : ListAdapter<CategorySearchModel, ViewHolder>(CategorySearchModel.ItemCallBack) {

    private val layoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return HomeCategoriesViewHolder(
            RvItemCategoryBinding.inflate(layoutInflater, parent, false),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { category ->
            holder as HomeCategoriesViewHolder
            holder.bind(category)
        }
    }
}