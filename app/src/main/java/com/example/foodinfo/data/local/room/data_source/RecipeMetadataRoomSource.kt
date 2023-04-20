package com.example.foodinfo.data.local.room.data_source

import com.example.foodinfo.data.local.data_source.RecipeMetadataLocalSource
import com.example.foodinfo.data.local.model.*
import com.example.foodinfo.data.local.room.dao.RecipeMetadataDAO
import com.example.foodinfo.data.local.room.model.entity.BasicOfRecipeMetadataEntity
import com.example.foodinfo.data.local.room.model.entity.CategoryOfRecipeMetadataEntity
import com.example.foodinfo.data.local.room.model.entity.LabelOfRecipeMetadataEntity
import com.example.foodinfo.data.local.room.model.entity.NutrientOfRecipeMetadataEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeMetadataRoomSource @Inject constructor(
    private val recipeMetadataDAO: RecipeMetadataDAO
) : RecipeMetadataLocalSource {
    override suspend fun getLabel(ID: Int): LabelOfRecipeMetadataDB {
        return recipeMetadataDAO.getLabel(ID)
    }

    override suspend fun getNutrient(ID: Int): NutrientOfRecipeMetadataDB {
        return recipeMetadataDAO.getNutrient(ID)
    }


    override fun observeBasics(): Flow<List<BasicOfRecipeMetadataDB>> {
        return recipeMetadataDAO.observeBasicsAll()
    }

    override fun observeLabels(): Flow<List<LabelOfRecipeMetadataDB>> {
        return recipeMetadataDAO.observeLabelsAll()
    }

    override fun observeNutrients(): Flow<List<NutrientOfRecipeMetadataDB>> {
        return recipeMetadataDAO.observeNutrientsAll()
    }

    override fun observeCategories(): Flow<List<CategoryOfRecipeMetadataDB>> {
        return recipeMetadataDAO.observeCategoriesAll()
    }

    override fun observeCategoryLabels(categoryID: Int): Flow<List<LabelOfRecipeMetadataDB>> {
        return recipeMetadataDAO.observeCategoryLabels(categoryID)
    }

    override suspend fun addBasics(basics: List<BasicOfRecipeMetadataDB>) {
        recipeMetadataDAO.addBasics(basics.map(BasicOfRecipeMetadataEntity::invoke))
    }

    override suspend fun addLabels(labels: List<LabelOfRecipeMetadataDB>) {
        recipeMetadataDAO.addLabels(labels.map(LabelOfRecipeMetadataEntity::invoke))
    }

    override suspend fun addNutrients(nutrients: List<NutrientOfRecipeMetadataDB>) {
        recipeMetadataDAO.addNutrients(nutrients.map(NutrientOfRecipeMetadataEntity::invoke))
    }

    override suspend fun addCategories(categories: List<CategoryOfRecipeMetadataDB>) {
        recipeMetadataDAO.addCategories(categories.map(CategoryOfRecipeMetadataEntity::invoke))
    }

    override suspend fun addRecipeMetadata(metadata: RecipeMetadataDB) {
        recipeMetadataDAO.addRecipeMetadata(
            basics = metadata.basics.map(BasicOfRecipeMetadataEntity::invoke),
            labels = metadata.labels.map(LabelOfRecipeMetadataEntity::invoke),
            nutrients = metadata.nutrients.map(NutrientOfRecipeMetadataEntity::invoke),
            categories = metadata.categories.map(CategoryOfRecipeMetadataEntity::invoke)
        )
    }
}