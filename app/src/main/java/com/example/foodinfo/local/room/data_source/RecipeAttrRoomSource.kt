package com.example.foodinfo.local.room.data_source

import com.example.foodinfo.local.data_source.RecipeAttrLocalSource
import com.example.foodinfo.local.model.*
import com.example.foodinfo.local.room.dao.RecipeAttrDAO
import com.example.foodinfo.local.room.model.entity.BasicRecipeAttrEntity
import com.example.foodinfo.local.room.model.entity.CategoryRecipeAttrEntity
import com.example.foodinfo.local.room.model.entity.LabelRecipeAttrEntity
import com.example.foodinfo.local.room.model.entity.NutrientRecipeAttrEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeAttrRoomSource @Inject constructor(
    private val recipeAttrDAO: RecipeAttrDAO
) : RecipeAttrLocalSource {
    override suspend fun getLabel(ID: Int): LabelRecipeAttrDB {
        return recipeAttrDAO.getLabel(ID)
    }

    override suspend fun getNutrient(ID: Int): NutrientRecipeAttrDB {
        return recipeAttrDAO.getNutrient(ID)
    }

    override suspend fun getCategory(ID: Int): CategoryRecipeAttrDB {
        return recipeAttrDAO.getCategory(ID)
    }

    override fun observeBasicsAll(): Flow<List<BasicRecipeAttrDB>> {
        return recipeAttrDAO.observeBasicsAll()
    }

    override fun observeLabelsAll(): Flow<List<LabelRecipeAttrDB>> {
        return recipeAttrDAO.observeLabelsAll()
    }

    override fun observeNutrientsAll(): Flow<List<NutrientRecipeAttrDB>> {
        return recipeAttrDAO.observeNutrientsAll()
    }

    override fun observeCategoriesAll(): Flow<List<CategoryRecipeAttrDB>> {
        return recipeAttrDAO.observeCategoriesAll()
    }

    override fun observeCategoryLabels(categoryID: Int): Flow<List<LabelRecipeAttrExtendedDB>> {
        return recipeAttrDAO.observeCategoryLabels(categoryID)
    }

    override suspend fun addBasics(attrs: List<BasicRecipeAttrDB>) {
        recipeAttrDAO.addBasics(attrs.map(BasicRecipeAttrEntity::invoke))
    }

    override suspend fun addLabels(attrs: List<LabelRecipeAttrDB>) {
        recipeAttrDAO.addLabels(attrs.map(LabelRecipeAttrEntity::invoke))
    }

    override suspend fun addNutrients(attrs: List<NutrientRecipeAttrDB>) {
        recipeAttrDAO.addNutrients(attrs.map(NutrientRecipeAttrEntity::invoke))
    }

    override suspend fun addCategories(attrs: List<CategoryRecipeAttrDB>) {
        recipeAttrDAO.addCategories(attrs.map(CategoryRecipeAttrEntity::invoke))
    }

    override suspend fun addRecipeAttrs(attrs: RecipeAttrsDB) {
        recipeAttrDAO.addRecipeAttrs(
            basics = attrs.basics.map(BasicRecipeAttrEntity::invoke),
            labels = attrs.labels.map(LabelRecipeAttrEntity::invoke),
            nutrients = attrs.nutrients.map(NutrientRecipeAttrEntity::invoke),
            categories = attrs.categories.map(CategoryRecipeAttrEntity::invoke)
        )
    }
}