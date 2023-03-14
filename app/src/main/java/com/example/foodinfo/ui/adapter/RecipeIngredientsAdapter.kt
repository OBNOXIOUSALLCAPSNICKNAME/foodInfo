package com.example.foodinfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemRecipeIngredientBinding
import com.example.foodinfo.repository.model.RecipeIngredientModel
import com.example.foodinfo.ui.view_holder.IngredientsViewHolder
import com.example.foodinfo.utils.AppListAdapter


class RecipeIngredientsAdapter(
    private val onGetWeight: (Float) -> String,
    private val onGetQuantity: (Float, String) -> String,
) : AppListAdapter<RecipeIngredientModel>(RecipeIngredientModel.ItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return IngredientsViewHolder(
            RvItemRecipeIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onGetWeight,
            onGetQuantity
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { (holder as IngredientsViewHolder).bind(it) }
    }
}