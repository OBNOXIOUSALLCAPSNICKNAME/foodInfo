package com.example.foodinfo.repository.impl

import android.content.Context
import com.example.foodinfo.local.dao.RecipeAttrDAO
import com.example.foodinfo.local.dto.*
import com.example.foodinfo.remote.api.RecipeAttrAPI
import com.example.foodinfo.repository.RecipeAttrRepository
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
import javax.inject.Inject


class RecipeAttrRepositoryImpl @Inject constructor(
    private val context: Context,
    private val recipeAttrDAO: RecipeAttrDAO,
    private val recipeAttrAPI: RecipeAttrAPI
) : RecipeAttrRepository() {
    override fun getNutrientHint(ID: Int): NutrientHintModel {
        return recipeAttrDAO.getNutrient(ID).toModelHint()
    }

    override fun getLabelHint(ID: Int): LabelHintModel {
        return recipeAttrDAO.getLabel(ID).toModelHint()
    }

    override fun getLabelsSearch(categoryID: Int): Flow<State<CategoryTargetSearchModel>> {
        return getData(
            context = context,
            remoteDataProvider = { recipeAttrAPI.getCategoryLabels(categoryID) },
            localDataProvider = { recipeAttrDAO.getCategoryLabels(categoryID) },
            updateLocalDelegate = { recipeAttrDAO.addLabels(it) },
            mapRemoteToLocalDelegate = { it.map { label -> label.toDB() } },
            mapLocalToModelDelegate = { it.toModelSearch() }
        )
    }

    override fun getCategory(ID: Int): Flow<State<CategorySearchModel>> {
        return getData(
            context = context,
            remoteDataProvider = { recipeAttrAPI.getCategory(ID) },
            localDataProvider = { recipeAttrDAO.getCategory(ID) },
            updateLocalDelegate = { recipeAttrDAO.addCategories(listOf(it)) },
            mapRemoteToLocalDelegate = { it.toDB() },
            mapLocalToModelDelegate = { it.toModel() }
        )
    }

    override fun getCategories(): Flow<State<List<CategorySearchModel>>> {
        return getData(
            context = context,
            remoteDataProvider = { recipeAttrAPI.getCategories() },
            localDataProvider = { recipeAttrDAO.getCategoriesAll() },
            updateLocalDelegate = { recipeAttrDAO.addCategories(it) },
            mapRemoteToLocalDelegate = { it.map { category -> category.toDB() } },
            mapLocalToModelDelegate = { it.map { label -> label.toModel() } }
        )
    }


    override fun getBasicsDB(): Flow<State<List<BasicRecipeAttrDB>>> {
        return getData(
            context = context,
            remoteDataProvider = { recipeAttrAPI.getBasics() },
            localDataProvider = { recipeAttrDAO.getBasicsAll() },
            updateLocalDelegate = { recipeAttrDAO.addBasics(it) },
            mapRemoteToLocalDelegate = { it.map { basic -> basic.toDB() } },
            mapLocalToModelDelegate = { it }
        )
    }

    override fun getLabelsDB(): Flow<State<List<LabelRecipeAttrDB>>> {
        return getData(
            context = context,
            remoteDataProvider = { recipeAttrAPI.getLabels() },
            localDataProvider = { recipeAttrDAO.getLabelsAll() },
            updateLocalDelegate = { recipeAttrDAO.addLabels(it) },
            mapRemoteToLocalDelegate = { it.map { label -> label.toDB() } },
            mapLocalToModelDelegate = { it }
        )
    }

    override fun getLabelsExtendedDB(): Flow<State<List<LabelRecipeAttrExtendedDB>>> {
        return getData(
            context = context,
            remoteDataProvider = { recipeAttrAPI.getLabels() },
            localDataProvider = { recipeAttrDAO.getLabelsExtendedAll() },
            updateLocalDelegate = { recipeAttrDAO.addLabels(it) },
            mapRemoteToLocalDelegate = { it.map { label -> label.toDB() } },
            mapLocalToModelDelegate = { it }
        )
    }

    override fun getNutrientsDB(): Flow<State<List<NutrientRecipeAttrDB>>> {
        return getData(
            context = context,
            remoteDataProvider = { recipeAttrAPI.getNutrients() },
            localDataProvider = { recipeAttrDAO.getNutrientsAll() },
            updateLocalDelegate = { recipeAttrDAO.addNutrients(it) },
            mapRemoteToLocalDelegate = { it.map { nutrient -> nutrient.toDB() } },
            mapLocalToModelDelegate = { it }
        )
    }

    override fun getCategoriesDB(): Flow<State<List<CategoryRecipeAttrDB>>> {
        return getData(
            context = context,
            remoteDataProvider = { recipeAttrAPI.getCategories() },
            localDataProvider = { recipeAttrDAO.getCategoriesAll() },
            updateLocalDelegate = { recipeAttrDAO.addCategories(it) },
            mapRemoteToLocalDelegate = { it.map { category -> category.toDB() } },
            mapLocalToModelDelegate = { it }
        )
    }

    override fun getRecipeAttrsDB(): Flow<State<RecipeAttrsDB>> {
        return getData(
            context = context,
            remoteDataProvider = { recipeAttrAPI.getRecipeAttrs() },
            localDataProvider = { recipeAttrDAO.getRecipeAttrs() },
            updateLocalDelegate = { recipeAttrDAO.addRecipeAttrs(it) },
            mapRemoteToLocalDelegate = { it.toDB() },
            mapLocalToModelDelegate = { it }
        )
    }
}