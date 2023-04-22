package com.example.foodinfo.data.local.model


open class RecipeMetadataDB(
    open val basics: List<BasicOfRecipeMetadataDB>,
    open val labels: List<LabelOfRecipeMetadataDB>,
    open val nutrients: List<NutrientOfRecipeMetadataDB>,
    open val categories: List<CategoryOfRecipeMetadataDB>
) {

    override fun toString(): String {
        return "${basics}${labels}${nutrients}${categories}"
    }
}