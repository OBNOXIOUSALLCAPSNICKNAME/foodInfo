package com.example.foodinfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemFilterInputNutrientsBinding
import com.example.foodinfo.domain.model.NutrientOfSearchFilterPreviewModel
import com.example.foodinfo.ui.view_holder.FilterNutrientFieldViewHolder
import com.example.foodinfo.utils.AppListAdapter


class FilterNutrientsAdapter(
    private val getFormattedRange: (Float?, Float?, String) -> String
) : AppListAdapter<NutrientOfSearchFilterPreviewModel>(FilterNutrientFieldViewHolder.ItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return FilterNutrientFieldViewHolder(
            RvItemFilterInputNutrientsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            getFormattedRange
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { (holder as FilterNutrientFieldViewHolder).bind(it) }
    }
}