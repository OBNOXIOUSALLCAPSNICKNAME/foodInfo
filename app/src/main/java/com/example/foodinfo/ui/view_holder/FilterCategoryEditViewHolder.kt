package com.example.foodinfo.ui.view_holder

import androidx.recyclerview.widget.DiffUtil
import com.example.foodinfo.databinding.RvItemFilterInputCategoryEditBinding
import com.example.foodinfo.domain.model.LabelOfSearchFilterEditModel
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


    object ItemCallBack : DiffUtil.ItemCallback<LabelOfSearchFilterEditModel>() {
        override fun areItemsTheSame(
            oldItem: LabelOfSearchFilterEditModel,
            newItem: LabelOfSearchFilterEditModel
        ) =
            oldItem.ID == newItem.ID

        override fun areContentsTheSame(
            oldItem: LabelOfSearchFilterEditModel,
            newItem: LabelOfSearchFilterEditModel
        ) =
            oldItem.infoID == newItem.infoID &&
            oldItem.name == newItem.name &&
            oldItem.isSelected == newItem.isSelected
    }
}