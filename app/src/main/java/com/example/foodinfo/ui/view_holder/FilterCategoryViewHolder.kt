package com.example.foodinfo.ui.view_holder

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import com.example.foodinfo.databinding.RvItemFilterInputCategoryBinding
import com.example.foodinfo.databinding.TvChipBinding
import com.example.foodinfo.domain.model.CategoryOfSearchFilterPreviewModel
import com.example.foodinfo.ui.base.BaseViewHolder


class FilterCategoryViewHolder(
    private val inflater: LayoutInflater,
    private val binding: RvItemFilterInputCategoryBinding,
    private val onBtnEditClickListener: (Int) -> Unit
) : BaseViewHolder<RvItemFilterInputCategoryBinding, CategoryOfSearchFilterPreviewModel>(binding) {

    init {
        binding.ivEdit.setOnClickListener { onBtnEditClickListener(item.ID) }
    }

    override fun bind(newItem: CategoryOfSearchFilterPreviewModel) {
        super.bind(newItem)
        binding.tvTitle.text = item.name
        if (item.labels.isEmpty()) {
            binding.cgLabels.isVisible = false
            binding.tvUnspecified.isVisible = true
        } else {
            binding.cgLabels.removeAllViews()
            binding.cgLabels.isVisible = true
            binding.tvUnspecified.isVisible = false
            for (label in item.labels) {
                binding.cgLabels.addView(
                    TvChipBinding.inflate(inflater, null, false).apply {
                        this.textView.text = label.name
                    }.root
                )
            }
        }
    }


    object ItemCallBack : DiffUtil.ItemCallback<CategoryOfSearchFilterPreviewModel>() {
        override fun areItemsTheSame(
            oldItem: CategoryOfSearchFilterPreviewModel,
            newItem: CategoryOfSearchFilterPreviewModel
        ) =
            oldItem.ID == newItem.ID

        override fun areContentsTheSame(
            oldItem: CategoryOfSearchFilterPreviewModel,
            newItem: CategoryOfSearchFilterPreviewModel
        ) =
            oldItem.name == newItem.name &&
            oldItem.labels == newItem.labels
    }
}