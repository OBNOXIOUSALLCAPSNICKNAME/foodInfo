package com.example.foodinfo.data.repository

import com.example.foodinfo.data.local.data_source.SearchFilterLocalSource
import com.example.foodinfo.data.local.model.*
import com.example.foodinfo.data.mapper.*
import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.model.*
import com.example.foodinfo.domain.repository.SearchFilterRepository
import com.example.foodinfo.utils.PrefUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject


class SearchFilterRepositoryImpl @Inject constructor(
    private val searchFilterLocal: SearchFilterLocalSource,
    private val prefUtils: PrefUtils
) : BaseRepository(), SearchFilterRepository {

    override suspend fun resetFilter() {
        searchFilterLocal.resetFilter(prefUtils.searchFilter)
    }

    override suspend fun resetNutrients() {
        searchFilterLocal.resetNutrients(prefUtils.searchFilter)
    }

    override suspend fun resetCategory(categoryID: Int) {
        searchFilterLocal.resetCategory(prefUtils.searchFilter, categoryID)
    }

    override suspend fun updateBasic(id: Int, minValue: Float?, maxValue: Float?) {
        searchFilterLocal.updateBasic(id, minValue, maxValue)
    }

    override suspend fun updateNutrient(id: Int, minValue: Float?, maxValue: Float?) {
        searchFilterLocal.updateNutrient(id, minValue, maxValue)
    }

    override suspend fun updateLabel(id: Int, isSelected: Boolean) {
        searchFilterLocal.updateLabel(id, isSelected)
    }


    override fun getCategory(
        categoryID: Int,
        attrs: List<LabelRecipeAttr>
    ): Flow<State<CategoryOfSearchFilter>> {
        return getData(
            remoteDataProvider = { DataSource.Empty },
            localDataProvider = {
                DataSource.LocalFlow(
                    searchFilterLocal.observeLabels(prefUtils.searchFilter).transform { labels ->
                        val labelsToUpdate = verifyLabels(attrs, labels)
                        if (labelsToUpdate != null) {
                            searchFilterLocal.invalidateLabels(prefUtils.searchFilter, labelsToUpdate)
                        } else {
                            emit(labels)
                        }
                    }
                )
            },
            saveRemoteDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = { it.toModel(categoryID) }
        )
    }

    override fun getNutrients(
        attrs: List<NutrientRecipeAttr>
    ): Flow<State<List<NutrientOfSearchFilter>>> {
        return getData(
            remoteDataProvider = { DataSource.Empty },
            localDataProvider = {
                DataSource.LocalFlow(
                    searchFilterLocal.observeNutrients(prefUtils.searchFilter).transform { nutrients ->
                        val nutrientsToUpdate = verifyNutrients(attrs, nutrients)
                        if (nutrientsToUpdate != null) {
                            searchFilterLocal.invalidateNutrients(prefUtils.searchFilter, nutrientsToUpdate)
                        } else {
                            emit(nutrients)
                        }
                    }
                )
            },
            saveRemoteDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = List<NutrientOfSearchFilterExtendedDB>::toModel
        )
    }


    override fun getFilter(attrs: RecipeAttrs): Flow<State<SearchFilter>> {
        return getData(
            remoteDataProvider = { DataSource.Empty },
            localDataProvider = { DataSource.LocalFlow(observeFilter(attrs)) },
            saveRemoteDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = SearchFilterExtendedDB::toModel
        )
    }

    override fun getFilterPreset(attrs: RecipeAttrs): Flow<State<SearchFilterPreset>> {
        return getData(
            remoteDataProvider = { DataSource.Empty },
            localDataProvider = { DataSource.LocalFlow(observeFilter(attrs)) },
            saveRemoteDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = SearchFilterExtendedDB::toModelPreset
        )
    }

    override fun getFilterPreset(attrs: RecipeAttrs, labelID: Int): Flow<State<SearchFilterPreset>> {
        return getData(
            remoteDataProvider = { DataSource.Empty },
            localDataProvider = { DataSource.Local(SearchFilterPreset(attrs, labelID)) },
            saveRemoteDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = { it }
        )
    }


    private fun observeFilter(attrs: RecipeAttrs): Flow<SearchFilterExtendedDB> {
        return searchFilterLocal.observeFilterExtended(prefUtils.searchFilter).transform { filter ->
            val basicsToUpdate = verifyBasics(attrs.basics, filter.basics)
            val labelsToUpdate = verifyLabels(attrs.labels, filter.labels)
            val nutrientsToUpdate = verifyNutrients(attrs.nutrients, filter.nutrients)
            if (basicsToUpdate != null || labelsToUpdate != null || nutrientsToUpdate != null) {
                searchFilterLocal.invalidateFilter(
                    prefUtils.searchFilter,
                    basicsToUpdate,
                    labelsToUpdate,
                    nutrientsToUpdate,
                )
            } else {
                emit(filter)
            }
        }
    }

    private fun verifyBasics(
        attrs: List<BasicRecipeAttr>,
        basics: List<BasicOfSearchFilterExtendedDB>
    ): List<BasicOfSearchFilterDB>? {
        val basicsMap = basics.associateBy { it.infoID }
        val basicsNew = attrs.filter { it.tag != null }.map { basicAttr ->
            basicsMap[basicAttr.ID]?.toDBLatest() ?: basicAttr.toFilter(prefUtils.searchFilter)
        }
        return compare(basics.map(BasicOfSearchFilterExtendedDB::toDB), basicsNew)
    }

    private fun verifyLabels(
        attrs: List<LabelRecipeAttr>,
        labels: List<LabelOfSearchFilterExtendedDB>
    ): List<LabelOfSearchFilterDB>? {
        val labelsMap = labels.associateBy { it.infoID }
        val labelsNew = attrs.map { labelAttr ->
            labelsMap[labelAttr.ID]?.toDB() ?: labelAttr.toFilter(prefUtils.searchFilter)
        }
        return compare(labels.map(LabelOfSearchFilterExtendedDB::toDB), labelsNew)
    }

    private fun verifyNutrients(
        attrs: List<NutrientRecipeAttr>,
        nutrients: List<NutrientOfSearchFilterExtendedDB>
    ): List<NutrientOfSearchFilterDB>? {
        val nutrientsMap = nutrients.associateBy { it.infoID }
        val nutrientsNew = attrs.map { nutrientAttr ->
            nutrientsMap[nutrientAttr.ID]?.toDBLatest() ?: nutrientAttr.toFilter(prefUtils.searchFilter)
        }
        return compare(nutrients.map(NutrientOfSearchFilterExtendedDB::toDB), nutrientsNew)
    }

    private fun <T> compare(old: List<T>, new: List<T>): List<T>? {
        return if (old.size == new.size && old.toSet() == new.toSet()) {
            null
        } else {
            new
        }
    }
}