package com.example.foodinfo.domain.repository

import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.model.*
import kotlinx.coroutines.flow.Flow


interface RecipeAttrRepository {

    suspend fun getNutrientHint(ID: Int): NutrientHint

    suspend fun getLabelHint(ID: Int): LabelHint

    fun getRecipeAttrs(apiCredentials: GitHubCredentials): Flow<State<RecipeAttrs>>

    fun getLabels(apiCredentials: GitHubCredentials): Flow<State<List<LabelRecipeAttr>>>

    fun getNutrients(apiCredentials: GitHubCredentials): Flow<State<List<NutrientRecipeAttr>>>

    fun getCategories(apiCredentials: GitHubCredentials): Flow<State<List<CategoryRecipeAttr>>>

    fun getCategoryLabels(
        apiCredentials: GitHubCredentials,
        categoryID: Int
    ): Flow<State<List<LabelRecipeAttr>>>
}