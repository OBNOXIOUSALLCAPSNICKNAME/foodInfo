package com.example.foodinfo.domain.interactor

import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.State.Utils.suspendFlowProvider
import com.example.foodinfo.domain.model.CategoryRecipeAttr
import com.example.foodinfo.domain.model.LabelRecipeAttr
import com.example.foodinfo.domain.model.NutrientRecipeAttr
import com.example.foodinfo.domain.model.RecipeAttrs
import com.example.foodinfo.domain.repository.APICredentialsRepository
import com.example.foodinfo.domain.repository.RecipeAttrRepository
import com.example.foodinfo.utils.PrefUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeAttrsInteractor @Inject constructor(
    private val apiCredentialsRepository: APICredentialsRepository,
    private val recipeAttrRepository: RecipeAttrRepository,
    private val prefUtils: PrefUtils
) {

    fun getRecipeAttrs(): Flow<State<RecipeAttrs>> {
        return suspendFlowProvider {
            recipeAttrRepository.getRecipeAttrs(
                apiCredentialsRepository.getGitHub(prefUtils.githubCredentials)
            )
        }
    }

    fun getLabels(): Flow<State<List<LabelRecipeAttr>>> {
        return suspendFlowProvider {
            recipeAttrRepository.getLabels(
                apiCredentialsRepository.getGitHub(prefUtils.githubCredentials)
            )
        }
    }

    fun getNutrients(): Flow<State<List<NutrientRecipeAttr>>> {
        return suspendFlowProvider {
            recipeAttrRepository.getNutrients(
                apiCredentialsRepository.getGitHub(prefUtils.githubCredentials)
            )
        }
    }

    fun getCategories(): Flow<State<List<CategoryRecipeAttr>>> {
        return suspendFlowProvider {
            recipeAttrRepository.getCategories(
                apiCredentialsRepository.getGitHub(prefUtils.githubCredentials)
            )
        }
    }

    fun getCategoryLabels(categoryID: Int): Flow<State<List<LabelRecipeAttr>>> {
        return suspendFlowProvider {
            recipeAttrRepository.getCategoryLabels(
                apiCredentialsRepository.getGitHub(prefUtils.githubCredentials),
                categoryID
            )
        }
    }
}
