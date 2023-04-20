package com.example.foodinfo.data.local.model


open class LabelRecipeMetadataExtendedDB(
    open val ID: Int,
    open val categoryID: Int,
    open val tag: String,
    open val name: String,
    open val description: String,
    open val previewURL: String,
    open val categoryMetadata: CategoryOfRecipeMetadataDB?
)