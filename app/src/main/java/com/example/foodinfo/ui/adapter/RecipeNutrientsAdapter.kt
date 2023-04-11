package com.example.foodinfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemRecipeNutrientBinding
import com.example.foodinfo.domain.model.NutrientOfRecipeModel
import com.example.foodinfo.ui.view_holder.NutrientsViewHolder
import com.example.foodinfo.utils.AppListAdapter


class RecipeNutrientsAdapter(
    private val onGetNutrientWeight: (Float, Float, String) -> String,
    private val onGetNutrientPercent: (Int) -> String,
    private val onNutrientClickListener: (Int) -> Unit,
) : AppListAdapter<NutrientOfRecipeModel>(NutrientsViewHolder.ItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return NutrientsViewHolder(
            RvItemRecipeNutrientBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onGetNutrientWeight,
            onGetNutrientPercent,
            onNutrientClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { (holder as NutrientsViewHolder).bind(it) }
    }
}