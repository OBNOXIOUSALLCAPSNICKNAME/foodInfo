package com.example.foodinfo.local.room.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.foodinfo.local.model.NutrientOfSearchFilterDB
import com.example.foodinfo.local.model.SearchFilterDB


@Entity(
    tableName = NutrientOfSearchFilterDB.TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = SearchFilterEntity::class,
        parentColumns = arrayOf(SearchFilterDB.Columns.NAME),
        childColumns = arrayOf(NutrientOfSearchFilterDB.Columns.FILTER_NAME),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class NutrientOfSearchFilterEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Columns.ID)
    override val ID: Int = 0,

    @ColumnInfo(name = Columns.FILTER_NAME, index = true)
    override val filterName: String,

    @ColumnInfo(name = Columns.INFO_ID)
    override val infoID: Int,

    @ColumnInfo(name = Columns.MIN_VALUE)
    override val minValue: Float?,

    @ColumnInfo(name = Columns.MAX_VALUE)
    override val maxValue: Float?

) : NutrientOfSearchFilterDB(
    ID = ID,
    filterName = filterName,
    infoID = infoID,
    minValue = minValue,
    maxValue = maxValue
) {
    companion object {
        operator fun invoke(item: NutrientOfSearchFilterDB): NutrientOfSearchFilterEntity {
            return NutrientOfSearchFilterEntity(
                ID = item.ID,
                filterName = item.filterName,
                infoID = item.infoID,
                minValue = item.minValue,
                maxValue = item.maxValue
            )
        }
    }
}