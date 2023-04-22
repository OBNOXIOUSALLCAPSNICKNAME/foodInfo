package com.example.foodinfo.domain.interactor

import com.example.foodinfo.core.utils.PrefUtils
import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.State.Utils.suspendFlowProvider
import com.example.foodinfo.domain.model.CategoryOfRecipeMetadata
import com.example.foodinfo.domain.model.LabelOfRecipeMetadata
import com.example.foodinfo.domain.model.NutrientOfRecipeMetadata
import com.example.foodinfo.domain.model.RecipeMetadata
import com.example.foodinfo.domain.repository.APICredentialsRepository
import com.example.foodinfo.domain.repository.RecipeMetadataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeMetadataInteractor @Inject constructor(
    private val apiCredentialsRepository: APICredentialsRepository,
    private val recipeMetadataRepository: RecipeMetadataRepository,
    private val prefUtils: PrefUtils
) {

    fun getRecipeMetadata(): Flow<State<RecipeMetadata>> {
        return suspendFlowProvider {
            recipeMetadataRepository.getRecipeMetadata(
                apiCredentialsRepository.getGitHub(prefUtils.githubCredentials)
            )
        }
    }

    fun getLabels(): Flow<State<List<LabelOfRecipeMetadata>>> {
        return suspendFlowProvider {
            recipeMetadataRepository.getLabels(
                apiCredentialsRepository.getGitHub(prefUtils.githubCredentials)
            )
        }
    }

    fun getNutrients(): Flow<State<List<NutrientOfRecipeMetadata>>> {
        return suspendFlowProvider {
            recipeMetadataRepository.getNutrients(
                apiCredentialsRepository.getGitHub(prefUtils.githubCredentials)
            )
        }
    }

    fun getCategories(): Flow<State<List<CategoryOfRecipeMetadata>>> {
        return suspendFlowProvider {
            recipeMetadataRepository.getCategories(
                apiCredentialsRepository.getGitHub(prefUtils.githubCredentials)
            )
        }
    }

    fun getCategoryLabels(categoryID: Int): Flow<State<List<LabelOfRecipeMetadata>>> {
        return suspendFlowProvider {
            recipeMetadataRepository.getCategoryLabels(
                apiCredentialsRepository.getGitHub(prefUtils.githubCredentials),
                categoryID
            )
        }
    }
}
