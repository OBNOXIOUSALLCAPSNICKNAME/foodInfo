package com.example.foodinfo.repository.model

import androidx.recyclerview.widget.DiffUtil
import com.example.foodinfo.local.dto.SearchFilterDB


data class SearchFilterModel(
    val name: String = SearchFilterDB.DEFAULT_NAME
) {

    object ItemCallBack :
        DiffUtil.ItemCallback<SearchFilterModel>() {

        override fun areItemsTheSame(
            oldItem: SearchFilterModel,
            newItem: SearchFilterModel
        ) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(
            oldItem: SearchFilterModel,
            newItem: SearchFilterModel
        ) =
            oldItem.name == newItem.name
    }
}