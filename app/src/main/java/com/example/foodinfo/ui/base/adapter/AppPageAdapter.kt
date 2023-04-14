package com.example.foodinfo.ui.base.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter

class AppPageAdapter(
    private val delegates: List<AppAdapterDelegate>
) : PagingDataAdapter<AppViewHolderModel, AppViewHolder>(AppDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        try {
            return delegates[viewType].inflate(parent)
        } catch (e: Exception) {
            throw IllegalStateException(
                "PlaceHolder delegate was not declared at the top of the delegates list."
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        val index = getItem(position)?.let { item ->
            delegates.indexOfFirst { it.isValidType(item) }
        }
        return when (index) {
            -1   -> throw IllegalStateException(
                "No valid ViewHolder for item of type ${getItem(position)!!::class.java}."
            )
            null -> 0
            else -> index
        }
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, emptyList()) }
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int, payloads: List<Any>) {
        getItem(position)?.let { holder.bind(it, payloads) }
    }
}


fun appPageAdapter(vararg delegates: AppAdapterDelegate): Lazy<AppPageAdapter> {
    return lazy(LazyThreadSafetyMode.NONE) { AppPageAdapter(delegates.toList()) }
}