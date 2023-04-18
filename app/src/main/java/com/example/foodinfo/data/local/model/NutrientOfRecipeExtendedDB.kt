package com.example.foodinfo.data.local.model


open class NutrientOfRecipeExtendedDB(
    open val ID: Int = 0,
    open val recipeID: String,
    open val infoID: Int,
    open val value: Float,
    open val attrInfo: NutrientRecipeAttrDB?
)