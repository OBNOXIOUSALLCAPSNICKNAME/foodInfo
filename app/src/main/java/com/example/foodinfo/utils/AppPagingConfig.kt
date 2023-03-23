package com.example.foodinfo.utils

import androidx.paging.PagingConfig


object AppPagingConfig {
    val RECIPE_EXPLORE__PAGER = PagingConfig(
        pageSize = 10,
        initialLoadSize = 20,
        jumpThreshold = 40,
        maxSize = 40
    )
    val RECIPE_FAVORITE_PAGER = PagingConfig(
        pageSize = 10,
        initialLoadSize = 20,
        jumpThreshold = 40,
        maxSize = 40
    )
}