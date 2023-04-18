package com.example.foodinfo.data.local.room.data_source

import com.example.foodinfo.data.local.data_source.SearchFilterLocalSource
import com.example.foodinfo.data.local.model.*
import com.example.foodinfo.data.local.room.dao.SearchFilterDAO
import com.example.foodinfo.data.local.room.model.entity.BasicOfSearchFilterEntity
import com.example.foodinfo.data.local.room.model.entity.LabelOfSearchFilterEntity
import com.example.foodinfo.data.local.room.model.entity.NutrientOfSearchFilterEntity
import com.example.foodinfo.data.local.room.model.entity.SearchFilterEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchFilterRoomSource @Inject constructor(
    private val searchFilterDAO: SearchFilterDAO
) : SearchFilterLocalSource {
    override suspend fun getBasics(filterName: String): List<BasicOfSearchFilterExtendedDB> {
        return searchFilterDAO.getBasics(filterName)
    }

    override suspend fun getLabels(filterName: String): List<LabelOfSearchFilterExtendedDB> {
        return searchFilterDAO.getLabels(filterName)
    }

    override suspend fun getNutrients(filterName: String): List<NutrientOfSearchFilterExtendedDB> {
        return searchFilterDAO.getNutrients(filterName)
    }

    override suspend fun getFilterExtended(filterName: String): SearchFilterExtendedDB {
        return searchFilterDAO.getFilterExtended(filterName)
    }

    override fun observeBasics(filterName: String): Flow<List<BasicOfSearchFilterExtendedDB>> {
        return searchFilterDAO.observeBasics(filterName)
    }

    override fun observeLabels(filterName: String): Flow<List<LabelOfSearchFilterExtendedDB>> {
        return searchFilterDAO.observeLabels(filterName)
    }

    override fun observeNutrients(filterName: String): Flow<List<NutrientOfSearchFilterExtendedDB>> {
        return searchFilterDAO.observeNutrients(filterName)
    }

    override fun observeFilterExtended(filterName: String): Flow<SearchFilterExtendedDB> {
        return searchFilterDAO.observeFilterExtended(filterName)
    }

    override suspend fun updateBasic(id: Int, minValue: Float?, maxValue: Float?) {
        searchFilterDAO.updateBasic(id, minValue, maxValue)
    }

    override suspend fun updateLabel(id: Int, isSelected: Boolean) {
        searchFilterDAO.updateLabel(id, isSelected)
    }

    override suspend fun updateNutrient(id: Int, minValue: Float?, maxValue: Float?) {
        searchFilterDAO.updateNutrient(id, minValue, maxValue)
    }

    override suspend fun invalidateFilter(
        filterName: String,
        basics: List<BasicOfSearchFilterDB>?,
        labels: List<LabelOfSearchFilterDB>?,
        nutrients: List<NutrientOfSearchFilterDB>?
    ) {
        searchFilterDAO.invalidateFilter(
            filterName = filterName,
            basics = basics?.map(BasicOfSearchFilterEntity::invoke),
            labels = labels?.map(LabelOfSearchFilterEntity::invoke),
            nutrients = nutrients?.map(NutrientOfSearchFilterEntity::invoke)
        )
    }

    override suspend fun invalidateBasics(filterName: String, basics: List<BasicOfSearchFilterDB>) {
        searchFilterDAO.invalidateBasics(filterName, basics.map(BasicOfSearchFilterEntity::invoke))
    }

    override suspend fun invalidateLabels(filterName: String, labels: List<LabelOfSearchFilterDB>) {
        searchFilterDAO.invalidateLabels(filterName, labels.map(LabelOfSearchFilterEntity::invoke))
    }

    override suspend fun invalidateNutrients(filterName: String, nutrients: List<NutrientOfSearchFilterDB>) {
        searchFilterDAO.invalidateNutrients(filterName, nutrients.map(NutrientOfSearchFilterEntity::invoke))
    }

    override suspend fun initializeEmptyFilter(filterName: String) {
        searchFilterDAO.insertFilter(SearchFilterEntity(filterName))
    }


    override suspend fun resetCategory(filterName: String, categoryID: Int) {
        searchFilterDAO.resetCategory(filterName, categoryID)
    }

    override suspend fun resetNutrients(filterName: String) {
        searchFilterDAO.resetNutrients(filterName)
    }

    override suspend fun resetFilter(filterName: String) {
        searchFilterDAO.resetFilter(filterName)
    }
}