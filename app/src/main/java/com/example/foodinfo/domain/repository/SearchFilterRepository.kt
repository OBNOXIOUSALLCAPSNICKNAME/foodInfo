package com.example.foodinfo.domain.repository

import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.model.*
import kotlinx.coroutines.flow.Flow


interface SearchFilterRepository {

    suspend fun resetFilter()

    suspend fun resetNutrients()

    suspend fun resetCategory(categoryID: Int)

    suspend fun selectCategory(categoryID: Int)

    suspend fun updateBasic(id: Int, minValue: Float?, maxValue: Float?)

    suspend fun updateNutrient(id: Int, minValue: Float?, maxValue: Float?)

    suspend fun updateLabel(id: Int, isSelected: Boolean)


    fun getCategory(
        categoryID: Int,
        metadata: List<LabelOfRecipeMetadata>
    ): Flow<State<CategoryOfSearchFilter>>

    fun getNutrients(
        metadata: List<NutrientOfRecipeMetadata>
    ): Flow<State<List<NutrientOfSearchFilter>>>


    fun getFilter(metadata: RecipeMetadata): Flow<State<SearchFilter>>

    fun getFilterPreset(metadata: RecipeMetadata): Flow<State<SearchFilterPreset>>

    fun getFilterPreset(metadata: RecipeMetadata, labelID: Int): Flow<State<SearchFilterPreset>>
}