package com.example.foodinfo.local.dao

import com.example.foodinfo.local.dto.*


interface RecipeAttrDAO {

    fun getNutrient(ID: Int): NutrientRecipeAttrDB


    fun getCategory(ID: Int): CategoryRecipeAttrDB


    fun getLabel(ID: Int): LabelRecipeAttrDB

    fun getCategoryLabels(categoryID: Int): List<@JvmSuppressWildcards(suppress = false) LabelRecipeAttrDB>


    fun getRecipeAttrs(): RecipeAttrsDB


    fun getCategoriesAll(): List<@JvmSuppressWildcards(suppress = false) CategoryRecipeAttrDB>

    fun getNutrientsAll(): List<@JvmSuppressWildcards(suppress = false) NutrientRecipeAttrDB>

    fun getBasicsAll(): List<@JvmSuppressWildcards(suppress = false) BasicRecipeAttrDB>

    fun getLabelsAll(): List<@JvmSuppressWildcards(suppress = false) LabelRecipeAttrDB>

    fun getLabelsExtendedAll(): List<@JvmSuppressWildcards(suppress = false) LabelRecipeAttrExtendedDB>


    fun addNutrients(attrs: List<NutrientRecipeAttrDB>)

    fun addBasics(attrs: List<BasicRecipeAttrDB>)

    fun addLabels(attrs: List<LabelRecipeAttrDB>)

    fun addCategories(attrs: List<CategoryRecipeAttrDB>)

    fun addRecipeAttrs(attrs: RecipeAttrsDB)
}