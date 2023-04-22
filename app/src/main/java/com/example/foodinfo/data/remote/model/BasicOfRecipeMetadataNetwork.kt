package com.example.foodinfo.data.remote.model

import com.google.gson.annotations.SerializedName


data class BasicOfRecipeMetadataNetwork(
    @SerializedName(Fields.ID)
    val ID: Int,

    @SerializedName(Fields.TAG)
    val tag: String?,

    @SerializedName(Fields.NAME)
    val name: String,

    @SerializedName(Fields.COLUMN_NAME)
    val columnName: String,

    @SerializedName(Fields.MEASURE)
    val measure: String,

    @SerializedName(Fields.STEP_SIZE)
    val stepSize: Float,

    @SerializedName(Fields.RANGE_MIN)
    val rangeMin: Float,

    @SerializedName(Fields.RANGE_MAX)
    val rangeMax: Float
) {

    object Fields {
        const val ID = "ID"
        const val TAG = "tag"
        const val NAME = "name"
        const val COLUMN_NAME = "columnName"
        const val MEASURE = "measure"
        const val STEP_SIZE = "stepSize"
        const val RANGE_MIN = "rangeMin"
        const val RANGE_MAX = "rangeMax"
    }
}