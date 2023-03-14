package com.example.foodinfo.remote.dto

import com.google.gson.annotations.SerializedName


class RecipePageNetwork(
    val from: Int,
    val to: Int,
    val count: Int,

    @SerializedName("hits")
    val hits: List<RecipeHitNetwork>,

    @SerializedName("_links")
    val links: LinksNetwork
)