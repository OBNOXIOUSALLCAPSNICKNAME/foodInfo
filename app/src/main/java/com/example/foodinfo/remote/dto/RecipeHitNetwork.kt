package com.example.foodinfo.remote.dto

import com.google.gson.annotations.SerializedName


data class RecipeHitNetwork(
    @SerializedName("recipe")
    val recipe: RecipeNetwork,

    @SerializedName("_links")
    val links: LinksNetwork
)