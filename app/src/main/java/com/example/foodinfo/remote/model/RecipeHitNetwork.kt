package com.example.foodinfo.remote.model

import com.google.gson.annotations.SerializedName


data class RecipeHitNetwork(
    @SerializedName(Fields.RECIPE)
    val recipe: RecipeNetwork,

    @SerializedName(Fields.LINKS)
    val links: LinksNetwork
) {

    object Fields {
        const val RECIPE = "recipe"
        const val LINKS = "_links"
    }
}