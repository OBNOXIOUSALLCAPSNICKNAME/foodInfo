package com.example.foodinfo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemFilterInputCategoryEditBinding
import com.example.foodinfo.repository.model.LabelOfSearchFilterEditModel
import com.example.foodinfo.ui.view_holder.FilterCategoryEditViewHolder


class FilterCategoryEditAdapter(
    context: Context,
    private val onQuestionMarkClickListener: (Int) -> Unit
) : ListAdapter<LabelOfSearchFilterEditModel, ViewHolder>(
    LabelOfSearchFilterEditModel.ItemCallBack
) {

    private val layoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return FilterCategoryEditViewHolder(
            RvItemFilterInputCategoryEditBinding.inflate(layoutInflater, parent, false),
            onQuestionMarkClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { label ->
            holder as FilterCategoryEditViewHolder
            holder.bind(label)
        }
    }
}