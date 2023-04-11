package com.example.foodinfo.remote.model

import com.google.gson.annotations.SerializedName


data class LinkBodyNetwork(
    @SerializedName(Fields.HREF)
    val href: String,

    @SerializedName(Fields.TITLE)
    val title: String,
) {

    object Fields {
        const val HREF = "href"
        const val TITLE = "title"
    }
}