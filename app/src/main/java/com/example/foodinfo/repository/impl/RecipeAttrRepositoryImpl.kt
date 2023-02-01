package com.example.foodinfo.repository.impl

import com.example.foodinfo.local.dao.RecipeAttrDAO
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.mapper.toModel
import com.example.foodinfo.repository.mapper.toModelHint
import com.example.foodinfo.repository.mapper.toModelSearch
import com.example.foodinfo.repository.model.CategorySearchModel
import com.example.foodinfo.repository.model.LabelHintModel
import com.example.foodinfo.repository.model.LabelSearchModel
import com.example.foodinfo.repository.model.NutrientHintModel
import javax.inject.Inject


class RecipeAttrRepositoryImpl @Inject constructor(
    private val recipeAttrDao: RecipeAttrDAO
) : RecipeAttrRepository() {
    override fun getNutrientHint(ID: Int): NutrientHintModel {
        return recipeAttrDao.getNutrient(ID).toModelHint()
    }

    override fun getLabelHint(ID: Int): LabelHintModel {
        return recipeAttrDao.getLabel(ID).toModelHint()
    }

    override fun getLabelsSearch(categoryID: Int): List<LabelSearchModel> {
        return recipeAttrDao.getCategoryLabels(categoryID).map { it.toModelSearch() }
    }

    override fun getCategory(ID: Int): CategorySearchModel {
        return recipeAttrDao.getCategory(ID).toModel()
    }

    override fun getCategories(): List<CategorySearchModel> {
        return recipeAttrDao.getCategoriesAll().map { it.toModel() }
    }
}