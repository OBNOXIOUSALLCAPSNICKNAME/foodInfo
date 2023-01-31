package com.example.foodinfo.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.foodinfo.local.dto.BasicRecipeAttrDB


@Entity(
    tableName = BasicRecipeAttrDB.TABLE_NAME,
    indices = [Index(value = arrayOf(BasicRecipeAttrDB.Columns.ID), unique = true)]
)
data class BasicRecipeAttrEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.ID)
    override val ID: Int = 0,

    @ColumnInfo(name = Columns.TAG)
    override val tag: String?,

    @ColumnInfo(name = Columns.NAME)
    override val name: String,

    @ColumnInfo(name = Columns.COLUMN_NAME)
    override val columnName: String,

    @ColumnInfo(name = Columns.MEASURE)
    override val measure: String,

    @ColumnInfo(name = Columns.RANGE_MIN)
    override val rangeMin: Float,

    @ColumnInfo(name = Columns.RANGE_MAX)
    override val rangeMax: Float,

    @ColumnInfo(name = Columns.STEP_SIZE)
    override val stepSize: Float

) : BasicRecipeAttrDB(
    ID = ID,
    tag = tag,
    name = name,
    columnName = columnName,
    measure = measure,
    rangeMin = rangeMin,
    rangeMax = rangeMax,
    stepSize = stepSize
) {

    companion object {
        fun fromDB(item: BasicRecipeAttrDB): BasicRecipeAttrEntity {
            return BasicRecipeAttrEntity(
                ID = item.ID,
                tag = item.tag,
                name = item.name,
                columnName = item.columnName,
                measure = item.measure,
                rangeMin = item.rangeMin,
                rangeMax = item.rangeMax,
                stepSize = item.stepSize
            )
        }
    }
}