package com.example.foodinfo.repository.model

import androidx.recyclerview.widget.DiffUtil


data class RecipeFavoriteModel(
    val ID: String,
    val name: String,
    val calories: String,
    val source: String,
    val servings: String,
    val previewURL: String
) {

    object ItemCallBack : DiffUtil.ItemCallback<RecipeFavoriteModel>() {
        override fun areItemsTheSame(
            oldItem: RecipeFavoriteModel, newItem: RecipeFavoriteModel
        ): Boolean {
            return oldItem.ID == newItem.ID
        }

        override fun areContentsTheSame(
            oldItem: RecipeFavoriteModel, newItem: RecipeFavoriteModel
        ): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.calories == newItem.calories &&
                    oldItem.source == newItem.source &&
                    oldItem.servings == newItem.servings &&
                    oldItem.previewURL == newItem.previewURL
        }
    }
}