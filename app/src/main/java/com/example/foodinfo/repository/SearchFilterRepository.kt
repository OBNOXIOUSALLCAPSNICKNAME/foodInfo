package com.example.foodinfo.repository

import com.example.foodinfo.local.dao.SearchFilterDAO
import com.example.foodinfo.local.dto.*
import com.example.foodinfo.repository.mapper.*
import com.example.foodinfo.repository.model.CategoryOfSearchFilterEditModel
import com.example.foodinfo.repository.model.NutrientOfSearchFilterEditModel
import com.example.foodinfo.repository.model.SearchFilterEditModel
import com.example.foodinfo.repository.model.SearchFilterPresetModel
import com.example.foodinfo.repository.model.filter_field.CategoryOfFilterPreset
import com.example.foodinfo.utils.FilterQueryBuilder
import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject


class SearchFilterRepository @Inject constructor(
    private val searchFilterDAO: SearchFilterDAO
) : BaseRepository() {

    fun getQueryByFilter(filterName: String, inputText: String, attrs: RecipeAttrsDB): Flow<State<String>> {
        //TODO attrs will be used for remote query
        return getData(
            remoteDataProvider = { },
            localDataProvider = { searchFilterDAO.getFilterExtended(filterName) },
            updateLocalDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = {
                val builder = FilterQueryBuilder(it.toModelPreset())
                builder.setInputText(inputText)
                builder.getQuery()
            }
        )
    }

    fun getQueryByLabel(labelID: Int, attrs: RecipeAttrsDB): Flow<State<String>> {
        //TODO attrs will be used for remote query
        return getData(
            remoteDataProvider = { },
            localDataProvider = {
                FilterQueryBuilder(
                    SearchFilterPresetModel(
                        basics = listOf(),
                        nutrients = listOf(),
                        categories = listOf(CategoryOfFilterPreset(listOf(labelID)))
                    )
                )
            },
            updateLocalDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = { it.getQuery() }
        )
    }

    fun resetFilter(filterName: String) {
        val filter = searchFilterDAO.getFilterExtended(filterName)
        searchFilterDAO.updateFilter(
            filter.basics.map { it.toDefault() },
            filter.labels.map { it.toDefault() },
            filter.nutrients.map { it.toDefault() }
        )
    }

    fun resetNutrients(filterName: String) {
        searchFilterDAO.updateNutrients(
            searchFilterDAO.getNutrients(filterName).map { it.toDefault() }
        )
    }

    fun resetCategory(filterName: String, categoryID: Int) {
        searchFilterDAO.updateLabels(
            searchFilterDAO.getLabels(filterName)
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


    internal fun createFilter(filterName: String, attrs: RecipeAttrsDB) {
        searchFilterDAO.insertFilter(
            filterName,
            attrs.basics.filter { it.tag != null }.map { it.toFilter(filterName) },
            attrs.labels.map { it.toFilter(filterName) },
            attrs.nutrients.map { it.toFilter(filterName) }
        )
    }

    internal fun getCategoryEdit(
        filterName: String,
        categoryID: Int,
        attrs: List<LabelRecipeAttrDB>
    ): Flow<State<CategoryOfSearchFilterEditModel>> {
        return getData(
            remoteDataProvider = { },
            localDataFlowProvider = {
                searchFilterDAO.observeLabels(filterName).transform { labels ->
                    val labelsToUpdate = verifyLabels(filterName, attrs, labels)
                    if (labelsToUpdate != null) {
                        searchFilterDAO.invalidateLabels(filterName, labelsToUpdate)
                    } else {
                        emit(labels)
                    }
                }
            },
            updateLocalDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = { it.toModelEdit(categoryID) }
        )
    }

    internal fun getNutrientsEdit(
        filterName: String,
        attrs: List<NutrientRecipeAttrDB>
    ): Flow<State<List<NutrientOfSearchFilterEditModel>>> {
        return getData(
            remoteDataProvider = { },
            localDataFlowProvider = {
                searchFilterDAO.observeNutrients(filterName).transform { nutrients ->
                    val nutrientsToUpdate = verifyNutrients(filterName, attrs, nutrients)
                    if (nutrientsToUpdate != null) {
                        searchFilterDAO.invalidateNutrients(filterName, nutrientsToUpdate)
                    } else {
                        emit(nutrients)
                    }
                }
            },
            updateLocalDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = { it.toModelEdit() }
        )
    }

    internal fun getFilterEdit(
        filterName: String,
        attrs: RecipeAttrsDB
    ): Flow<State<SearchFilterEditModel>> {
        return getData(
            remoteDataProvider = { },
            localDataFlowProvider = {
                searchFilterDAO.observeFilterExtended(filterName).transform { filter ->
                    val basicsToUpdate = verifyBasics(filterName, attrs.basics, filter.basics)
                    val labelsToUpdate = verifyLabels(filterName, attrs.labels, filter.labels)
                    val nutrientsToUpdate = verifyNutrients(filterName, attrs.nutrients, filter.nutrients)
                    if (listOf(basicsToUpdate, labelsToUpdate, nutrientsToUpdate).any { it != null }) {
                        searchFilterDAO.invalidateFilter(
                            filterName,
                            basicsToUpdate,
                            labelsToUpdate,
                            nutrientsToUpdate,
                        )
                    } else {
                        emit(filter)
                    }
                }
            },
            updateLocalDelegate = { },
            mapToLocalDelegate = { },
            mapToModelDelegate = { it.toModelEdit() }
        )
    }


    private fun verifyBasics(
        filterName: String,
        attrs: List<BasicRecipeAttrDB>,
        basics: List<BasicOfSearchFilterExtendedDB>
    ): List<BasicOfSearchFilterDB>? {
        val basicsMap = basics.associateBy { it.infoID }
        val basicsNew = attrs.filter { it.tag != null }.map { basicAttr ->
            basicsMap[basicAttr.ID]?.toDBLatest() ?: basicAttr.toFilter(filterName)
        }
        return if (basicsNew.sortedBy { it.ID } != basics.map { it.toDB() }.sortedBy { it.ID }) {
            basicsNew
        } else {
            null
        }
    }

    private fun verifyLabels(
        filterName: String,
        attrs: List<LabelRecipeAttrDB>,
        labels: List<LabelOfSearchFilterExtendedDB>
    ): List<LabelOfSearchFilterDB>? {
        val labelsMap = labels.associateBy { it.infoID }
        val labelsNew = attrs.map { labelAttr ->
            labelsMap[labelAttr.ID]?.toDB() ?: labelAttr.toFilter(filterName)
        }
        return if (labelsNew.sortedBy { it.ID } != labels.map { it.toDB() }.sortedBy { it.ID }) {
            labelsNew
        } else {
            null
        }
    }

    private fun verifyNutrients(
        filterName: String,
        attrs: List<NutrientRecipeAttrDB>,
        nutrients: List<NutrientOfSearchFilterExtendedDB>
    ): List<NutrientOfSearchFilterDB>? {
        val nutrientsMap = nutrients.associateBy { it.infoID }
        val nutrientsNew = attrs.map { nutrientAttr ->
            nutrientsMap[nutrientAttr.ID]?.toDBLatest() ?: nutrientAttr.toFilter(filterName)
        }
        return if (nutrientsNew.sortedBy { it.ID } != nutrients.map { it.toDB() }.sortedBy { it.ID }) {
            nutrientsNew
        } else {
            null
        }
    }
}