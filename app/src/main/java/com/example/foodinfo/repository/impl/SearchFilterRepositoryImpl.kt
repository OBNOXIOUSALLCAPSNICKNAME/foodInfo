package com.example.foodinfo.repository.impl

import com.example.foodinfo.local.dao.RecipeAttrDAO
import com.example.foodinfo.local.dao.SearchFilterDAO
import com.example.foodinfo.local.dto.BasicOfSearchFilterDB
import com.example.foodinfo.local.dto.LabelOfSearchFilterDB
import com.example.foodinfo.local.dto.NutrientOfSearchFilterDB
import com.example.foodinfo.repository.SearchFilterRepository
import com.example.foodinfo.repository.mapper.toModelEdit
import com.example.foodinfo.repository.mapper.toModelFilterEdit
import com.example.foodinfo.repository.mapper.toModelFilterField
import com.example.foodinfo.repository.model.CategoryOfSearchFilterEditModel
import com.example.foodinfo.repository.model.NutrientOfSearchFilterEditModel
import com.example.foodinfo.repository.model.SearchFilterEditModel
import com.example.foodinfo.repository.model.filter_field.CategoryOfFilterPreset
import com.example.foodinfo.utils.FilterQueryBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class SearchFilterRepositoryImpl @Inject constructor(
    private val searchFilterDAO: SearchFilterDAO,
    private val recipeAttrDAO: RecipeAttrDAO
) : SearchFilterRepository {

    override fun getQueryByFilter(filterName: String, inputText: String): String {
        val builder = FilterQueryBuilder(
            searchFilterDAO.getBasics(filterName).toModelFilterField(),
            searchFilterDAO.getLabels(filterName).toModelFilterField(),
            searchFilterDAO.getNutrients(filterName).toModelFilterField(),
        )
        builder.setInputText(inputText)
        return builder.getQuery()
    }

    override fun getQueryByLabel(labelID: Int): String {
        val builder = FilterQueryBuilder(
            categoriesOfFilterPreset = listOf(CategoryOfFilterPreset(listOf(labelID)))
        )
        return builder.getQuery()
    }


    override fun getCategoryEdit(
        filterName: String,
        categoryID: Int
    ): Flow<CategoryOfSearchFilterEditModel> {
        return searchFilterDAO.observeLabels(filterName).map { it.toModelFilterEdit(categoryID) }
    }

    override fun getNutrientsEdit(filterName: String): Flow<List<NutrientOfSearchFilterEditModel>> {
        return searchFilterDAO.observeNutrients(filterName).map { data ->
            data.map { it.toModelEdit() }
        }
    }

    override fun getFilterEdit(filterName: String): Flow<SearchFilterEditModel> {
        return searchFilterDAO.observeFilterExtended(filterName).map { it.toModelEdit() }
    }


    override fun createFilter(filterName: String) {
        val basics = recipeAttrDAO.getBasicsAll().map { attr ->
            BasicOfSearchFilterDB(
                filterName = filterName,
                infoID = attr.ID,
                minValue = attr.rangeMin,
                maxValue = attr.rangeMax
            )
        }
        val labels = recipeAttrDAO.getLabelsAll().map { attr ->
            LabelOfSearchFilterDB(
                filterName = filterName,
                infoID = attr.ID,
                isSelected = false
            )
        }
        val nutrients = recipeAttrDAO.getNutrientsAll().map { attr ->
            NutrientOfSearchFilterDB(
                filterName = filterName,
                infoID = attr.ID,
                minValue = attr.rangeMin,
                maxValue = attr.rangeMax
            )
        }
        searchFilterDAO.insertFilter(filterName, basics, labels, nutrients)
    }

    override fun resetFilter(filterName: String) {
        resetBasics(filterName)
        resetNutrients(filterName)
        resetLabels(filterName)
    }


    override fun resetBasics(filterName: String) {
        val baseFields = searchFilterDAO.getBasics(filterName).map { basic ->
            BasicOfSearchFilterDB(
                filterName = filterName,
                ID = basic.ID,
                infoID = basic.infoID,
                minValue = basic.attrInfo.rangeMin,
                maxValue = basic.attrInfo.rangeMax
            )
        }
        searchFilterDAO.updateBasics(baseFields)
    }

    override fun resetNutrients(filterName: String) {
        val nutrients = searchFilterDAO.getNutrients(filterName).map { nutrient ->
            NutrientOfSearchFilterDB(
                filterName = filterName,
                ID = nutrient.ID,
                infoID = nutrient.infoID,
                minValue = nutrient.attrInfo.rangeMin,
                maxValue = nutrient.attrInfo.rangeMax
            )
        }
        searchFilterDAO.updateNutrients(nutrients)
    }

    override fun resetCategory(filterName: String, categoryID: Int) {
        val labels = searchFilterDAO.getLabels(filterName).filter { label ->
            label.attrInfo.categoryID == categoryID
        }.map { label ->
            LabelOfSearchFilterDB(
                filterName = filterName,
                ID = label.ID,
                infoID = label.infoID,
                isSelected = false
            )
        }
        searchFilterDAO.updateLabels(labels)
    }

    private fun resetLabels(filterName: String) {
        val labels = searchFilterDAO.getLabels(filterName).map { label ->
            LabelOfSearchFilterDB(
                filterName = filterName,
                ID = label.ID,
                infoID = label.infoID,
                isSelected = false
            )
        }
        searchFilterDAO.updateLabels(labels)
    }


    override fun updateBaseField(id: Int, minValue: Float, maxValue: Float) {
        searchFilterDAO.updateBasic(id, minValue, maxValue)
    }

    override fun updateNutrient(id: Int, minValue: Float, maxValue: Float) {
        searchFilterDAO.updateNutrient(id, minValue, maxValue)
    }

    override fun updateLabel(id: Int, isSelected: Boolean) {
        searchFilterDAO.updateLabel(id, isSelected)
    }
}