package com.example.foodinfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemLabelBinding
import com.example.foodinfo.repository.model.LabelSearchModel
import com.example.foodinfo.ui.view_holder.SearchLabelsViewHolder
import com.example.foodinfo.utils.AppListAdapter


class SearchLabelsAdapter(
    private val onItemClickListener: (Int) -> Unit,
) : AppListAdapter<LabelSearchModel>(SearchLabelsViewHolder.ItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return SearchLabelsViewHolder(
            RvItemLabelBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { (holder as SearchLabelsViewHolder).bind(it) }
    }
}