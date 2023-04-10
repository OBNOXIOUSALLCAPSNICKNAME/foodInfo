package com.example.foodinfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemFilterInputCategoryEditBinding
import com.example.foodinfo.repository.model.LabelOfSearchFilterEditModel
import com.example.foodinfo.ui.view_holder.FilterCategoryEditViewHolder
import com.example.foodinfo.utils.AppListAdapter


class FilterCategoryEditAdapter(
    private val onQuestionMarkClickListener: (Int) -> Unit,
    private val onItemClickListener: (Int, Boolean) -> Unit
) : AppListAdapter<LabelOfSearchFilterEditModel>(FilterCategoryEditViewHolder.ItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return FilterCategoryEditViewHolder(
            RvItemFilterInputCategoryEditBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onQuestionMarkClickListener,
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { (holder as FilterCategoryEditViewHolder).bind(it) }
    }
}