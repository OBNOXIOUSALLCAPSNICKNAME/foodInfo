package com.example.foodinfo.features.search_filter.adapter

import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.example.foodinfo.databinding.RvItemCategoryOfSearchFilterPreviewBinding
import com.example.foodinfo.databinding.TvChipBinding
import com.example.foodinfo.features.search_filter.model.CategoryPreviewVHModel
import com.example.foodinfo.ui.base.adapter.appAdapterDelegate


fun categoryPreviewAdapterDelegate(
    onBtnEditClickListener: (CategoryPreviewVHModel) -> Unit
) = appAdapterDelegate(
    inflate = RvItemCategoryOfSearchFilterPreviewBinding::inflate,
    onInit = { binding, itemProvider ->
        binding.ivEdit.setOnClickListener { onBtnEditClickListener(itemProvider()) }
    },
    onBind = { binding, item, _ ->
        binding.tvTitle.text = item.name
        if (item.labels.isEmpty()) {
            binding.cgLabels.isVisible = false
            binding.tvUnspecified.isVisible = true
        } else {
            binding.cgLabels.removeAllViews()
            binding.cgLabels.isVisible = true
            binding.tvUnspecified.isVisible = false
            LayoutInflater.from(binding.root.context).also { inflater ->
                for (label in item.labels) {
                    binding.cgLabels.addView(
                        TvChipBinding.inflate(inflater, null, false).apply {
                            this.textView.text = label.name
                        }.root
                    )
                }
            }
        }
    }
)