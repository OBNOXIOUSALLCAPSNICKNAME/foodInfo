package com.example.foodinfo.data.local.data_source

import com.example.foodinfo.data.local.model.*
import kotlinx.coroutines.flow.Flow


interface RecipeMetadataLocalSource {

    suspend fun getLabel(ID: Int): LabelOfRecipeMetadataDB

    suspend fun getNutrient(ID: Int): NutrientOfRecipeMetadataDB


    fun observeBasics(): Flow<List<BasicOfRecipeMetadataDB>>

    fun observeLabels(): Flow<List<LabelOfRecipeMetadataDB>>

    fun observeNutrients(): Flow<List<NutrientOfRecipeMetadataDB>>

    fun observeCategories(): Flow<List<CategoryOfRecipeMetadataDB>>

    fun observeCategoryLabels(categoryID: Int): Flow<List<LabelOfRecipeMetadataDB>>


    // add functions must remove all content and insert new one
    suspend fun addBasics(basics: List<BasicOfRecipeMetadataDB>)

    suspend fun addLabels(labels: List<LabelOfRecipeMetadataDB>)

    suspend fun addNutrients(nutrients: List<NutrientOfRecipeMetadataDB>)

    suspend fun addCategories(categories: List<CategoryOfRecipeMetadataDB>)

    suspend fun addRecipeMetadata(metadata: RecipeMetadataDB)
}