package com.example.foodinfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemFilterInputBaseFieldBinding
import com.example.foodinfo.repository.model.BasicOfSearchFilterEditModel
import com.example.foodinfo.ui.view_holder.FilterBaseFieldViewHolder
import com.example.foodinfo.utils.AppListAdapter


class FilterBaseFieldAdapter(
    private val onValueChangedCallback: (Int, Float?, Float?) -> Unit
) : AppListAdapter<BasicOfSearchFilterEditModel>(FilterBaseFieldViewHolder.ItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return FilterBaseFieldViewHolder(
            RvItemFilterInputBaseFieldBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onValueChangedCallback
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { (holder as FilterBaseFieldViewHolder).bind(it) }
    }
}