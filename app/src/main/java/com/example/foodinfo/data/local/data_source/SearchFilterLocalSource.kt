package com.example.foodinfo.data.local.data_source

import com.example.foodinfo.data.local.model.*
import kotlinx.coroutines.flow.Flow


interface SearchFilterLocalSource {

    suspend fun getBasics(filterName: String): List<BasicOfSearchFilterExtendedDB>

    suspend fun getLabels(filterName: String): List<LabelOfSearchFilterExtendedDB>

    suspend fun getNutrients(filterName: String): List<NutrientOfSearchFilterExtendedDB>

    suspend fun getFilterExtended(filterName: String): SearchFilterExtendedDB


    fun observeBasics(filterName: String): Flow<List<BasicOfSearchFilterExtendedDB>>

    fun observeLabels(filterName: String): Flow<List<LabelOfSearchFilterExtendedDB>>

    fun observeNutrients(filterName: String): Flow<List<NutrientOfSearchFilterExtendedDB>>

    fun observeFilterExtended(filterName: String): Flow<SearchFilterExtendedDB>


    suspend fun updateBasic(id: Int, minValue: Float?, maxValue: Float?)

    suspend fun updateLabel(id: Int, isSelected: Boolean)

    suspend fun updateNutrient(id: Int, minValue: Float?, maxValue: Float?)


    // invalidate functions must remove content related with provided filterName and insert new one
    suspend fun invalidateFilter(
        filterName: String,
        basics: List<BasicOfSearchFilterDB>? = null,
        labels: List<LabelOfSearchFilterDB>? = null,
        nutrients: List<NutrientOfSearchFilterDB>? = null
    )

    suspend fun invalidateBasics(filterName: String, basics: List<BasicOfSearchFilterDB>)

    suspend fun invalidateLabels(filterName: String, labels: List<LabelOfSearchFilterDB>)

    suspend fun invalidateNutrients(filterName: String, nutrients: List<NutrientOfSearchFilterDB>)


    suspend fun initializeEmptyFilter(filterName: String)


    suspend fun resetCategory(filterName: String, categoryID: Int)

    suspend fun selectCategory(filterName: String, categoryID: Int)

    suspend fun resetNutrients(filterName: String)

    suspend fun resetFilter(filterName: String)
}
