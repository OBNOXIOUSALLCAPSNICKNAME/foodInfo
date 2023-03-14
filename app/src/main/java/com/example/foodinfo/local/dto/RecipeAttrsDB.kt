package com.example.foodinfo.local.dto


open class RecipeAttrsDB(
    open val basics: List<BasicRecipeAttrDB>,
    open val labels: List<LabelRecipeAttrDB>,
    open val nutrients: List<NutrientRecipeAttrDB>,
    open val categories: List<CategoryRecipeAttrDB>
) {

    override fun toString(): String {
        return "${basics}${labels}${nutrients}${categories}"
    }
}