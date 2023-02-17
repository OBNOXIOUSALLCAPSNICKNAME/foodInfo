package com.example.foodinfo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemFilterInputNutrientsBinding
import com.example.foodinfo.repository.model.NutrientOfSearchFilterPreviewModel
import com.example.foodinfo.ui.view_holder.FilterNutrientFieldViewHolder


class FilterNutrientsAdapter(
    context: Context,
    private val getFormattedRange: (Float?, Float?, String) -> String
) : ListAdapter<NutrientOfSearchFilterPreviewModel, ViewHolder>(NutrientOfSearchFilterPreviewModel.ItemCallBack) {

    private val layoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return FilterNutrientFieldViewHolder(
            RvItemFilterInputNutrientsBinding.inflate(layoutInflater, parent, false),
            getFormattedRange
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { nutrient ->
            holder as FilterNutrientFieldViewHolder
            holder.bind(nutrient)
        }
    }
}