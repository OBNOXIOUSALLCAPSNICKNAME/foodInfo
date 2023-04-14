package com.example.foodinfo.ui.base.adapter

import androidx.recyclerview.widget.DiffUtil


object AppDiffCallback : DiffUtil.ItemCallback<AppViewHolderModel>() {
    override fun areItemsTheSame(
        oldItem: AppViewHolderModel,
        newItem: AppViewHolderModel
    ): Boolean =
        oldItem.areItemsTheSame(newItem)

    override fun areContentsTheSame(
        oldItem: AppViewHolderModel,
        newItem: AppViewHolderModel
    ): Boolean =
        oldItem.areContentsTheSame(newItem)

    override fun getChangePayload(
        oldItem: AppViewHolderModel,
        newItem: AppViewHolderModel
    ): Any? =
        oldItem.getChangePayload(newItem)
}