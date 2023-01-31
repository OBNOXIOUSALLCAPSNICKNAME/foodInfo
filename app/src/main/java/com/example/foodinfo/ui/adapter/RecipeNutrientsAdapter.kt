package com.example.foodinfo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemRecipeNutrientBinding
import com.example.foodinfo.repository.model.NutrientOfRecipeModel
import com.example.foodinfo.ui.view_holder.NutrientsViewHolder


class RecipeNutrientsAdapter(
    context: Context,
    private val onGetNutrientWeight: (Float, Float, String) -> String,
    private val onGetNutrientPercent: (Int) -> String,
    private val onNutrientClickListener: (Int) -> Unit,
) : ListAdapter<NutrientOfRecipeModel, ViewHolder>(
    NutrientOfRecipeModel.ItemCallBack
) {

    private val layoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return NutrientsViewHolder(
            RvItemRecipeNutrientBinding.inflate(layoutInflater, parent, false),
            onGetNutrientWeight,
            onGetNutrientPercent,
            onNutrientClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { nutrient ->
            holder as NutrientsViewHolder
            holder.bind(nutrient)
        }
    }
}