package com.example.foodinfo.domain.model

import androidx.recyclerview.widget.DiffUtil
import com.example.foodinfo.local.model.SearchFilterDB


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