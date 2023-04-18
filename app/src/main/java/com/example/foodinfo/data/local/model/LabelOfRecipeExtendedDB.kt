package com.example.foodinfo.data.local.model


open class LabelOfRecipeExtendedDB(
    open val ID: Int = 0,
    open val infoID: Int,
    open val recipeID: String,
    open val attrInfo: LabelRecipeAttrExtendedDB?
)