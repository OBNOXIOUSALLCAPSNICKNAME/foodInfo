package com.example.foodinfo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemNutrientBinding
import com.example.foodinfo.repository.model.RecipeNutrientModel
import com.example.foodinfo.ui.view_holder.NutrientsViewHolder


class RecipeNutrientsAdapter(
    context: Context,
    private val onGetNutrientWeight: (Double, Double, String) -> String,
    private val onGetNutrientPercent: (Int) -> String,
) : ListAdapter<RecipeNutrientModel, ViewHolder>(
    RecipeNutrientModel.ItemCallBack
) {

    private val layoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return NutrientsViewHolder(
            RvItemNutrientBinding.inflate(layoutInflater, parent, false),
            onGetNutrientWeight,
            onGetNutrientPercent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { nutrient ->
            holder as NutrientsViewHolder
            holder.bind(nutrient)
        }
    }
}