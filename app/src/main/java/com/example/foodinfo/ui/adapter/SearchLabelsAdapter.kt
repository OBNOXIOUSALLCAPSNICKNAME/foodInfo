package com.example.foodinfo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemLabelBinding
import com.example.foodinfo.repository.model.LabelSearchModel
import com.example.foodinfo.ui.view_holder.SearchLabelsViewHolder


class SearchLabelsAdapter(
    context: Context,
    private val onItemClickListener: (Int) -> Unit,
) : ListAdapter<LabelSearchModel, ViewHolder>(
    LabelSearchModel.ItemCallBack
) {

    private val layoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return SearchLabelsViewHolder(
            RvItemLabelBinding.inflate(layoutInflater, parent, false),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { label ->
            holder as SearchLabelsViewHolder
            holder.bind(label)
        }
    }
}