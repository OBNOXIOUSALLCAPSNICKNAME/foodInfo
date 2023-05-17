package com.example.foodinfo.core.utils.select_manager


interface Selectable<Key> {
    val ID: Key
    var isSelected: Boolean
}