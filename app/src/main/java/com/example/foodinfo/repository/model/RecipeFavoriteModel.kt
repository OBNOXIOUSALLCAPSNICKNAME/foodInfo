package com.example.foodinfo.repository.model

import androidx.recyclerview.widget.DiffUtil


data class RecipeFavoriteModel(
    val ID: String,
    val name: String,
    val calories: String,
    val source: String,
    val servings: String,
    val preview: EdamamImageURL
) {

    object ItemCallBack : DiffUtil.ItemCallback<RecipeFavoriteModel>() {
        override fun areItemsTheSame(
            oldItem: RecipeFavoriteModel, newItem: RecipeFavoriteModel
        ) =
            oldItem.ID == newItem.ID

        override fun areContentsTheSame(
            oldItem: RecipeFavoriteModel, newItem: RecipeFavoriteModel
        ) =
            oldItem.name == newItem.name &&
            oldItem.calories == newItem.calories &&
            oldItem.source == newItem.source &&
            oldItem.servings == newItem.servings &&
            oldItem.preview == newItem.preview
    }
}