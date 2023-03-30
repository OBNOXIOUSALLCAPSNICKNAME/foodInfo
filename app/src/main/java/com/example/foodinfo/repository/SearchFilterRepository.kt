package com.example.foodinfo.repository

import com.example.foodinfo.local.dao.SearchFilterDAO
import com.example.foodinfo.local.dto.*
import com.example.foodinfo.repository.mapper.*
import com.example.foodinfo.repository.model.*
import com.example.foodinfo.repository.state_handling.BaseRepository
import com.example.foodinfo.repository.state_handling.DataProvider
import com.example.foodinfo.repository.state_handling.State
import com.example.foodinfo.utils.PrefUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject


class SearchFilterRepository @Inject constructor(
    private val searchFilterDAO: SearchFilterDAO,
    private val prefUtils: PrefUtils
) : BaseRepository() {

    fun resetFilter() {
        val filter = searchFilterDAO.getFilterExtended(prefUtils.searchFilter)
        searchFilterDAO.updateFilter(
            filter.basics.map { it.toDefault() },
            filter.labels.map { it.toDefault() },
            filter.nutrients.map { it.toDefault() }
        )
    }

    fun resetNutrients() {
        searchFilterDAO.updateNutrients(
            searchFilterDAO.getNutrients(prefUtils.searchFilter).map { it.toDefault() }
        )
    }

    fun resetCategory(categoryID: Int) {
        searchFilterDAO.updateLabels(
            searchFilterDAO.getLabels(prefUtils.searchFilter)
                .filter { it.attrInfo!!.categoryID == categoryID }
                .map { it.toDefault() }
        )
    }

    fun updateBasic(id: Int, minValue: Float?, maxValue: Float?) {
        searchFilterDAO.updateBasic(id, minValue, maxValue)
    }

    fun updateNutrient(id: Int, minValue: Float?, maxValue: Float?) {
        searchFilterDAO.updateNutrient(id, minValue, maxValue)
    }

    fun updateLabel(id: Int, isSelected: Boolean) {
        searchFilterDAO.updateLabel(id, isSelected)
    }


    internal fun getCategoryEdit(
        categoryID: Int,
        attrs: List<LabelRecipeAttrDB>
    ): Flow<State<CategoryOfSearchFilterEditModel>> {
        return getData(
            remoteDataProvider = { DataProvider.Empty },
            localDataProvider = {
                DataProvider.LocalFlow(
                    searchFilterDAO.observeLabels(prefUtils.searchFilter).transform { labels ->
                        val labelsToUpdate = verifyLabels(attrs, labels)
                        if (labelsToUpdate != null) {
                            searchFilterDAO.invalidateLabels(prefUtils.searchFilter, labelsToUpdate)
                        } else {
                            emit(labels)
                        }
                    }
                )
            },
            saveRemoteDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = { it.toModelEdit(categoryID) }
        )
    }

    internal fun getNutrientsEdit(
        attrs: List<NutrientRecipeAttrDB>
    ): Flow<State<List<NutrientOfSearchFilterEditModel>>> {
        return getData(
            remoteDataProvider = { DataProvider.Empty },
            localDataProvider = {
                DataProvider.LocalFlow(
                    searchFilterDAO.observeNutrients(prefUtils.searchFilter).transform { nutrients ->
                        val nutrientsToUpdate = verifyNutrients(attrs, nutrients)
                        if (nutrientsToUpdate != null) {
                            searchFilterDAO.invalidateNutrients(prefUtils.searchFilter, nutrientsToUpdate)
                        } else {
                            emit(nutrients)
                        }
                    }
                )
            },
            saveRemoteDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = { it.toModelEdit() }
        )
    }

    internal fun getFilterEdit(attrs: RecipeAttrsDB): Flow<State<SearchFilterEditModel>> {
        return baseGetFilterPreset(attrs) { it.toModelEdit() }
    }

    internal fun getFilterPreset(attrs: RecipeAttrsDB): Flow<State<SearchFilterPresetModel>> {
        return baseGetFilterPreset(attrs) { it.toModelPreset() }
    }

    internal fun getFilterPresetByLabel(
        labelID: Int,
        attrs: RecipeAttrsDB
    ): Flow<State<SearchFilterPresetModel>> {
        return getData(
            remoteDataProvider = { DataProvider.Empty },
            localDataProvider = {
                val label = attrs.labels.first { it.ID == labelID }
                val category = CategoryOfFilterPresetModel(
                    tag = attrs.categories.first { it.ID == label.categoryID }.tag,
                    labels = listOf(LabelOfFilterPresetModel(label.tag, label.ID))
                )
                DataProvider.Local(SearchFilterPresetModel(categories = listOf(category)))
            },
            saveRemoteDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = { it }
        )
    }

    private fun <T> baseGetFilterPreset(
        attrs: RecipeAttrsDB,
        mapDelegate: (SearchFilterExtendedDB) -> T
    ): Flow<State<T>> {
        return getData(
            remoteDataProvider = { DataProvider.Empty },
            localDataProvider = {
                DataProvider.LocalFlow(
                    searchFilterDAO.observeFilterExtended(prefUtils.searchFilter).transform { filter ->
                        val basicsToUpdate = verifyBasics(attrs.basics, filter.basics)
                        val labelsToUpdate = verifyLabels(attrs.labels, filter.labels)
                        val nutrientsToUpdate = verifyNutrients(attrs.nutrients, filter.nutrients)
                        if (basicsToUpdate != null || labelsToUpdate != null || nutrientsToUpdate != null) {
                            searchFilterDAO.invalidateFilter(
                                prefUtils.searchFilter,
                                basicsToUpdate,
                                labelsToUpdate,
                                nutrientsToUpdate,
                            )
                        } else {
                            emit(filter)
                        }
                    }
                )
            },
            saveRemoteDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = { mapDelegate(it) }
        )
    }


    private fun verifyBasics(
        attrs: List<BasicRecipeAttrDB>,
        basics: List<BasicOfSearchFilterExtendedDB>
    ): List<BasicOfSearchFilterDB>? {
        val basicsMap = basics.associateBy { it.infoID }
        val basicsNew = attrs.filter { it.tag != null }.map { basicAttr ->
            basicsMap[basicAttr.ID]?.toDBLatest() ?: basicAttr.toFilter(prefUtils.searchFilter)
        }
        return compare(basics.map { it.toDB() }, basicsNew)
    }

    private fun verifyLabels(
        attrs: List<LabelRecipeAttrDB>,
        labels: List<LabelOfSearchFilterExtendedDB>
    ): List<LabelOfSearchFilterDB>? {
        val labelsMap = labels.associateBy { it.infoID }
        val labelsNew = attrs.map { labelAttr ->
            labelsMap[labelAttr.ID]?.toDB() ?: labelAttr.toFilter(prefUtils.searchFilter)
        }
        return compare(labels.map { it.toDB() }, labelsNew)
    }

    private fun verifyNutrients(
        attrs: List<NutrientRecipeAttrDB>,
        nutrients: List<NutrientOfSearchFilterExtendedDB>
    ): List<NutrientOfSearchFilterDB>? {
        val nutrientsMap = nutrients.associateBy { it.infoID }
        val nutrientsNew = attrs.map { nutrientAttr ->
            nutrientsMap[nutrientAttr.ID]?.toDBLatest() ?: nutrientAttr.toFilter(prefUtils.searchFilter)
        }
        return compare(nutrients.map { it.toDB() }, nutrientsNew)
    }

    private fun <T> compare(old: List<T>, new: List<T>): List<T>? {
        return if (old.size == new.size && old.toSet() == new.toSet()) {
            null
        } else {
            new
        }
    }
}