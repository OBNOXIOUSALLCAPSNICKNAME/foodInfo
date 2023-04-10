package com.example.foodinfo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.databinding.RvItemFilterInputNutrientsEditBinding
import com.example.foodinfo.repository.model.NutrientOfSearchFilterEditModel
import com.example.foodinfo.ui.view_holder.FilterNutrientFieldEditViewHolder
import com.example.foodinfo.utils.AppListAdapter


class FilterNutrientFieldEditAdapter(
    private val onHeaderClickCallback: (Int) -> Unit,
    private val onValueChangedCallback: (Int, Float?, Float?) -> Unit
) : AppListAdapter<NutrientOfSearchFilterEditModel>(FilterNutrientFieldEditViewHolder.ItemCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return FilterNutrientFieldEditViewHolder(
            RvItemFilterInputNutrientsEditBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onHeaderClickCallback,
            onValueChangedCallback
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { (holder as FilterNutrientFieldEditViewHolder).bind(it) }
    }
}