package com.example.foodinfo.ui.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter


class AppListAdapter(
    private val delegates: List<AppAdapterDelegate>
) : ListAdapter<AppViewHolderModel, AppViewHolder>(AppDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        return delegates[viewType].inflate(parent)
    }

    override fun getItemViewType(position: Int): Int {
        delegates.indexOfFirst { it.isValidType(getItem(position)) }.also { index ->
            if (index == -1) {
                throw IllegalStateException(
                    "No valid ViewHolder for item of type ${getItem(position)::class.java}"
                )
            } else {
                return index
            }
        }
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(getItem(position), emptyList())
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int, payloads: List<Any>) {
        holder.bind(getItem(position), payloads)
    }
}


fun appListAdapter(vararg delegates: AppAdapterDelegate): Lazy<AppListAdapter> {
    return lazy(LazyThreadSafetyMode.NONE) { AppListAdapter(delegates.toList()) }
}