package com.example.foodinfo.ui.view_holder

import android.view.LayoutInflater
import com.example.foodinfo.databinding.ItemRecipeCategoryBinding
import com.example.foodinfo.databinding.TvChipBinding
import com.example.foodinfo.repository.model.CategoryOfRecipeModel


class RecipeCategoryViewHolder(
    private val inflater: LayoutInflater,
    private val binding: ItemRecipeCategoryBinding,
    private val onLabelClickListener: (Int) -> Unit
) : BaseViewHolder<ItemRecipeCategoryBinding, CategoryOfRecipeModel>(binding) {

    override fun bind(newItem: CategoryOfRecipeModel) {
        super.bind(newItem)
        binding.tvTitle.text = item.name
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