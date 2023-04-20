package com.example.foodinfo.domain.model

import androidx.paging.PagingConfig


/**
 * Class that contains all necessary data to fetch recipe page.
 */
data class PagingHelper(
    val recipeMetadata: RecipeMetadata,
    val filterPreset: SearchFilterPreset,
    val pagingConfig: PagingConfig,
    val inputText: String,
    val isOnline: Boolean
)