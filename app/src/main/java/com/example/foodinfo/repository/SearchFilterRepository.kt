package com.example.foodinfo.repository

import com.example.foodinfo.local.dto.SearchFilterDB
import com.example.foodinfo.repository.model.CategoryOfSearchFilterEditModel
import com.example.foodinfo.repository.model.NutrientOfSearchFilterEditModel
import com.example.foodinfo.repository.model.SearchFilterEditModel
import kotlinx.coroutines.flow.Flow


abstract class SearchFilterRepository : BaseRepository() {

    abstract fun getQueryByFilter(
        filterName: String = SearchFilterDB.DEFAULT_NAME,
        inputText: String = ""
    ): String

    abstract fun getQueryByLabel(labelID: Int): String


    abstract fun getCategoryEdit(
        filterName: String = SearchFilterDB.DEFAULT_NAME,
        categoryID: Int
    ): Flow<CategoryOfSearchFilterEditModel>

    abstract fun getNutrientsEdit(
        filterName: String = SearchFilterDB.DEFAULT_NAME
    ): Flow<List<NutrientOfSearchFilterEditModel>>

    abstract fun getFilterEdit(
        filterName: String = SearchFilterDB.DEFAULT_NAME
    ): Flow<SearchFilterEditModel>


    abstract fun createFilter(filterName: String = SearchFilterDB.DEFAULT_NAME)

    abstract fun resetFilter(filterName: String = SearchFilterDB.DEFAULT_NAME)

    abstract fun resetBasics(filterName: String = SearchFilterDB.DEFAULT_NAME)

    abstract fun resetNutrients(filterName: String = SearchFilterDB.DEFAULT_NAME)

    abstract fun resetCategory(filterName: String = SearchFilterDB.DEFAULT_NAME, categoryID: Int)


    abstract fun updateBaseField(id: Int, minValue: Float, maxValue: Float)

    abstract fun updateNutrient(id: Int, minValue: Float, maxValue: Float)

    abstract fun updateLabel(id: Int, isSelected: Boolean)
}