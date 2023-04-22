package com.example.foodinfo.core.utils.view_model


interface Selectable<Key> {
    val ID: Key
    var isSelected: Boolean
}