package com.example.foodinfo.data.remote.model

import com.google.gson.annotations.SerializedName


class RecipePageNetwork(
    @SerializedName(Fields.FROM)
    val from: Int,

    @SerializedName(Fields.TO)
    val to: Int,

    @SerializedName(Fields.HITS)
    val hits: List<RecipeHitNetwork>,

    @SerializedName(Fields.LINKS)
    val links: LinksNetwork
) {

    object Fields {
        const val FROM = "from"
        const val TO = "to"
        const val HITS = "hits"
        const val LINKS = "_links"
    }
}