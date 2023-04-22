package com.example.foodinfo.core.utils.paging

import androidx.paging.PagingConfig
import com.example.foodinfo.core.utils.edamam.EdamamInfo


object AppPagingConfig {
    val RECIPE_EXPLORE_PAGER = PagingConfig(
        prefetchDistance = EdamamInfo.PAGE_SIZE,
        initialLoadSize = EdamamInfo.PAGE_SIZE,
        pageSize = EdamamInfo.PAGE_SIZE,
        maxSize = EdamamInfo.PAGE_SIZE * EdamamInfo.THROTTLING_CALLS,
        enablePlaceholders = true
    )
    val RECIPE_FAVORITE_PAGER = PagingConfig(
        pageSize = 20,
        initialLoadSize = 20,
        maxSize = 200
    )
}