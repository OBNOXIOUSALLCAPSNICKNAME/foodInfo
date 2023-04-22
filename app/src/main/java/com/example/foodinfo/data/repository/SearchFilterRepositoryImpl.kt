package com.example.foodinfo.data.repository

import com.example.foodinfo.core.utils.PrefUtils
import com.example.foodinfo.data.local.data_source.SearchFilterLocalSource
import com.example.foodinfo.data.local.model.*
import com.example.foodinfo.data.mapper.*
import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.model.*
import com.example.foodinfo.domain.repository.SearchFilterRepository
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
        metadata: List<LabelOfRecipeMetadata>
    ): Flow<State<CategoryOfSearchFilter>> {
        return getData(
            remoteDataProvider = { DataSource.Empty },
            localDataProvider = {
                DataSource.LocalFlow(
                    searchFilterLocal.observeLabels(prefUtils.searchFilter).transform { labels ->
                        val labelsToUpdate = verifyLabels(metadata, labels)
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
        metadata: List<NutrientOfRecipeMetadata>
    ): Flow<State<List<NutrientOfSearchFilter>>> {
        return getData(
            remoteDataProvider = { DataSource.Empty },
            localDataProvider = {
                DataSource.LocalFlow(
                    searchFilterLocal.observeNutrients(prefUtils.searchFilter).transform { nutrients ->
                        val nutrientsToUpdate = verifyNutrients(metadata, nutrients)
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


    override fun getFilter(metadata: RecipeMetadata): Flow<State<SearchFilter>> {
        return getData(
            remoteDataProvider = { DataSource.Empty },
            localDataProvider = { DataSource.LocalFlow(observeFilter(metadata)) },
            saveRemoteDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = SearchFilterExtendedDB::toModel
        )
    }

    override fun getFilterPreset(metadata: RecipeMetadata): Flow<State<SearchFilterPreset>> {
        return getData(
            remoteDataProvider = { DataSource.Empty },
            localDataProvider = { DataSource.LocalFlow(observeFilter(metadata)) },
            saveRemoteDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = SearchFilterExtendedDB::toModelPreset
        )
    }

    override fun getFilterPreset(metadata: RecipeMetadata, labelID: Int): Flow<State<SearchFilterPreset>> {
        return getData(
            remoteDataProvider = { DataSource.Empty },
            localDataProvider = { DataSource.Local(SearchFilterPreset(metadata, labelID)) },
            saveRemoteDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = { it }
        )
    }


    private fun observeFilter(metadata: RecipeMetadata): Flow<SearchFilterExtendedDB> {
        return searchFilterLocal.observeFilterExtended(prefUtils.searchFilter).transform { filter ->
            val basicsToUpdate = verifyBasics(metadata.basics, filter.basics)
            val labelsToUpdate = verifyLabels(metadata.labels, filter.labels)
            val nutrientsToUpdate = verifyNutrients(metadata.nutrients, filter.nutrients)
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
        metadata: List<BasicOfRecipeMetadata>,
        basics: List<BasicOfSearchFilterExtendedDB>
    ): List<BasicOfSearchFilterDB>? {
        val basicsMap = basics.associateBy { it.infoID }
        val basicsNew = metadata.filter { it.tag != null }.map { basic ->
            basicsMap[basic.ID]?.toDBLatest() ?: basic.toFilter(prefUtils.searchFilter)
        }
        return compare(basics.map(BasicOfSearchFilterExtendedDB::toDB), basicsNew)
    }

    private fun verifyLabels(
        metadata: List<LabelOfRecipeMetadata>,
        labels: List<LabelOfSearchFilterExtendedDB>
    ): List<LabelOfSearchFilterDB>? {
        val labelsMap = labels.associateBy { it.infoID }
        val labelsNew = metadata.map { label ->
            labelsMap[label.ID]?.toDB() ?: label.toFilter(prefUtils.searchFilter)
        }
        return compare(labels.map(LabelOfSearchFilterExtendedDB::toDB), labelsNew)
    }

    private fun verifyNutrients(
        metadata: List<NutrientOfRecipeMetadata>,
        nutrients: List<NutrientOfSearchFilterExtendedDB>
    ): List<NutrientOfSearchFilterDB>? {
        val nutrientsMap = nutrients.associateBy { it.infoID }
        val nutrientsNew = metadata.map { nutrient ->
            nutrientsMap[nutrient.ID]?.toDBLatest() ?: nutrient.toFilter(prefUtils.searchFilter)
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