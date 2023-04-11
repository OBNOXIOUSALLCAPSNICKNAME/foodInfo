package com.example.foodinfo.domain.use_case

import android.content.Context
import com.example.foodinfo.local.data_source.APICredentialsLocalSource
import com.example.foodinfo.local.model.RecipeAttrsDB
import com.example.foodinfo.domain.repository.RecipeAttrRepository
import com.example.foodinfo.domain.repository.SearchFilterRepository
import com.example.foodinfo.domain.model.CategoryOfSearchFilterEditModel
import com.example.foodinfo.domain.model.NutrientOfSearchFilterEditModel
import com.example.foodinfo.domain.model.SearchFilterEditModel
import com.example.foodinfo.domain.model.SearchFilterPresetModel
import com.example.foodinfo.domain.state.State
import com.example.foodinfo.domain.state.getResolved
import com.example.foodinfo.utils.PrefUtils
import com.example.foodinfo.utils.edamam.FieldSet
import com.example.foodinfo.utils.extensions.hasInternet
import com.example.foodinfo.utils.paging.PageFetchHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject


class SearchFilterUseCase @Inject constructor(
    private val apiCredentialsLocal: APICredentialsLocalSource,
    private val recipeAttrRepository: RecipeAttrRepository,
    private val searchFilterRepository: SearchFilterRepository,
    private val prefUtils: PrefUtils,
    private val context: Context
) {

    private inline fun baseGetHelper(
        crossinline filterPresetProvider: (RecipeAttrsDB) -> Flow<State<SearchFilterPresetModel>>,
        crossinline pagingHelperProvider: suspend (RecipeAttrsDB, SearchFilterPresetModel) -> PageFetchHelper
    ): Flow<State<PageFetchHelper>> {
        return getResolved(
            extraData = recipeAttrRepository.getRecipeAttrsDBLatest(),
            outputDataProvider = { attrs ->
                filterPresetProvider(attrs).transform { state ->
                    val helper = state.data?.let { pagingHelperProvider(attrs, it) }
                    when (state) {
                        is State.Failure -> emit(
                            State.Failure(
                                state.messageID!!,
                                state.throwable!!,
                                state.errorCode!!
                            )
                        )
                        is State.Success -> emit(State.Success(helper!!))
                        is State.Loading -> emit(State.Loading(helper!!))
                        is State.Initial -> emit(State.Initial())
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
                    apiCredentials = apiCredentialsLocal.getEdamam(prefUtils.edamamCredentials),
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
                    apiCredentials = apiCredentialsLocal.getEdamam(prefUtils.edamamCredentials),
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