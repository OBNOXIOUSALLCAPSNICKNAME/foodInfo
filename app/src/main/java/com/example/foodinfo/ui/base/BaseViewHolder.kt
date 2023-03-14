package com.example.foodinfo.ui.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Base ViewHolder class that allows to set clickListeners once during onCreateViewHolder
 * and don't doing that every time at onBindViewHolder.
 */
abstract class BaseViewHolder<out V : ViewBinding, I : Any>(binding: V) :
    RecyclerView.ViewHolder(binding.root) {

    lateinit var item: I

    open fun bind(newItem: I) {
        item = newItem
    }

    open fun bind(newItem: I, payloads: List<Any>) {
        item = newItem
    }
}