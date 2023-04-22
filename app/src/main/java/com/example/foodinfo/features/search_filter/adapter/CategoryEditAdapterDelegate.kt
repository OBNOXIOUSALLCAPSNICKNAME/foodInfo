package com.example.foodinfo.features.search_filter.adapter

import com.example.foodinfo.databinding.RvItemCategoryOfSearchFilterEditBinding
import com.example.foodinfo.features.search_filter.model.LabelEditVHModel
import com.example.foodinfo.ui.base.adapter.appAdapterDelegate


fun categoryEditAdapterDelegate(
    onQuestionMarkClickListener: (LabelEditVHModel) -> Unit,
    onItemClickListener: (LabelEditVHModel) -> Unit
) = appAdapterDelegate(
    inflate = RvItemCategoryOfSearchFilterEditBinding::inflate,
    onInit = { binding, itemProvider ->
        with(binding) {
            ivQuestionMark.setOnClickListener {
                onQuestionMarkClickListener(itemProvider())
            }
            llContent.setOnClickListener {
                onItemClickListener.invoke(itemProvider())
            }
        }
    },
    onBind = { binding, item, _ ->
        with(binding) {
            tvHeader.text = item.name
            cbChecked.isChecked = item.isSelected
        }
    }
)