package com.example.foodinfo.local.dao

import com.example.foodinfo.local.dto.*
import kotlinx.coroutines.flow.Flow


interface RecipeAttrDAO {

    fun getLabel(ID: Int): LabelRecipeAttrDB

    fun getNutrient(ID: Int): NutrientRecipeAttrDB

    fun getCategory(ID: Int): CategoryRecipeAttrDB


    fun getBasicsAll(): List<@JvmWildcard BasicRecipeAttrDB>

    fun getLabelsAll(): List<@JvmWildcard LabelRecipeAttrDB>

    fun getNutrientsAll(): List<@JvmWildcard NutrientRecipeAttrDB>

    fun getCategoriesAll(): List<@JvmWildcard CategoryRecipeAttrDB>

    fun getRecipeAttrs(): RecipeAttrsDB


    fun observeBasicsAll(): Flow<@JvmWildcard List<@JvmWildcard BasicRecipeAttrDB>>

    fun observeLabelsAll(): Flow<@JvmWildcard List<@JvmWildcard LabelRecipeAttrDB>>

    fun observeNutrientsAll(): Flow<@JvmWildcard List<@JvmWildcard NutrientRecipeAttrDB>>

    fun observeCategoriesAll(): Flow<@JvmWildcard List<@JvmWildcard CategoryRecipeAttrDB>>

    fun observeCategoryLabels(categoryID: Int): Flow<@JvmWildcard List<@JvmWildcard LabelRecipeAttrExtendedDB>>


    // add functions must remove all content and insert new one
    suspend fun addBasics(attrs: List<BasicRecipeAttrDB>)

    suspend fun addLabels(attrs: List<LabelRecipeAttrDB>)

    suspend fun addNutrients(attrs: List<NutrientRecipeAttrDB>)

    suspend fun addCategories(attrs: List<CategoryRecipeAttrDB>)

    suspend fun addRecipeAttrs(attrs: RecipeAttrsDB)
}