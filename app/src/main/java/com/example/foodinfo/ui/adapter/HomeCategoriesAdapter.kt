package com.example.foodinfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemCategoryBinding
import com.example.foodinfo.repository.model.CategorySearchModel
import com.example.foodinfo.ui.view_holder.HomeCategoriesViewHolder
import com.example.foodinfo.utils.AppListAdapter


class HomeCategoriesAdapter(
    private val onItemClickListener: (Int) -> Unit
) : AppListAdapter<CategorySearchModel>(HomeCategoriesViewHolder.ItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return HomeCategoriesViewHolder(
            RvItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { (holder as HomeCategoriesViewHolder).bind(it) }
    }
}