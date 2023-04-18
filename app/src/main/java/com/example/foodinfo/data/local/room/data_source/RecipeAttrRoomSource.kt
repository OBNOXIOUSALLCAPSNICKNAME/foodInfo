package com.example.foodinfo.data.local.room.data_source

import com.example.foodinfo.data.local.data_source.RecipeAttrLocalSource
import com.example.foodinfo.data.local.model.*
import com.example.foodinfo.data.local.room.dao.RecipeAttrDAO
import com.example.foodinfo.data.local.room.model.entity.BasicRecipeAttrEntity
import com.example.foodinfo.data.local.room.model.entity.CategoryRecipeAttrEntity
import com.example.foodinfo.data.local.room.model.entity.LabelRecipeAttrEntity
import com.example.foodinfo.data.local.room.model.entity.NutrientRecipeAttrEntity
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


    override fun observeBasics(): Flow<List<BasicRecipeAttrDB>> {
        return recipeAttrDAO.observeBasicsAll()
    }

    override fun observeLabels(): Flow<List<LabelRecipeAttrDB>> {
        return recipeAttrDAO.observeLabelsAll()
    }

    override fun observeNutrients(): Flow<List<NutrientRecipeAttrDB>> {
        return recipeAttrDAO.observeNutrientsAll()
    }

    override fun observeCategories(): Flow<List<CategoryRecipeAttrDB>> {
        return recipeAttrDAO.observeCategoriesAll()
    }

    override fun observeCategoryLabels(categoryID: Int): Flow<List<LabelRecipeAttrDB>> {
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