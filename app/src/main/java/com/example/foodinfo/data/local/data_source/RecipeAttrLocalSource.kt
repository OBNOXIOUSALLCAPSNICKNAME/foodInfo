package com.example.foodinfo.data.local.data_source

import com.example.foodinfo.data.local.model.*
import kotlinx.coroutines.flow.Flow


interface RecipeAttrLocalSource {

    suspend fun getLabel(ID: Int): LabelRecipeAttrDB

    suspend fun getNutrient(ID: Int): NutrientRecipeAttrDB


    fun observeBasics(): Flow<List<BasicRecipeAttrDB>>

    fun observeLabels(): Flow<List<LabelRecipeAttrDB>>

    fun observeNutrients(): Flow<List<NutrientRecipeAttrDB>>

    fun observeCategories(): Flow<List<CategoryRecipeAttrDB>>

    fun observeCategoryLabels(categoryID: Int): Flow<List<LabelRecipeAttrDB>>


    // add functions must remove all content and insert new one
    suspend fun addBasics(attrs: List<BasicRecipeAttrDB>)

    suspend fun addLabels(attrs: List<LabelRecipeAttrDB>)

    suspend fun addNutrients(attrs: List<NutrientRecipeAttrDB>)

    suspend fun addCategories(attrs: List<CategoryRecipeAttrDB>)

    suspend fun addRecipeAttrs(attrs: RecipeAttrsDB)
}