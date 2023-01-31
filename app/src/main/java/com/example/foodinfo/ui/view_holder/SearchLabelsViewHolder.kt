package com.example.foodinfo.ui.view_holder

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodinfo.R
import com.example.foodinfo.databinding.RvItemLabelBinding
import com.example.foodinfo.repository.model.LabelSearchModel
import com.example.foodinfo.utils.glide.GlideApp


class SearchLabelsViewHolder(
    private val binding: RvItemLabelBinding,
    onItemClickListener: (Int) -> Unit,
) : BaseViewHolder<RvItemLabelBinding, LabelSearchModel>(binding) {

    init {
        binding.clContent.setOnClickListener {
            onItemClickListener(item.ID)
        }
    }


    override fun bind(newItem: LabelSearchModel) {
        super.bind(newItem)
        binding.tvTitle.text = item.name
        GlideApp.with(binding.ivPreview.context)
            .load(item.preview)
            .error(R.drawable.ic_no_image)
            .placeholder(null)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.ivPreview)
    }
}