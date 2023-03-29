package com.example.foodinfo.repository

import com.example.foodinfo.local.dao.APICredentialsDAO
import com.example.foodinfo.local.dao.RecipeAttrDAO
import com.example.foodinfo.local.dto.GitHubCredentialsDB
import com.example.foodinfo.local.dto.LabelRecipeAttrDB
import com.example.foodinfo.local.dto.NutrientRecipeAttrDB
import com.example.foodinfo.local.dto.RecipeAttrsDB
import com.example.foodinfo.remote.api.RecipeAttrAPI
import com.example.foodinfo.repository.mapper.toDB
import com.example.foodinfo.repository.mapper.toModel
import com.example.foodinfo.repository.mapper.toModelHint
import com.example.foodinfo.repository.mapper.toModelSearch
import com.example.foodinfo.repository.model.CategorySearchModel
import com.example.foodinfo.repository.model.CategoryTargetSearchModel
import com.example.foodinfo.repository.model.LabelHintModel
import com.example.foodinfo.repository.model.NutrientHintModel
import com.example.foodinfo.repository.state_handling.BaseRepository
import com.example.foodinfo.repository.state_handling.DataProvider
import com.example.foodinfo.repository.state_handling.State
import com.example.foodinfo.utils.PrefUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


class RecipeAttrRepository @Inject constructor(
    private val apiCredentialsDAO: APICredentialsDAO,
    private val recipeAttrDAO: RecipeAttrDAO,
    private val recipeAttrAPI: RecipeAttrAPI,
    private val prefUtils: PrefUtils
) : BaseRepository() {

    private val authToken: String
        get() =
            GitHubCredentialsDB.TOKEN_PREFIX +
            apiCredentialsDAO.getGitHub(prefUtils.githubCredentials).token


    fun getNutrientHint(ID: Int): NutrientHintModel {
        return recipeAttrDAO.getNutrient(ID).toModelHint()
    }

    fun getLabelHint(ID: Int): LabelHintModel {
        return recipeAttrDAO.getLabel(ID).toModelHint()
    }

    fun getCategoryLabelsLatest(categoryID: Int): Flow<State<CategoryTargetSearchModel>> {
        return getData(
            remoteDataProvider = { DataProvider.Remote(recipeAttrAPI.getRecipeAttrs(authToken)) },
            localDataProvider = { DataProvider.LocalFlow(recipeAttrDAO.observeCategoryLabels(categoryID)) },
            saveRemoteDelegate = { recipeAttrDAO.addRecipeAttrs(it) },
            mapToLocalDelegate = { it.toDB() },
            mapToModelDelegate = { it.toModelSearch() }
        )
    }

    fun getCategoriesLatest(): Flow<State<List<CategorySearchModel>>> {
        return getData(
            remoteDataProvider = { DataProvider.Remote(recipeAttrAPI.getRecipeAttrs(authToken)) },
            localDataProvider = { DataProvider.LocalFlow(recipeAttrDAO.observeCategoriesAll()) },
            saveRemoteDelegate = { recipeAttrDAO.addRecipeAttrs(it) },
            mapToLocalDelegate = { it.toDB() },
            mapToModelDelegate = { it.map { label -> label.toModel() } }
        )
    }

    fun getLabelsDBLatest(): Flow<State<List<LabelRecipeAttrDB>>> {
        return getData(
            remoteDataProvider = { DataProvider.Remote(recipeAttrAPI.getRecipeAttrs(authToken)) },
            localDataProvider = { DataProvider.LocalFlow(recipeAttrDAO.observeLabelsAll()) },
            saveRemoteDelegate = { recipeAttrDAO.addRecipeAttrs(it) },
            mapToLocalDelegate = { it.toDB() },
            mapToModelDelegate = { it }
        )
    }

    fun getNutrientsDBLatest(): Flow<State<List<NutrientRecipeAttrDB>>> {
        return getData(
            remoteDataProvider = { DataProvider.Remote(recipeAttrAPI.getRecipeAttrs(authToken)) },
            localDataProvider = { DataProvider.LocalFlow(recipeAttrDAO.observeNutrientsAll()) },
            saveRemoteDelegate = { recipeAttrDAO.addRecipeAttrs(it) },
            mapToLocalDelegate = { it.toDB() },
            mapToModelDelegate = { it }
        )
    }

    fun getRecipeAttrsDBLatest(): Flow<State<RecipeAttrsDB>> {
        return getData(
            remoteDataProvider = { DataProvider.Remote(recipeAttrAPI.getRecipeAttrs(authToken)) },
            localDataProvider = {
                DataProvider.LocalFlow(
                    combine(
                        recipeAttrDAO.observeBasicsAll(),
                        recipeAttrDAO.observeLabelsAll(),
                        recipeAttrDAO.observeNutrientsAll(),
                        recipeAttrDAO.observeCategoriesAll(),
                        ::RecipeAttrsDB
                    )
                )
            },
            saveRemoteDelegate = { recipeAttrDAO.addRecipeAttrs(it) },
            mapToLocalDelegate = { it.toDB() },
            mapToModelDelegate = { it }
        )
    }

    fun getRecipeAttrsDB(): Flow<State<RecipeAttrsDB>> {
        return getData(
            remoteDataProvider = { DataProvider.Empty },
            localDataProvider = { DataProvider.Local(recipeAttrDAO.getRecipeAttrs()) },
            saveRemoteDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = { it }
        )
    }
}