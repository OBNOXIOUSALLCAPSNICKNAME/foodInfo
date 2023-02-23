package com.example.foodinfo.remote.dto

import com.google.gson.annotations.SerializedName


data class LabelRecipeAttrNetwork(
    @SerializedName("ID")
    val ID: Int,

    @SerializedName("tag")
    val tag: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("categoryID")
    val categoryID: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("previewURL")
    val previewURL: String
)