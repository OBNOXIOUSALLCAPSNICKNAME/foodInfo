package com.example.foodinfo.ui.base.adapter

import androidx.recyclerview.widget.DiffUtil


class AppDiffCallback<T : AppViewHolderModel> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem.areItemsTheSame(newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem.areContentsTheSame(newItem)

    override fun getChangePayload(oldItem: T, newItem: T): Any? = oldItem.getChangePayload(newItem)
}