package com.example.foodinfo.repository

import com.example.foodinfo.repository.model.CategorySearchModel
import com.example.foodinfo.repository.model.LabelHintModel
import com.example.foodinfo.repository.model.LabelSearchModel
import com.example.foodinfo.repository.model.NutrientHintModel


interface RecipeAttrRepository {

    fun getNutrientHint(ID: Int): NutrientHintModel

    fun getLabelHint(ID: Int): LabelHintModel

    fun getLabelsSearch(categoryID: Int): List<LabelSearchModel>

    fun getCategory(ID: Int): CategorySearchModel

    fun getCategories(): List<CategorySearchModel>
}