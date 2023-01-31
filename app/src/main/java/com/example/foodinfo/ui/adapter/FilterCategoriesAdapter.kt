package com.example.foodinfo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemFilterInputCategoryBinding
import com.example.foodinfo.repository.model.CategoryOfSearchFilterPreviewModel
import com.example.foodinfo.ui.view_holder.FilterCategoryViewHolder


class FilterCategoriesAdapter(
    context: Context,
    private val onLabelClickListener: (Int) -> Unit
) : ListAdapter<CategoryOfSearchFilterPreviewModel, ViewHolder>(
    CategoryOfSearchFilterPreviewModel.ItemCallBack
) {

    private val layoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return FilterCategoryViewHolder(
            layoutInflater,
            RvItemFilterInputCategoryBinding.inflate(layoutInflater, parent, false),
            onLabelClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { category ->
            holder as FilterCategoryViewHolder
            holder.bind(category)
        }
    }
}