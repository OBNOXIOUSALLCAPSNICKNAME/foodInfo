package com.example.foodinfo.ui.view_holder

import com.example.foodinfo.databinding.RvItemFilterInputCategoryEditBinding
import com.example.foodinfo.repository.model.LabelOfSearchFilterEditModel
import com.example.foodinfo.ui.base.BaseViewHolder


class FilterCategoryEditViewHolder(
    private val binding: RvItemFilterInputCategoryEditBinding,
    private val onQuestionMarkClickListener: (Int) -> Unit,
    private val onItemClickListener: (Int, Boolean) -> Unit
) : BaseViewHolder<RvItemFilterInputCategoryEditBinding, LabelOfSearchFilterEditModel>(binding) {

    init {
        binding.ivQuestionMark.setOnClickListener { onQuestionMarkClickListener(item.infoID) }
        binding.llContent.setOnClickListener {
            onItemClickListener.invoke(item.ID, !item.isSelected)
        }
    }

    override fun bind(newItem: LabelOfSearchFilterEditModel) {
        super.bind(newItem)
        with(binding) {
            tvHeader.text = item.name
            cbChecked.isChecked = item.isSelected
        }
    }
}