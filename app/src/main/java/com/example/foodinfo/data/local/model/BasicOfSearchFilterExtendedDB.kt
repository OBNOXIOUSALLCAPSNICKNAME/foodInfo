package com.example.foodinfo.data.local.model


open class BasicOfSearchFilterExtendedDB(
    open val ID: Int,
    open val infoID: Int,
    open val filterName: String,
    open val minValue: Float?,
    open val maxValue: Float?,
    open val metadata: BasicOfRecipeMetadataDB?
)