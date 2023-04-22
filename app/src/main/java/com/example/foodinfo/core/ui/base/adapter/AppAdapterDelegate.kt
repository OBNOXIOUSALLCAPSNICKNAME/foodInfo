package com.example.foodinfo.core.ui.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding


interface AppAdapterDelegate {
    fun isValidType(item: AppViewHolderModel): Boolean

    fun inflate(parent: ViewGroup): AppViewHolder
}

inline fun <reified Model : AppViewHolderModel, VB : ViewBinding> appAdapterDelegate(
    noinline inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    noinline onInit: (VB, () -> Model) -> Unit = { _, _ -> },
    noinline onBind: (VB, Model, List<Any>) -> Unit = { _, _, _ -> }
) = object : AppAdapterDelegate {

    override fun isValidType(item: AppViewHolderModel): Boolean {
        return item is Model
    }

    override fun inflate(parent: ViewGroup): AppViewHolder {
        inflate(LayoutInflater.from(parent.context), parent, false).also { binding ->
            return object : AppViewHolder(binding) {
                init {
                    onInit(binding) { item as Model }
                }

                override fun bind(item: AppViewHolderModel, payloads: List<Any>) {
                    this.item = item
                    onBind(binding, item as Model, payloads)
                }
            }
        }
    }
}

fun <VB : ViewBinding> placeholderAdapterDelegate(
    inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
) = appAdapterDelegate<AppViewHolderModel, VB>(inflate)