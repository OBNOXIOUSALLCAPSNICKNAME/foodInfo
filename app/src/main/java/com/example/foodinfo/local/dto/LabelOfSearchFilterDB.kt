package com.example.foodinfo.local.dto


open class LabelOfSearchFilterDB(
    open val ID: Int = 0,
    open val filterName: String,
    open val infoID: Int,
    open val isSelected: Boolean
) {

    object Columns {
        const val ID = "id"
        const val FILTER_NAME = "filter_name"
        const val INFO_ID = "info_id"
        const val IS_SELECTED = "is_selected"
    }

    companion object {
        const val TABLE_NAME = "label_of_search_filter"
    }


    override fun equals(other: Any?): Boolean {
        return (other is LabelOfSearchFilterDB) &&
                other.filterName == this.filterName &&
                other.infoID == this.infoID &&
                other.isSelected == this.isSelected
    }

    override fun hashCode(): Int {
        var result = filterName.hashCode()
        result = 31 * result + infoID
        result = 31 * result + isSelected.hashCode()
        return result
    }
}