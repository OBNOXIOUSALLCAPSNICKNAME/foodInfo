package com.example.foodinfo.remote.dto

import com.google.gson.annotations.SerializedName


data class CategoryRecipeAttrNetwork(
    @SerializedName("ID")
    val ID: Int,

    @SerializedName("tag")
    val tag: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("allowMultiple")
    val allowMultiple: Boolean,

    @SerializedName("previewURL")
    val previewURL: String
)