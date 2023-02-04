package com.example.foodinfo.repository.impl

import android.content.Context
import com.example.foodinfo.local.dao.RecipeAttrDAO
import com.example.foodinfo.local.dto.*
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.mapper.toModel
import com.example.foodinfo.repository.mapper.toModelHint
import com.example.foodinfo.repository.mapper.toModelSearch
import com.example.foodinfo.repository.model.CategorySearchModel
import com.example.foodinfo.repository.model.LabelHintModel
import com.example.foodinfo.repository.model.LabelSearchModel
import com.example.foodinfo.repository.model.NutrientHintModel
import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeAttrRepositoryImpl @Inject constructor(
    private val context: Context,
    private val recipeAttrDao: RecipeAttrDAO,
    //    private val recipeAPI: RecipeAPI
) : RecipeAttrRepository() {
    override fun getNutrientHint(ID: Int): NutrientHintModel {
        return recipeAttrDao.getNutrient(ID).toModelHint()
    }

    override fun getLabelHint(ID: Int): LabelHintModel {
        return recipeAttrDao.getLabel(ID).toModelHint()
    }

    override fun getLabelsSearch(categoryID: Int): Flow<State<List<LabelSearchModel>>> {
        return getLatest(
            context = context,
            fetchRemoteDelegate = { },
            fetchLocalOnceDelegate = { recipeAttrDao.getCategoryLabels(categoryID) },
            updateLocalDelegate = { },
            mapRemoteToLocalDelegate = { },
            mapLocalToModelDelegate = { it.map { label -> label.toModelSearch() } }
        )
    }

    override fun getCategory(ID: Int): Flow<State<CategorySearchModel>> {
        return getLatest(
            context = context,
            fetchRemoteDelegate = { },
            fetchLocalOnceDelegate = { recipeAttrDao.getCategory(ID) },
            updateLocalDelegate = { },
            mapRemoteToLocalDelegate = { },
            mapLocalToModelDelegate = { it.toModel() }
        )
    }

    override fun getCategories(): Flow<State<List<CategorySearchModel>>> {
        return getLatest(
            context = context,
            fetchRemoteDelegate = { },
            fetchLocalOnceDelegate = { recipeAttrDao.getCategoriesAll() },
            updateLocalDelegate = { },
            mapRemoteToLocalDelegate = { },
            mapLocalToModelDelegate = { it.map { label -> label.toModel() } }
        )
    }


    override fun getBasicsDB(): Flow<State<List<BasicRecipeAttrDB>>> {
        return getLatest(
            context = context,
            fetchRemoteDelegate = { },
            fetchLocalOnceDelegate = { recipeAttrDao.getBasicsAll() },
            updateLocalDelegate = { },
            mapRemoteToLocalDelegate = { },
            mapLocalToModelDelegate = { it }
        )
    }

    override fun getLabelsDB(): Flow<State<List<LabelRecipeAttrDB>>> {
        return getLatest(
            context = context,
            fetchRemoteDelegate = { },
            fetchLocalOnceDelegate = { recipeAttrDao.getLabelsAll() },
            updateLocalDelegate = { },
            mapRemoteToLocalDelegate = { },
            mapLocalToModelDelegate = { it }
        )
    }

    override fun getNutrientsDB(): Flow<State<List<NutrientRecipeAttrDB>>> {
        return getLatest(
            context = context,
            fetchRemoteDelegate = { },
            fetchLocalOnceDelegate = { recipeAttrDao.getNutrientsAll() },
            updateLocalDelegate = { },
            mapRemoteToLocalDelegate = { },
            mapLocalToModelDelegate = { it }
        )
    }

    override fun getCategoriesDB(): Flow<State<List<CategoryRecipeAttrDB>>> {
        return getLatest(
            context = context,
            fetchRemoteDelegate = { },
            fetchLocalOnceDelegate = { recipeAttrDao.getCategoriesAll() },
            updateLocalDelegate = { },
            mapRemoteToLocalDelegate = { },
            mapLocalToModelDelegate = { it }
        )
    }

    override fun getRecipeAttrsDB(): Flow<State<RecipeAttrsDB>> {
        return getLatest(
            context = context,
            fetchRemoteDelegate = { },
            fetchLocalOnceDelegate = { recipeAttrDao.getRecipeAttrs() },
            updateLocalDelegate = { },
            mapRemoteToLocalDelegate = { },
            mapLocalToModelDelegate = { it }
        )
    }
}