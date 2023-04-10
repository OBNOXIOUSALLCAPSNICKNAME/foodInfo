package com.example.foodinfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemSearchInputBinding
import com.example.foodinfo.repository.model.SearchInputModel
import com.example.foodinfo.ui.view_holder.SearchInputViewHolder
import com.example.foodinfo.utils.AppListAdapter


class SearchInputAdapter(
    private val onArrowClickListener: (String) -> Unit,
    private val onItemClickListener: (String) -> Unit
) : AppListAdapter<SearchInputModel>(SearchInputViewHolder.ItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return SearchInputViewHolder(
            RvItemSearchInputBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onArrowClickListener,
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { (holder as SearchInputViewHolder).bind(it) }
    }
}