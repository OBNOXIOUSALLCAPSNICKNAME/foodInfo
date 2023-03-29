package com.example.foodinfo.repository.model

import androidx.recyclerview.widget.DiffUtil


data class RecipeShortModel(
    val ID: String,
    val name: String,
    val calories: String,
    val servings: String,
    val cookingTime: Int,
    val ingredientsCount: String,
    val preview: EdamamImageURL,
    val isFavorite: Boolean
) {

    object ItemCallBack : DiffUtil.ItemCallback<RecipeShortModel>() {
        override fun areItemsTheSame(
            oldItem: RecipeShortModel, newItem: RecipeShortModel
        ) =
            oldItem.ID == newItem.ID

        override fun areContentsTheSame(
            oldItem: RecipeShortModel, newItem: RecipeShortModel
        ) =
            oldItem.name == newItem.name &&
            oldItem.calories == newItem.calories &&
            oldItem.servings == newItem.servings &&
            oldItem.cookingTime == newItem.cookingTime &&
            oldItem.ingredientsCount == newItem.ingredientsCount &&
            oldItem.preview == newItem.preview &&
            oldItem.isFavorite == newItem.isFavorite


        override fun getChangePayload(
            oldItem: RecipeShortModel,
            newItem: RecipeShortModel
        ): Any? {
            if (oldItem.isFavorite != newItem.isFavorite) return newItem.isFavorite
            return super.getChangePayload(oldItem, newItem)
        }
    }
}