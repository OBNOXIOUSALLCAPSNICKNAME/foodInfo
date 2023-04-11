package com.example.foodinfo.remote.model

import com.google.gson.annotations.SerializedName


data class CategoryRecipeAttrNetwork(
    @SerializedName(Fields.ID)
    val ID: Int,

    @SerializedName(Fields.TAG)
    val tag: String,

    @SerializedName(Fields.NAME)
    val name: String,

    @SerializedName(Fields.ALLOW_MULTIPLE)
    val allowMultiple: Boolean,

    @SerializedName(Fields.PREVIEW_URL)
    val previewURL: String
) {

    object Fields {
        const val ID = "ID"
        const val TAG = "tag"
        const val NAME = "name"
        const val ALLOW_MULTIPLE = "allowMultiple"
        const val PREVIEW_URL = "previewURL"
    }
}