package com.example.foodinfo.ui.base.adapter


interface AppViewHolderModel {
    fun areItemsTheSame(other: AppViewHolderModel): Boolean

    fun areContentsTheSame(other: AppViewHolderModel): Boolean

    fun getChangePayload(other: AppViewHolderModel): Any? = null
}