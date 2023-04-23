package com.example.foodinfo.features.category.adapter

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodinfo.R
import com.example.foodinfo.core.ui.base.adapter.appAdapterDelegate
import com.example.foodinfo.core.utils.glide.GlideApp
import com.example.foodinfo.databinding.RvItemLabelOfCategoryBinding
import com.example.foodinfo.features.category.model.LabelVHModel


fun labelAdapterDelegate(
    onItemClickListener: (LabelVHModel) -> Unit
) = appAdapterDelegate(
    inflate = RvItemLabelOfCategoryBinding::inflate,
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
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.ivPreview)
    }
)