package com.example.foodinfo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemFilterInputBaseFieldBinding
import com.example.foodinfo.repository.model.BasicOfSearchFilterEditModel
import com.example.foodinfo.ui.view_holder.FilterBaseFieldViewHolder


class FilterBaseFieldAdapter(
    context: Context,
    private val onValueChangedCallback: (Int, Float, Float) -> Unit
) : ListAdapter<BasicOfSearchFilterEditModel, ViewHolder>(BasicOfSearchFilterEditModel.ItemCallBack) {

    private val layoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return FilterBaseFieldViewHolder(
            RvItemFilterInputBaseFieldBinding.inflate(layoutInflater, parent, false),
            onValueChangedCallback
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { field ->
            holder as FilterBaseFieldViewHolder
            holder.bind(field)
        }
    }
}