package com.example.foodinfo.domain.repository

import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.model.*
import kotlinx.coroutines.flow.Flow


interface RecipeMetadataRepository {

    suspend fun getNutrientHint(ID: Int): NutrientHint

    suspend fun getLabelHint(ID: Int): LabelHint

    fun getRecipeMetadata(apiCredentials: GitHubCredentials): Flow<State<RecipeMetadata>>

    fun getLabels(apiCredentials: GitHubCredentials): Flow<State<List<LabelOfRecipeMetadata>>>

    fun getNutrients(apiCredentials: GitHubCredentials): Flow<State<List<NutrientOfRecipeMetadata>>>

    fun getCategories(apiCredentials: GitHubCredentials): Flow<State<List<CategoryOfRecipeMetadata>>>

    fun getCategoryLabels(
        apiCredentials: GitHubCredentials,
        categoryID: Int
    ): Flow<State<List<LabelOfRecipeMetadata>>>
}