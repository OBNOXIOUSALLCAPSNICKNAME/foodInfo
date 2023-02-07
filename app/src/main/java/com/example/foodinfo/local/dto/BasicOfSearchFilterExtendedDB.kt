package com.example.foodinfo.local.dto


open class BasicOfSearchFilterExtendedDB(
    open val ID: Int,
    open val infoID: Int,
    open val filterName: String,
    open val minValue: Float,
    open val maxValue: Float,
    open val attrInfo: BasicRecipeAttrDB?
)