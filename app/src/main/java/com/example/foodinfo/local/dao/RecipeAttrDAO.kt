package com.example.foodinfo.local.dao

import com.example.foodinfo.local.dto.BasicRecipeAttrDB
import com.example.foodinfo.local.dto.CategoryRecipeAttrDB
import com.example.foodinfo.local.dto.LabelRecipeAttrDB
import com.example.foodinfo.local.dto.NutrientRecipeAttrDB


interface RecipeAttrDAO {

    fun getNutrient(ID: Int): NutrientRecipeAttrDB


    fun getCategory(ID: Int): CategoryRecipeAttrDB


    fun getLabel(ID: Int): LabelRecipeAttrDB

    fun getCategoryLabels(categoryID: Int): List<LabelRecipeAttrDB>


    fun getCategoriesAll(): List<CategoryRecipeAttrDB>

    fun getNutrientsAll(): List<NutrientRecipeAttrDB>

    fun getBasicsAll(): List<BasicRecipeAttrDB>

    fun getLabelsAll(): List<LabelRecipeAttrDB>


    fun addNutrients(attrs: List<NutrientRecipeAttrDB>)

    fun addBasics(attrs: List<BasicRecipeAttrDB>)

    fun addLabels(attrs: List<LabelRecipeAttrDB>)

    fun addCategories(attrs: List<CategoryRecipeAttrDB>)
}