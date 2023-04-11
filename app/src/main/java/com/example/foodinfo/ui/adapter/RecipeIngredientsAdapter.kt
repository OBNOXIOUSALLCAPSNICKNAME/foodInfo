package com.example.foodinfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemRecipeIngredientBinding
import com.example.foodinfo.domain.model.IngredientOfRecipeModel
import com.example.foodinfo.ui.view_holder.IngredientsViewHolder
import com.example.foodinfo.utils.AppListAdapter


class RecipeIngredientsAdapter(
    private val onGetWeight: (Float) -> String
) : AppListAdapter<IngredientOfRecipeModel>(IngredientsViewHolder.ItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return IngredientsViewHolder(
            RvItemRecipeIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onGetWeight
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { (holder as IngredientsViewHolder).bind(it) }
    }
}