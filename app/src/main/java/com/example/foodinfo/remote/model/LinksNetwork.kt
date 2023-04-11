package com.example.foodinfo.remote.model

import com.google.gson.annotations.SerializedName


data class LinksNetwork(
    @SerializedName(Fields.NEXT)
    val next: LinkBodyNetwork? = null,

    @SerializedName(Fields.SELF)
    val self: LinkBodyNetwork? = null
) {

    object Fields {
        const val NEXT = "next"
        const val SELF = "self"
    }
}