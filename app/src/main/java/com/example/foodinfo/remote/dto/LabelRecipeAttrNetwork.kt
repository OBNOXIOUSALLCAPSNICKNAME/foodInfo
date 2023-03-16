package com.example.foodinfo.remote.dto

import com.google.gson.annotations.SerializedName


data class LabelRecipeAttrNetwork(
    @SerializedName(Fields.ID)
    val ID: Int,

    @SerializedName(Fields.TAG)
    val tag: String,

    @SerializedName(Fields.NAME)
    val name: String,

    @SerializedName(Fields.CATEGORY_ID)
    val categoryID: Int,

    @SerializedName(Fields.DESCRIPTION)
    val description: String,

    @SerializedName(Fields.PREVIEW_URL)
    val previewURL: String
) {

    object Fields {
        const val ID = "ID"
        const val TAG = "tag"
        const val NAME = "name"
        const val CATEGORY_ID = "categoryID"
        const val DESCRIPTION = "description"
        const val PREVIEW_URL = "previewURL"
    }
}