package com.example.foodinfo.local.dao

import com.example.foodinfo.local.dto.*
import kotlinx.coroutines.flow.Flow


interface SearchFilterDAO {

    fun getBasics(filterName: String): List<@JvmWildcard BasicOfSearchFilterExtendedDB>

    fun getLabels(filterName: String): List<@JvmWildcard LabelOfSearchFilterExtendedDB>

    fun getNutrients(filterName: String): List<@JvmWildcard NutrientOfSearchFilterExtendedDB>

    fun getFilterExtended(filterName: String): SearchFilterExtendedDB


    fun observeBasics(filterName: String): Flow<@JvmWildcard List<@JvmWildcard BasicOfSearchFilterExtendedDB>>

    fun observeLabels(filterName: String): Flow<@JvmWildcard List<@JvmWildcard LabelOfSearchFilterExtendedDB>>

    fun observeNutrients(filterName: String): Flow<@JvmWildcard List<@JvmWildcard NutrientOfSearchFilterExtendedDB>>

    fun observeFilterExtended(filterName: String): Flow<@JvmWildcard SearchFilterExtendedDB>


    fun updateBasic(id: Int, minValue: Float?, maxValue: Float?)

    fun updateLabel(id: Int, isSelected: Boolean)

    fun updateNutrient(id: Int, minValue: Float?, maxValue: Float?)


    fun updateFilter(
        basics: List<BasicOfSearchFilterDB>,
        labels: List<LabelOfSearchFilterDB>,
        nutrients: List<NutrientOfSearchFilterDB>
    )

    fun updateBasics(basics: List<BasicOfSearchFilterDB>)

    fun updateLabels(labels: List<LabelOfSearchFilterDB>)

    fun updateNutrients(nutrients: List<NutrientOfSearchFilterDB>)


    fun invalidateFilter(
        filterName: String,
        basics: List<BasicOfSearchFilterDB>? = null,
        labels: List<LabelOfSearchFilterDB>? = null,
        nutrients: List<NutrientOfSearchFilterDB>? = null
    )

    fun invalidateBasics(filterName: String, basics: List<BasicOfSearchFilterDB>)

    fun invalidateLabels(filterName: String, labels: List<LabelOfSearchFilterDB>)

    fun invalidateNutrients(filterName: String, nutrients: List<NutrientOfSearchFilterDB>)


    // DO NOT INSERT if filter already exists
    fun insertFilter(
        filterName: String,
        basics: List<BasicOfSearchFilterDB>,
        labels: List<LabelOfSearchFilterDB>,
        nutrients: List<NutrientOfSearchFilterDB>
    )
}
