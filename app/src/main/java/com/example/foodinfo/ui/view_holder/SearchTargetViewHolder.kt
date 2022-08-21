package com.example.foodinfo.ui.view_holder

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodinfo.R
import com.example.foodinfo.databinding.RvItemSearchTargetBinding
import com.example.foodinfo.repository.model.RecipeShortModel
import com.example.foodinfo.utils.glide.GlideApp


class SearchTargetViewHolder(
    private val binding: RvItemSearchTargetBinding,
    private val onGetTime: (Int) -> String,
    onItemClickListener: (String) -> Unit
) : BaseViewHolder<RvItemSearchTargetBinding, RecipeShortModel>(binding) {

    init {
        binding.ivPreview.setOnClickListener {
            onItemClickListener(item.id)
        }
    }


    override fun bind(newItem: RecipeShortModel): Unit = with(binding) {
        super.bind(newItem)
        tvName.text = item.name
        tvTimeValue.text = onGetTime.invoke(item.totalTime)
        tvServingsValue.text = item.servings
        tvCaloriesValue.text = item.calories
        GlideApp.with(ivPreview.context)
            .load(item.previewURL)
            .error(R.drawable.ic_no_image)
            .placeholder(null)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(ivPreview)
    }
}