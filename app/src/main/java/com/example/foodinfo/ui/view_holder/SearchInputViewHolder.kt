package com.example.foodinfo.ui.view_holder

import com.example.foodinfo.databinding.RvItemSearchInputBinding
import com.example.foodinfo.repository.model.SearchInputModel
import com.example.foodinfo.ui.base.BaseViewHolder


class SearchInputViewHolder(
    private val binding: RvItemSearchInputBinding,
    onArrowClickListener: (String) -> Unit,
    onItemClickListener: (String) -> Unit
) : BaseViewHolder<RvItemSearchInputBinding, SearchInputModel>(binding) {

    init {
        with(binding) {
            tvSearchInput.setOnClickListener { onItemClickListener(item.inputText) }
            ivApplySearch.setOnClickListener { onItemClickListener(item.inputText) }
            ivApplyText.setOnClickListener { onArrowClickListener(item.inputText) }
        }
    }


    override fun bind(newItem: SearchInputModel) {
        super.bind(newItem)
        binding.tvSearchInput.text = item.inputText
    }
}