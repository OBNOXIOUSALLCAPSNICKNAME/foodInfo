package com.example.foodinfo.repository.model

import androidx.recyclerview.widget.DiffUtil


data class RecipeIngredientModel(
    val ID: Int,
    val text: String,
    val measure: String,
    val quantity: Float,
    val weight: Float,
    val food: String,
    val foodCategory: String,
    val foodId: String,
    val previewURL: String
) {

    object ItemCallBack :
        DiffUtil.ItemCallback<RecipeIngredientModel>() {

        override fun areItemsTheSame(
            oldItem: RecipeIngredientModel,
            newItem: RecipeIngredientModel
        ): Boolean {
            return oldItem.ID == newItem.ID
        }

        override fun areContentsTheSame(
            oldItem: RecipeIngredientModel,
            newItem: RecipeIngredientModel
        ): Boolean {
            return oldItem.text == newItem.text &&
                    oldItem.measure == newItem.measure &&
                    oldItem.quantity == newItem.quantity &&
                    oldItem.weight == newItem.weight &&
                    oldItem.food == newItem.food &&
                    oldItem.foodCategory == newItem.foodCategory &&
                    oldItem.foodId == newItem.foodId &&
                    oldItem.previewURL == newItem.previewURL
        }
    }
}