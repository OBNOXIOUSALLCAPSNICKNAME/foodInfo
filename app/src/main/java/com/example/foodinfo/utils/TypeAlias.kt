package com.example.foodinfo.utils

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodinfo.remote.NetworkResponse


typealias ApiResponse<S> = NetworkResponse<S, Any>

typealias AppListAdapter<T> = ListAdapter<T, ViewHolder>
typealias AppPageAdapter<T> = PagingDataAdapter<T, ViewHolder>