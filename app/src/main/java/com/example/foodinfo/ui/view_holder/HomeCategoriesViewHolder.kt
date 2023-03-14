package com.example.foodinfo.ui.view_holder

import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.foodinfo.R
import com.example.foodinfo.databinding.RvItemCategoryBinding
import com.example.foodinfo.repository.model.CategorySearchModel
import com.example.foodinfo.ui.base.BaseViewHolder
import com.example.foodinfo.utils.glide.GlideApp


class HomeCategoriesViewHolder(
    private val binding: RvItemCategoryBinding,
    onItemClickListener: (Int) -> Unit
) : BaseViewHolder<RvItemCategoryBinding, CategorySearchModel>(binding) {

    init {
        binding.clContent.setOnClickListener {
            onItemClickListener(item.ID)
        }
    }


    override fun bind(newItem: CategorySearchModel) {
        super.bind(newItem)
        binding.tvTitle.text = item.name
        GlideApp.with(binding.ivPreview.context)
            .load(item.preview)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .error(R.drawable.ic_no_image)
            .into(binding.ivPreview)
    }
}