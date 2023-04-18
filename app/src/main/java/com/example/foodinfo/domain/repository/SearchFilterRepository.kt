package com.example.foodinfo.domain.repository

import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.model.*
import kotlinx.coroutines.flow.Flow


interface SearchFilterRepository {

    suspend fun resetFilter()

    suspend fun resetNutrients()

    suspend fun resetCategory(categoryID: Int)

    suspend fun updateBasic(id: Int, minValue: Float?, maxValue: Float?)

    suspend fun updateNutrient(id: Int, minValue: Float?, maxValue: Float?)

    suspend fun updateLabel(id: Int, isSelected: Boolean)


    fun getCategory(
        categoryID: Int,
        attrs: List<LabelRecipeAttr>
    ): Flow<State<CategoryOfSearchFilter>>

    fun getNutrients(
        attrs: List<NutrientRecipeAttr>
    ): Flow<State<List<NutrientOfSearchFilter>>>


    fun getFilter(attrs: RecipeAttrs): Flow<State<SearchFilter>>

    fun getFilterPreset(attrs: RecipeAttrs): Flow<State<SearchFilterPreset>>

    fun getFilterPreset(attrs: RecipeAttrs, labelID: Int): Flow<State<SearchFilterPreset>>
}