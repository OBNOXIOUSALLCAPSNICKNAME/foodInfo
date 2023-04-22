package com.example.foodinfo.data.local.model


open class LabelOfSearchFilterExtendedDB(
    open val ID: Int,
    open val filterName: String,
    open val infoID: Int,
    open val isSelected: Boolean,
    open val metadata: LabelRecipeMetadataExtendedDB?
)