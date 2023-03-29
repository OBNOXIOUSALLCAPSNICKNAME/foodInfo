package com.example.foodinfo.repository.use_case

import android.content.Context
import com.example.foodinfo.local.dao.APICredentialsDAO
import com.example.foodinfo.local.dto.RecipeAttrsDB
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.SearchFilterRepository
import com.example.foodinfo.repository.model.*
import com.example.foodinfo.repository.state_handling.State
import com.example.foodinfo.repository.state_handling.getResolved
import com.example.foodinfo.utils.*
import com.example.foodinfo.utils.extensions.hasInternet
import com.example.foodinfo.utils.edamam.FieldSet
import com.example.foodinfo.utils.paging.PageFetchHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject


class SearchFilterUseCase @Inject constructor(
    private val apiCredentialsDAO: APICredentialsDAO,
    private val recipeAttrRepository: RecipeAttrRepository,
    private val searchFilterRepository: SearchFilterRepository,
    private val prefUtils: PrefUtils,
    private val context: Context
) {

    private inline fun baseGetHelper(
        crossinline filterPresetProvider: (RecipeAttrsDB) -> Flow<State<SearchFilterPresetModel>>,
        crossinline pagingHelperProvider: (RecipeAttrsDB, SearchFilterPresetModel) -> PageFetchHelper
    ): Flow<State<PageFetchHelper>> {
        return getResolved(
            extraData = recipeAttrRepository.getRecipeAttrsDBLatest(),
            outputDataProvider = { attrs ->
                filterPresetProvider(attrs).transform { state ->
                    val helper = state.data?.let { pagingHelperProvider(attrs, it) }
                    when (state) {
                        is State.Error   -> emit(
                            State.Error(
                                state.messageID!!,
                                state.throwable!!,
                                state.errorCode!!
                            )
                        )
                        is State.Success -> emit(State.Success(helper!!))
                        is State.Loading -> emit(State.Loading(helper))
                    }
                }
            }
        )
    }

    fun getHelper(inputText: String = ""): Flow<State<PageFetchHelper>> {
        return baseGetHelper(
            filterPresetProvider = { attrs ->
                searchFilterRepository.getFilterPreset(attrs)
            },
            pagingHelperProvider = { attrs, preset ->
                PageFetchHelper(
                    attrs = attrs,
                    searchFilterPreset = preset,
                    apiCredentials = apiCredentialsDAO.getEdamam(prefUtils.edamamCredentials),
                    inputText = inputText,
                    fieldSet = FieldSet.FULL,
                    isOnline = context.hasInternet()
                )
            }
        )
    }

    fun getHelperByLabel(labelID: Int): Flow<State<PageFetchHelper>> {
        return baseGetHelper(
            filterPresetProvider = { attrs ->
                searchFilterRepository.getFilterPresetByLabel(labelID, attrs)
            },
            pagingHelperProvider = { attrs, preset ->
                PageFetchHelper(
                    attrs = attrs,
                    searchFilterPreset = preset,
                    apiCredentials = apiCredentialsDAO.getEdamam(prefUtils.edamamCredentials),
                    inputText = "",
                    fieldSet = FieldSet.FULL,
                    isOnline = context.hasInternet()
                )
            }
        )
    }

    fun getFilterEdit(): Flow<State<SearchFilterEditModel>> {
        return getResolved(
            extraData = recipeAttrRepository.getRecipeAttrsDBLatest(),
            outputDataProvider = { searchFilterRepository.getFilterEdit(it) }
        )
    }

    fun getCategoryEdit(categoryID: Int): Flow<State<CategoryOfSearchFilterEditModel>> {
        return getResolved(
            extraData = recipeAttrRepository.getLabelsDBLatest(),
            outputDataProvider = { searchFilterRepository.getCategoryEdit(categoryID, it) }
        )
    }

    fun getNutrientsEdit(): Flow<State<List<NutrientOfSearchFilterEditModel>>> {
        return getResolved(
            extraData = recipeAttrRepository.getNutrientsDBLatest(),
            outputDataProvider = { searchFilterRepository.getNutrientsEdit(it) }
        )
    }
}