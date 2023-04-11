package com.example.foodinfo.ui.view_holder

import androidx.recyclerview.widget.DiffUtil
import com.example.foodinfo.databinding.RvItemSearchInputBinding
import com.example.foodinfo.domain.model.SearchInputModel
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


    object ItemCallBack : DiffUtil.ItemCallback<SearchInputModel>() {
        override fun areItemsTheSame(
            oldItem: SearchInputModel,
            newItem: SearchInputModel
        ) =
            oldItem.ID == newItem.ID

        override fun areContentsTheSame(
            oldItem: SearchInputModel,
            newItem: SearchInputModel
        ) =
            oldItem.inputText == newItem.inputText &&
            oldItem.date == newItem.date
    }
}