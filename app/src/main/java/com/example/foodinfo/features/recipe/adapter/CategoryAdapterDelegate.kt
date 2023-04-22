package com.example.foodinfo.features.recipe.adapter

import android.view.LayoutInflater
import com.example.foodinfo.databinding.ItemRecipeCategoryBinding
import com.example.foodinfo.databinding.TvChipBinding
import com.example.foodinfo.features.recipe.model.CategoryVHModel
import com.example.foodinfo.ui.base.adapter.appAdapterDelegate


fun categoryAdapterDelegate(
    onLabelClickListener: (Int) -> Unit
) = appAdapterDelegate<CategoryVHModel, ItemRecipeCategoryBinding>(
    inflate = ItemRecipeCategoryBinding::inflate,
    onBind = { binding, item, _ ->
        binding.tvTitle.text = item.name
        LayoutInflater.from(binding.root.context).also { inflater ->
            for (label in item.labels) {
                binding.cgLabels.addView(
                    TvChipBinding.inflate(inflater, null, false).apply {
                        this.textView.text = label.name
                    }.root.apply {
                        setOnClickListener { onLabelClickListener(label.infoID) }
                    }
                )
            }
        }
    }
)