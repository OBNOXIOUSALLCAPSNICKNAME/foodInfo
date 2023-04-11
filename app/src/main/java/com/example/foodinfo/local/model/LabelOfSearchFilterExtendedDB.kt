package com.example.foodinfo.local.model


open class LabelOfSearchFilterExtendedDB(
    open val ID: Int,
    open val filterName: String,
    open val infoID: Int,
    open val isSelected: Boolean,
    open val attrInfo: LabelRecipeAttrExtendedDB?
)