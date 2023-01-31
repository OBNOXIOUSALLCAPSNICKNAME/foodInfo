package com.example.foodinfo.repository

import com.example.foodinfo.local.dto.SearchFilterDB
import com.example.foodinfo.repository.model.CategoryOfSearchFilterEditModel
import com.example.foodinfo.repository.model.NutrientOfSearchFilterEditModel
import com.example.foodinfo.repository.model.SearchFilterEditModel
import kotlinx.coroutines.flow.Flow


interface SearchFilterRepository {

    fun getQueryByFilter(
        filterName: String = SearchFilterDB.DEFAULT_NAME,
        inputText: String = ""
    ): String

    fun getQueryByLabel(labelID: Int): String


    fun getCategoryEdit(
        filterName: String = SearchFilterDB.DEFAULT_NAME,
        categoryID: Int
    ): Flow<CategoryOfSearchFilterEditModel>

    fun getNutrientsEdit(
        filterName: String = SearchFilterDB.DEFAULT_NAME
    ): Flow<List<NutrientOfSearchFilterEditModel>>

    fun getFilterEdit(
        filterName: String = SearchFilterDB.DEFAULT_NAME
    ): Flow<SearchFilterEditModel>


    fun createFilter(filterName: String = SearchFilterDB.DEFAULT_NAME)

    fun resetFilter(filterName: String = SearchFilterDB.DEFAULT_NAME)

    fun resetBasics(filterName: String = SearchFilterDB.DEFAULT_NAME)

    fun resetNutrients(filterName: String = SearchFilterDB.DEFAULT_NAME)

    fun resetCategory(filterName: String = SearchFilterDB.DEFAULT_NAME, categoryID: Int)


    fun updateBaseField(id: Int, minValue: Float, maxValue: Float)

    fun updateNutrient(id: Int, minValue: Float, maxValue: Float)

    fun updateLabel(id: Int, isSelected: Boolean)
}