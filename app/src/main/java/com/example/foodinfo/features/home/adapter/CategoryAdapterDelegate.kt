package com.example.foodinfo.features.home.adapter

import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.foodinfo.R
import com.example.foodinfo.core.ui.base.adapter.appAdapterDelegate
import com.example.foodinfo.core.utils.glide.GlideApp
import com.example.foodinfo.databinding.RvItemCategoryBinding
import com.example.foodinfo.features.home.model.CategoryVHModel


fun categoryAdapterDelegate(
    onItemClickListener: (CategoryVHModel) -> Unit
) = appAdapterDelegate(
    inflate = RvItemCategoryBinding::inflate,
    onInit = { binding, itemProvider ->
        binding.root.apply {
            setOnClickListener {
                onItemClickListener(itemProvider())
            }
        }
    },
    onBind = { binding, item, _ ->
        binding.tvTitle.text = item.name
        GlideApp.with(binding.ivPreview.context)
            .load(item.preview)
            .into(binding.ivPreview)
    }
)