package com.example.foodinfo.local.dao

import com.example.foodinfo.local.dto.*


interface RecipeAttrDAO {

    fun getNutrient(ID: Int): NutrientRecipeAttrDB


    fun getCategory(ID: Int): CategoryRecipeAttrDB


    fun getLabel(ID: Int): LabelRecipeAttrDB

    fun getCategoryLabels(categoryID: Int): List<@JvmWildcard LabelRecipeAttrDB>


    fun getRecipeAttrs(): RecipeAttrsDB


    fun getCategoriesAll(): List<@JvmWildcard CategoryRecipeAttrDB>

    fun getNutrientsAll(): List<@JvmWildcard NutrientRecipeAttrDB>

    fun getBasicsAll(): List<@JvmWildcard BasicRecipeAttrDB>

    fun getLabelsAll(): List<@JvmWildcard LabelRecipeAttrDB>

    fun getLabelsExtendedAll(): List<@JvmWildcard LabelRecipeAttrExtendedDB>


    fun addNutrients(attrs: List<NutrientRecipeAttrDB>)

    fun addBasics(attrs: List<BasicRecipeAttrDB>)

    fun addLabels(attrs: List<LabelRecipeAttrDB>)

    fun addCategories(attrs: List<CategoryRecipeAttrDB>)

    fun addRecipeAttrs(attrs: RecipeAttrsDB)
}