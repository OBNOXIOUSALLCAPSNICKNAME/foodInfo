package com.example.foodinfo.utils.view_model


interface Selectable<Key> {
    val ID: Key
    var isSelected: Boolean
}