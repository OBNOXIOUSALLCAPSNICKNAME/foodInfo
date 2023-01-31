package com.example.foodinfo.local.dto


open class NutrientOfRecipeExtendedDB(
    open val ID: Int,
    open val recipeID: String,
    open val infoID: Int,
    open val totalValue: Float,
    open val attrInfo: NutrientRecipeAttrDB
)