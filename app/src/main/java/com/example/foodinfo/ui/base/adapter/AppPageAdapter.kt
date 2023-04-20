package com.example.foodinfo.ui.base.adapter

import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter


/**
 * Multiple model types unsupported for [AppPageAdapter] due to **T** invariance for [PagingData]
 * (unlike [AppListAdapter] which is inherited from [ListAdapter]):
 * ~~~
 * interface BaseModel
 *
 * class ModelImpl : BaseModel
 *
 * // ListAdapter analog
 * abstract class ListAdapter<T> {
 *     fun addData(data: List<T>) {}
 * }
 * // PagingDataAdapter analog
 * abstract class PagingDataAdapter<T : Any> {
 *     fun addData(data: PagingData<T>) {}
 * }
 *
 * class AppListAdapter : ListAdapter<BaseModel>()
 * class AppPageAdapter : PagingDataAdapter<BaseModel>()
 *
 *
 * val appListAdapter = AppListAdapter()
 * val appPageAdapter = AppPageAdapter()
 *
 * appListAdapter.addData(emptyList<ModelImpl>()) // OK
 *
 * //Type mismatch. Required: PagingData<BaseModel> Found: PagingData<ModelImpl>
 * appPageAdapter.addData(PagingData.empty<ModelImpl>())
 * ~~~
 *
 * To achieve multiple model types use sealed class instead:
 * ~~~
 * sealed class Animal : AppViewHolderModel {
 *     data class Cat(val data: Any) : Animal() {
 *         override fun areItemsTheSame(other: AppViewHolderModel): Boolean { ... }
 *
 *         override fun areContentsTheSame(other: AppViewHolderModel): Boolean { ... }
 *     }
 *
 *     data class Dog(val data: Any) : Animal() {
 *         override fun areItemsTheSame(other: AppViewHolderModel): Boolean { ... }
 *
 *         override fun areContentsTheSame(other: AppViewHolderModel): Boolean { ... }
 *     }
 * }
 *
 * val recyclerAdapter: AppPageAdapter<Animal> by appPageAdapter(
 *     catAdapterDelegate(),
 *     dogAdapterDelegate()
 * )
 * ~~~
 */
class AppPageAdapter<T : AppViewHolderModel>(
    private val delegates: List<AppAdapterDelegate>
) : PagingDataAdapter<T, AppViewHolder>(AppDiffCallback()) {

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


fun <T : AppViewHolderModel> appPageAdapter(vararg delegates: AppAdapterDelegate): Lazy<AppPageAdapter<T>> {
    return lazy(LazyThreadSafetyMode.NONE) { AppPageAdapter(delegates.toList()) }
}