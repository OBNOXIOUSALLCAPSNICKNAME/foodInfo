package com.example.foodinfo.local.room.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.foodinfo.local.dto.*
import com.example.foodinfo.local.room.entity.BasicOfSearchFilterEntity
import com.example.foodinfo.local.room.entity.LabelOfSearchFilterEntity
import com.example.foodinfo.local.room.entity.NutrientOfSearchFilterEntity


data class SearchFilterExtendedPOJO(
    @ColumnInfo(name = SearchFilterDB.Columns.NAME)
    override val name: String = SearchFilterDB.DEFAULT_NAME,

    @Relation(
        parentColumn = SearchFilterDB.Columns.NAME,
        entityColumn = BasicOfSearchFilterDB.Columns.FILTER_NAME,
        entity = BasicOfSearchFilterEntity::class
    )
    override val basics: List<BasicOfSearchFilterExtendedPOJO>,

    @Relation(
        parentColumn = SearchFilterDB.Columns.NAME,
        entityColumn = LabelOfSearchFilterDB.Columns.FILTER_NAME,
        entity = LabelOfSearchFilterEntity::class
    )
    override val labels: List<LabelOfSearchFilterExtendedPOJO>,

    @Relation(
        parentColumn = SearchFilterDB.Columns.NAME,
        entityColumn = NutrientOfSearchFilterDB.Columns.FILTER_NAME,
        entity = NutrientOfSearchFilterEntity::class
    )
    override val nutrients: List<NutrientOfSearchFilterExtendedPOJO>

) : SearchFilterExtendedDB(
    name = name,
    basics = basics,
    labels = labels,
    nutrients = nutrients
)