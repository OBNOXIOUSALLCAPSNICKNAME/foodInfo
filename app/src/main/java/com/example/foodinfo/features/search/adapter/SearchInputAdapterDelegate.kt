package com.example.foodinfo.features.search.adapter

import com.example.foodinfo.databinding.RvItemSearchInputBinding
import com.example.foodinfo.features.search.model.SearchInputVHModel
import com.example.foodinfo.ui.base.adapter.appAdapterDelegate


fun searchInputAdapterDelegate(
    onArrowClickListener: (SearchInputVHModel) -> Unit,
    onItemClickListener: (SearchInputVHModel) -> Unit
) = appAdapterDelegate(
    inflate = RvItemSearchInputBinding::inflate,
    onInit = { binding, itemProvider ->
        with(binding) {
            tvSearchInput.setOnClickListener { onItemClickListener(itemProvider()) }
            ivApplySearch.setOnClickListener { onItemClickListener(itemProvider()) }
            ivApplyText.setOnClickListener { onArrowClickListener(itemProvider()) }
        }
    },
    onBind = { binding, item, _ ->
        binding.tvSearchInput.text = item.inputText
    }
)