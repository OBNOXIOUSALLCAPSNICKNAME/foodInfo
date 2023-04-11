package com.example.foodinfo.domain.repository

import com.example.foodinfo.local.data_source.APICredentialsLocalSource
import com.example.foodinfo.local.data_source.RecipeAttrLocalSource
import com.example.foodinfo.local.model.GitHubCredentialsDB
import com.example.foodinfo.local.model.LabelRecipeAttrDB
import com.example.foodinfo.local.model.NutrientRecipeAttrDB
import com.example.foodinfo.local.model.RecipeAttrsDB
import com.example.foodinfo.remote.data_source.RecipeAttrRemoteSource
import com.example.foodinfo.domain.mapper.toDB
import com.example.foodinfo.domain.mapper.toModel
import com.example.foodinfo.domain.mapper.toModelHint
import com.example.foodinfo.domain.mapper.toModelSearch
import com.example.foodinfo.domain.model.CategorySearchModel
import com.example.foodinfo.domain.model.CategoryTargetSearchModel
import com.example.foodinfo.domain.model.LabelHintModel
import com.example.foodinfo.domain.model.NutrientHintModel
import com.example.foodinfo.domain.state.BaseRepository
import com.example.foodinfo.domain.state.DataProvider
import com.example.foodinfo.domain.state.State
import com.example.foodinfo.utils.PrefUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


class RecipeAttrRepository @Inject constructor(
    private val apiCredentialsLocal: APICredentialsLocalSource,
    private val recipeAttrLocal: RecipeAttrLocalSource,
    private val recipeAttrRemote: RecipeAttrRemoteSource,
    private val prefUtils: PrefUtils
) : BaseRepository() {

    suspend fun getNutrientHint(ID: Int): NutrientHintModel {
        return recipeAttrLocal.getNutrient(ID).toModelHint()
    }

    suspend fun getLabelHint(ID: Int): LabelHintModel {
        return recipeAttrLocal.getLabel(ID).toModelHint()
    }

    fun getCategoryLabelsLatest(categoryID: Int): Flow<State<CategoryTargetSearchModel>> {
        return getData(
            remoteDataProvider = { DataProvider.Remote(recipeAttrRemote.getRecipeAttrs(getToken())) },
            localDataProvider = { DataProvider.LocalFlow(recipeAttrLocal.observeCategoryLabels(categoryID)) },
            saveRemoteDelegate = { recipeAttrLocal.addRecipeAttrs(it) },
            mapToLocalDelegate = { it.toDB() },
            mapToModelDelegate = { it.toModelSearch() }
        )
    }

    fun getCategoriesLatest(): Flow<State<List<CategorySearchModel>>> {
        return getData(
            remoteDataProvider = { DataProvider.Remote(recipeAttrRemote.getRecipeAttrs(getToken())) },
            localDataProvider = { DataProvider.LocalFlow(recipeAttrLocal.observeCategoriesAll()) },
            saveRemoteDelegate = { recipeAttrLocal.addRecipeAttrs(it) },
            mapToLocalDelegate = { it.toDB() },
            mapToModelDelegate = { it.map { label -> label.toModel() } }
        )
    }

    fun getLabelsDBLatest(): Flow<State<List<LabelRecipeAttrDB>>> {
        return getData(
            remoteDataProvider = { DataProvider.Remote(recipeAttrRemote.getRecipeAttrs(getToken())) },
            localDataProvider = { DataProvider.LocalFlow(recipeAttrLocal.observeLabelsAll()) },
            saveRemoteDelegate = { recipeAttrLocal.addRecipeAttrs(it) },
            mapToLocalDelegate = { it.toDB() },
            mapToModelDelegate = { it }
        )
    }

    fun getNutrientsDBLatest(): Flow<State<List<NutrientRecipeAttrDB>>> {
        return getData(
            remoteDataProvider = { DataProvider.Remote(recipeAttrRemote.getRecipeAttrs(getToken())) },
            localDataProvider = { DataProvider.LocalFlow(recipeAttrLocal.observeNutrientsAll()) },
            saveRemoteDelegate = { recipeAttrLocal.addRecipeAttrs(it) },
            mapToLocalDelegate = { it.toDB() },
            mapToModelDelegate = { it }
        )
    }

    fun getRecipeAttrsDBLatest(): Flow<State<RecipeAttrsDB>> {
        return getData(
            remoteDataProvider = { DataProvider.Remote(recipeAttrRemote.getRecipeAttrs(getToken())) },
            localDataProvider = {
                DataProvider.LocalFlow(
                    combine(
                        recipeAttrLocal.observeBasicsAll(),
                        recipeAttrLocal.observeLabelsAll(),
                        recipeAttrLocal.observeNutrientsAll(),
                        recipeAttrLocal.observeCategoriesAll(),
                        ::RecipeAttrsDB
                    )
                )
            },
            saveRemoteDelegate = { recipeAttrLocal.addRecipeAttrs(it) },
            mapToLocalDelegate = { it.toDB() },
            mapToModelDelegate = { it }
        )
    }


    private suspend fun getToken(): String {
        return GitHubCredentialsDB.TOKEN_PREFIX +
        apiCredentialsLocal.getGitHub(prefUtils.githubCredentials).token
    }
}