package com.example.foodinfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemFilterInputCategoryBinding
import com.example.foodinfo.domain.model.CategoryOfSearchFilterPreviewModel
import com.example.foodinfo.ui.view_holder.FilterCategoryViewHolder
import com.example.foodinfo.utils.AppListAdapter


class FilterCategoriesAdapter(
    private val onLabelClickListener: (Int) -> Unit
) : AppListAdapter<CategoryOfSearchFilterPreviewModel>(FilterCategoryViewHolder.ItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return FilterCategoryViewHolder(
            LayoutInflater.from(parent.context),
            RvItemFilterInputCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onLabelClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { (holder as FilterCategoryViewHolder).bind(it) }
    }
}