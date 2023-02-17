package com.example.foodinfo.repository

import com.example.foodinfo.local.dao.RecipeAttrDAO
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
import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


class RecipeAttrRepository @Inject constructor(
    private val recipeAttrDAO: RecipeAttrDAO,
    private val recipeAttrAPI: RecipeAttrAPI
) : BaseRepository() {
    fun getNutrientHint(ID: Int): NutrientHintModel {
        return recipeAttrDAO.getNutrient(ID).toModelHint()
    }

    fun getLabelHint(ID: Int): LabelHintModel {
        return recipeAttrDAO.getLabel(ID).toModelHint()
    }

    fun getCategoryLabelsLatest(categoryID: Int): Flow<State<CategoryTargetSearchModel>> {
        return getData(
            remoteDataProvider = { recipeAttrAPI.getRecipeAttrs() },
            localDataFlowProvider = { recipeAttrDAO.observeCategoryLabels(categoryID) },
            updateLocalDelegate = { recipeAttrDAO.addRecipeAttrs(it) },
            mapToLocalDelegate = { it.toDB() },
            mapToModelDelegate = { it.toModelSearch() }
        )
    }

    fun getCategoriesLatest(): Flow<State<List<CategorySearchModel>>> {
        return getData(
            remoteDataProvider = { recipeAttrAPI.getRecipeAttrs() },
            localDataFlowProvider = { recipeAttrDAO.observeCategoriesAll() },
            updateLocalDelegate = { recipeAttrDAO.addRecipeAttrs(it) },
            mapToLocalDelegate = { it.toDB() },
            mapToModelDelegate = { it.map { label -> label.toModel() } }
        )
    }

    fun getLabelsDBLatest(): Flow<State<List<LabelRecipeAttrDB>>> {
        return getData(
            remoteDataProvider = { recipeAttrAPI.getRecipeAttrs() },
            localDataFlowProvider = { recipeAttrDAO.observeLabelsAll() },
            updateLocalDelegate = { recipeAttrDAO.addRecipeAttrs(it) },
            mapToLocalDelegate = { it.toDB() },
            mapToModelDelegate = { it }
        )
    }

    fun getNutrientsDBLatest(): Flow<State<List<NutrientRecipeAttrDB>>> {
        return getData(
            remoteDataProvider = { recipeAttrAPI.getRecipeAttrs() },
            localDataFlowProvider = { recipeAttrDAO.observeNutrientsAll() },
            updateLocalDelegate = { recipeAttrDAO.addRecipeAttrs(it) },
            mapToLocalDelegate = { it.toDB() },
            mapToModelDelegate = { it }
        )
    }

    fun getRecipeAttrsDBLatest(): Flow<State<RecipeAttrsDB>> {
        return getData(
            remoteDataProvider = { recipeAttrAPI.getRecipeAttrs() },
            localDataFlowProvider = {
                combine(
                    recipeAttrDAO.observeBasicsAll(),
                    recipeAttrDAO.observeLabelsAll(),
                    recipeAttrDAO.observeNutrientsAll(),
                    recipeAttrDAO.observeCategoriesAll()
                ) { basics, labels, nutrients, categories ->
                    RecipeAttrsDB(basics, labels, nutrients, categories)
                }
            },
            updateLocalDelegate = { recipeAttrDAO.addRecipeAttrs(it) },
            mapToLocalDelegate = { it.toDB() },
            mapToModelDelegate = { it }
        )
    }

    fun getRecipeAttrsDB(): Flow<State<RecipeAttrsDB>> {
        return getData(
            remoteDataProvider = { },
            localDataProvider = { recipeAttrDAO.getRecipeAttrs() },
            updateLocalDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = { it }
        )
    }
}