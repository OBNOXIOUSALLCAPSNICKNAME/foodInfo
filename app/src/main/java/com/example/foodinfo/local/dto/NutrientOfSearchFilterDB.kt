package com.example.foodinfo.local.dto


open class NutrientOfSearchFilterDB(
    open val ID: Int = 0,
    open val filterName: String,
    open val infoID: Int,
    open val minValue: Float,
    open val maxValue: Float
) {

    object Columns {
        const val ID = "id"
        const val FILTER_NAME = "filter_name"
        const val INFO_ID = "info_id"
        const val MIN_VALUE = "min_value"
        const val MAX_VALUE = "max_value"
    }

    companion object {
        const val TABLE_NAME = "nutrient_of_search_filter"
    }

    override fun equals(other: Any?): Boolean {
        return (other is NutrientOfSearchFilterDB) &&
                other.ID == this.ID &&
                other.filterName == this.filterName &&
                other.infoID == this.infoID &&
                other.minValue == this.minValue &&
                other.maxValue == this.maxValue
    }

    override fun hashCode(): Int {
        var result = ID
        result = 31 * result + filterName.hashCode()
        result = 31 * result + infoID
        result = 31 * result + minValue.hashCode()
        result = 31 * result + maxValue.hashCode()
        return result
    }
}