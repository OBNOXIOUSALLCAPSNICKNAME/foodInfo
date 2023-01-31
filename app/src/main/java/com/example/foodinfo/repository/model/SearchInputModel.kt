package com.example.foodinfo.repository.model

import androidx.recyclerview.widget.DiffUtil


// choose best dateTime format and lib
data class SearchInputModel(
    val ID: Int = 0,
    val inputText: String,
    val date: String = System.currentTimeMillis().toString()
) {

    object ItemCallBack : DiffUtil.ItemCallback<SearchInputModel>() {

        override fun areItemsTheSame(
            oldItem: SearchInputModel, newItem: SearchInputModel
        ): Boolean {
            return oldItem.ID == newItem.ID
        }

        override fun areContentsTheSame(
            oldItem: SearchInputModel, newItem: SearchInputModel
        ): Boolean {
            return oldItem.inputText == newItem.inputText &&
                    oldItem.date == newItem.date
        }
    }
}