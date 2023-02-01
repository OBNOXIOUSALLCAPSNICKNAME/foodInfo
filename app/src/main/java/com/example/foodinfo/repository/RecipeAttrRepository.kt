package com.example.foodinfo.repository

import com.example.foodinfo.repository.model.CategorySearchModel
import com.example.foodinfo.repository.model.LabelHintModel
import com.example.foodinfo.repository.model.LabelSearchModel
import com.example.foodinfo.repository.model.NutrientHintModel


abstract class RecipeAttrRepository : BaseRepository() {

    abstract fun getNutrientHint(ID: Int): NutrientHintModel

    abstract fun getLabelHint(ID: Int): LabelHintModel

    abstract fun getLabelsSearch(categoryID: Int): List<LabelSearchModel>

    abstract fun getCategory(ID: Int): CategorySearchModel

    abstract fun getCategories(): List<CategorySearchModel>
}