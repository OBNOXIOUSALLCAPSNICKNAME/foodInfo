package com.example.foodinfo.utils.paging

import com.example.foodinfo.domain.model.SearchFilterPresetModel
import com.example.foodinfo.local.model.RecipeAttrsDB


/**
 * Class that contains all necessary data to fetch recipe page.
 */
data class PageFetchHelper(
    val isOnline: Boolean,
    val recipeAttrs: RecipeAttrsDB,
    val filterPreset: SearchFilterPresetModel
)