package com.example.foodinfo.data.remote.model

import com.google.gson.annotations.SerializedName


data class NutrientOfRecipeMetadataNetwork(
    @SerializedName(Fields.ID)
    val ID: Int,

    @SerializedName(Fields.TAG)
    val tag: String,

    @SerializedName(Fields.NAME)
    val name: String,

    @SerializedName(Fields.DESCRIPTION)
    val description: String,

    @SerializedName(Fields.PREVIEW_URL)
    val previewURL: String,

    @SerializedName(Fields.MEASURE)
    val measure: String,

    @SerializedName(Fields.HAS_RDI)
    val hasRDI: Boolean,

    @SerializedName(Fields.DAILY_ALLOWANCE)
    val dailyAllowance: Float,

    @SerializedName(Fields.RANGE_MIN)
    val rangeMin: Float,

    @SerializedName(Fields.RANGE_MAX)
    val rangeMax: Float,

    @SerializedName(Fields.STEP_SIZE)
    val stepSize: Float
) {

    object Fields {
        const val ID = "ID"
        const val TAG = "tag"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val PREVIEW_URL = "previewURL"
        const val MEASURE = "measure"
        const val HAS_RDI = "hasRDI"
        const val DAILY_ALLOWANCE = "dailyAllowance"
        const val RANGE_MIN = "rangeMin"
        const val RANGE_MAX = "rangeMax"
        const val STEP_SIZE = "stepSize"
    }
}