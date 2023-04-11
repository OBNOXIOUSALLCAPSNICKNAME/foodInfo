package com.example.foodinfo.local.data_source

import com.example.foodinfo.local.model.*
import kotlinx.coroutines.flow.Flow


interface RecipeAttrLocalSource {

    suspend fun getLabel(ID: Int): LabelRecipeAttrDB

    suspend fun getNutrient(ID: Int): NutrientRecipeAttrDB

    suspend fun getCategory(ID: Int): CategoryRecipeAttrDB


    fun observeBasicsAll(): Flow<List<BasicRecipeAttrDB>>

    fun observeLabelsAll(): Flow<List<LabelRecipeAttrDB>>

    fun observeNutrientsAll(): Flow<List<NutrientRecipeAttrDB>>

    fun observeCategoriesAll(): Flow<List<CategoryRecipeAttrDB>>

    fun observeCategoryLabels(categoryID: Int): Flow<List<LabelRecipeAttrExtendedDB>>


    // add functions must remove all content and insert new one
    suspend fun addBasics(attrs: List<BasicRecipeAttrDB>)

    suspend fun addLabels(attrs: List<LabelRecipeAttrDB>)

    suspend fun addNutrients(attrs: List<NutrientRecipeAttrDB>)

    suspend fun addCategories(attrs: List<CategoryRecipeAttrDB>)

    suspend fun addRecipeAttrs(attrs: RecipeAttrsDB)
}