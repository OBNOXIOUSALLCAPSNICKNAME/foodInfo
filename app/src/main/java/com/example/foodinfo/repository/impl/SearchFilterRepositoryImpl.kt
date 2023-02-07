package com.example.foodinfo.repository.impl

import android.content.Context
import com.example.foodinfo.local.dao.SearchFilterDAO
import com.example.foodinfo.local.dto.*
import com.example.foodinfo.repository.SearchFilterRepository
import com.example.foodinfo.repository.mapper.*
import com.example.foodinfo.repository.model.*
import com.example.foodinfo.repository.model.filter_field.CategoryOfFilterPreset
import com.example.foodinfo.utils.FilterQueryBuilder
import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchFilterRepositoryImpl @Inject constructor(
    private val context: Context,
    private val searchFilterDAO: SearchFilterDAO
) : SearchFilterRepository() {

    override fun getQueryByFilter(filterName: String, inputText: String): Flow<State<String>> {
        return getData(
            context = context,
            remoteDataProvider = { },
            localDataProvider = { searchFilterDAO.getFilterExtended(filterName) },
            updateLocalDelegate = { },
            mapRemoteToLocalDelegate = { },
            mapLocalToModelDelegate = {
                val builder = FilterQueryBuilder(it.toModelPreset())
                builder.setInputText(inputText)
                builder.getQuery()
            }
        )
    }

    override fun getQueryByLabel(labelID: Int): Flow<State<String>> {
        return getData(
            context = context,
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
            mapRemoteToLocalDelegate = { },
            mapLocalToModelDelegate = { it.getQuery() }
        )
    }


    override fun getCategoryEdit(
        filterName: String,
        categoryID: Int,
        attrs: List<LabelRecipeAttrDB>
    ): Flow<State<CategoryOfSearchFilterEditModel>> {
        return getVerified(
            verificationDelegate = { labels ->
                val labelsToUpdate = verifyLabels(filterName, labels, attrs)
                if (labelsToUpdate != null) {
                    searchFilterDAO.invalidateLabels(filterName, labelsToUpdate)
                    null
                } else {
                    labels.toModelFilterEdit(categoryID)
                }
            },
            dataFlow = getData(
                context = context,
                remoteDataProvider = { },
                localDataFlowProvider = { searchFilterDAO.observeLabels(filterName) },
                updateLocalDelegate = { },
                mapRemoteToLocalDelegate = { },
                mapLocalToModelDelegate = { it }
            )
        )
    }

    override fun getNutrientsEdit(
        filterName: String,
        attrs: List<NutrientRecipeAttrDB>
    ): Flow<State<List<NutrientOfSearchFilterEditModel>>> {
        return getVerified(
            verificationDelegate = { nutrients ->
                val nutrientsToUpdate = verifyNutrients(filterName, nutrients, attrs)
                if (nutrientsToUpdate != null) {
                    searchFilterDAO.invalidateNutrients(filterName, nutrientsToUpdate)
                    null
                } else {
                    nutrients.map { it.toModelEdit() }
                }
            },
            dataFlow = getData(
                context = context,
                remoteDataProvider = { },
                localDataFlowProvider = { searchFilterDAO.observeNutrients(filterName) },
                updateLocalDelegate = { },
                mapRemoteToLocalDelegate = { },
                mapLocalToModelDelegate = { it }
            )
        )
    }

    override fun getFilterEdit(
        filterName: String,
        attrs: RecipeAttrsDB
    ): Flow<State<SearchFilterEditModel>> {
        return getVerified(
            verificationDelegate = { filter ->
                val basicsToUpdate = verifyBasics(filterName, filter.basics, attrs.basics)
                val labelsToUpdate = verifyLabels(filterName, filter.labels, attrs.labels)
                val nutrientsToUpdate = verifyNutrients(filterName, filter.nutrients, attrs.nutrients)
                if (listOf(basicsToUpdate, labelsToUpdate, nutrientsToUpdate).any { it != null }) {
                    searchFilterDAO.invalidateFilter(
                        filterName, basicsToUpdate, labelsToUpdate, nutrientsToUpdate
                    )
                    null
                } else {
                    filter.toModelEdit()
                }
            },
            dataFlow = getData(
                context = context,
                remoteDataProvider = { },
                localDataFlowProvider = { searchFilterDAO.observeFilterExtended(filterName) },
                updateLocalDelegate = { },
                mapRemoteToLocalDelegate = { },
                mapLocalToModelDelegate = { it }
            )
        )
    }


    override fun createFilter(filterName: String, attrs: RecipeAttrsDB) {
        searchFilterDAO.insertFilter(
            filterName,
            attrs.basics.map { it.toFilterDefault(filterName) },
            attrs.labels.map { it.toFilterDefault(filterName) },
            attrs.nutrients.map { it.toFilterDefault(filterName) }
        )
    }

    override fun resetFilter(filterName: String, attrs: RecipeAttrsDB) {
        searchFilterDAO.invalidateFilter(
            filterName,
            attrs.basics.map { it.toFilterDefault(filterName) },
            attrs.labels.map { it.toFilterDefault(filterName) },
            attrs.nutrients.map { it.toFilterDefault(filterName) }
        )
    }

    override fun resetNutrients(filterName: String, attrs: List<NutrientRecipeAttrDB>) {
        searchFilterDAO.invalidateNutrients(filterName, attrs.map { it.toFilterDefault(filterName) })
    }

    override fun resetBasics(filterName: String, attrs: List<BasicRecipeAttrDB>) {
        searchFilterDAO.invalidateBasics(filterName, attrs.map { it.toFilterDefault(filterName) })
    }

    override fun resetCategory(filterName: String, categoryID: Int, attrs: List<LabelRecipeAttrDB>) {
        searchFilterDAO.invalidateLabels(
            filterName, attrs.filter { it.categoryID == categoryID }.map { it.toFilterDefault(filterName) }
        )
    }


    override fun updateBasics(filterName: String, basics: List<BasicOfSearchFilterEditModel>) {
        searchFilterDAO.updateBasics(basics.map { it.toDB(filterName) })
    }

    override fun updateLabels(filterName: String, labels: List<LabelOfSearchFilterEditModel>) {
        searchFilterDAO.updateLabels(labels.map { it.toDB(filterName) })
    }

    override fun updateNutrients(filterName: String, nutrients: List<NutrientOfSearchFilterEditModel>) {
        searchFilterDAO.updateNutrients(nutrients.map { it.toDB(filterName) })
    }


    private fun verifyLabels(
        filterName: String,
        labels: List<LabelOfSearchFilterExtendedDB>,
        attrs: List<LabelRecipeAttrDB>
    ): List<LabelOfSearchFilterDB>? {
        val labelsMap = labels.associateBy { it.infoID }
        val labelsNew = attrs.map { labelAttr ->
            val label = labelsMap[labelAttr.ID]
            if (label != null) {
                labelAttr.toFilter(label)
            } else {
                labelAttr.toFilterDefault(filterName)
            }
        }
        return if (labelsNew.sortedBy { it.ID } != labels.map { it.toDB() }.sortedBy { it.ID }) {
            labelsNew
        } else {
            null
        }
    }

    private fun verifyBasics(
        filterName: String,
        basics: List<BasicOfSearchFilterExtendedDB>,
        attrs: List<BasicRecipeAttrDB>
    ): List<BasicOfSearchFilterDB>? {
        val basicsMap = basics.associateBy { it.infoID }
        val basicsNew = attrs.map { basicAttr ->
            val basic = basicsMap[basicAttr.ID]
            if (basic != null) {
                basicAttr.toFilter(basic)
            } else {
                basicAttr.toFilterDefault(filterName)
            }
        }
        return if (basicsNew.sortedBy { it.ID } != basics.map { it.toDB() }.sortedBy { it.ID }) {
            basicsNew
        } else {
            null
        }
    }

    private fun verifyNutrients(
        filterName: String,
        nutrients: List<NutrientOfSearchFilterExtendedDB>,
        attrs: List<NutrientRecipeAttrDB>
    ): List<NutrientOfSearchFilterDB>? {
        val nutrientsMap = nutrients.associateBy { it.infoID }
        val nutrientsNew = attrs.map { nutrientAttr ->
            val nutrient = nutrientsMap[nutrientAttr.ID]
            if (nutrient != null) {
                nutrientAttr.toFilter(nutrient)
            } else {
                nutrientAttr.toFilterDefault(filterName)
            }
        }
        return if (nutrientsNew.sortedBy { it.ID } != nutrients.map { it.toDB() }.sortedBy { it.ID }) {
            nutrientsNew
        } else {
            null
        }
    }
}