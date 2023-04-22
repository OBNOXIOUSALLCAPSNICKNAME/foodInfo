package com.example.foodinfo.data.repository

import com.example.foodinfo.data.local.data_source.RecipeMetadataLocalSource
import com.example.foodinfo.data.local.model.CategoryOfRecipeMetadataDB
import com.example.foodinfo.data.local.model.LabelOfRecipeMetadataDB
import com.example.foodinfo.data.local.model.NutrientOfRecipeMetadataDB
import com.example.foodinfo.data.local.model.RecipeMetadataDB
import com.example.foodinfo.data.mapper.toDB
import com.example.foodinfo.data.mapper.toModel
import com.example.foodinfo.data.mapper.toModelHint
import com.example.foodinfo.data.remote.data_source.RecipeMetadataRemoteSource
import com.example.foodinfo.data.remote.model.RecipeMetadataNetwork
import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.model.*
import com.example.foodinfo.domain.repository.RecipeMetadataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


class RecipeMetadataRepositoryImpl @Inject constructor(
    private val recipeMetadataLocal: RecipeMetadataLocalSource,
    private val recipeMetadataRemote: RecipeMetadataRemoteSource
) : BaseRepository(), RecipeMetadataRepository {

    override suspend fun getNutrientHint(ID: Int): NutrientHint {
        return recipeMetadataLocal.getNutrient(ID).toModelHint()
    }

    override suspend fun getLabelHint(ID: Int): LabelHint {
        return recipeMetadataLocal.getLabel(ID).toModelHint()
    }

    override fun getRecipeMetadata(apiCredentials: GitHubCredentials): Flow<State<RecipeMetadata>> {
        return getData(
            remoteDataProvider = { DataSource.Remote(recipeMetadataRemote.getRecipeMetadata(apiCredentials)) },
            localDataProvider = {
                DataSource.LocalFlow(
                    combine(
                        recipeMetadataLocal.observeBasics(),
                        recipeMetadataLocal.observeLabels(),
                        recipeMetadataLocal.observeNutrients(),
                        recipeMetadataLocal.observeCategories(),
                        ::RecipeMetadataDB
                    )
                )
            },
            saveRemoteDelegate = recipeMetadataLocal::addRecipeMetadata,
            mapToLocalDelegate = RecipeMetadataNetwork::toDB,
            mapToModelDelegate = RecipeMetadataDB::toModel
        )
    }

    override fun getLabels(apiCredentials: GitHubCredentials): Flow<State<List<LabelOfRecipeMetadata>>> {
        return getData(
            remoteDataProvider = { DataSource.Remote(recipeMetadataRemote.getRecipeMetadata(apiCredentials)) },
            localDataProvider = { DataSource.LocalFlow(recipeMetadataLocal.observeLabels()) },
            saveRemoteDelegate = recipeMetadataLocal::addRecipeMetadata,
            mapToLocalDelegate = RecipeMetadataNetwork::toDB,
            mapToModelDelegate = { labels -> labels.map(LabelOfRecipeMetadataDB::toModel) }
        )
    }

    override fun getNutrients(apiCredentials: GitHubCredentials): Flow<State<List<NutrientOfRecipeMetadata>>> {
        return getData(
            remoteDataProvider = { DataSource.Remote(recipeMetadataRemote.getRecipeMetadata(apiCredentials)) },
            localDataProvider = { DataSource.LocalFlow(recipeMetadataLocal.observeNutrients()) },
            saveRemoteDelegate = recipeMetadataLocal::addRecipeMetadata,
            mapToLocalDelegate = RecipeMetadataNetwork::toDB,
            mapToModelDelegate = { nutrients -> nutrients.map(NutrientOfRecipeMetadataDB::toModel) }
        )
    }

    override fun getCategories(apiCredentials: GitHubCredentials): Flow<State<List<CategoryOfRecipeMetadata>>> {
        return getData(
            remoteDataProvider = { DataSource.Remote(recipeMetadataRemote.getRecipeMetadata(apiCredentials)) },
            localDataProvider = { DataSource.LocalFlow(recipeMetadataLocal.observeCategories()) },
            saveRemoteDelegate = recipeMetadataLocal::addRecipeMetadata,
            mapToLocalDelegate = RecipeMetadataNetwork::toDB,
            mapToModelDelegate = { categories -> categories.map(CategoryOfRecipeMetadataDB::toModel) }
        )
    }

    override fun getCategoryLabels(
        apiCredentials: GitHubCredentials,
        categoryID: Int
    ): Flow<State<List<LabelOfRecipeMetadata>>> {
        return getData(
            remoteDataProvider = { DataSource.Remote(recipeMetadataRemote.getRecipeMetadata(apiCredentials)) },
            localDataProvider = { DataSource.LocalFlow(recipeMetadataLocal.observeCategoryLabels(categoryID)) },
            saveRemoteDelegate = recipeMetadataLocal::addRecipeMetadata,
            mapToLocalDelegate = RecipeMetadataNetwork::toDB,
            mapToModelDelegate = { labels -> labels.map(LabelOfRecipeMetadataDB::toModel) }
        )
    }
}