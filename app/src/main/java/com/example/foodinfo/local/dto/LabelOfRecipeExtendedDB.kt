package com.example.foodinfo.local.dto


open class LabelOfRecipeExtendedDB(
    open val ID: Int,
    open val infoID: Int,
    open val recipeID: String,
    open val attrInfo: LabelRecipeAttrExtendedDB
)