package com.example.foodinfo.utils.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


val String.measureSpacer
    get() = when (this) {
        "g",
        "mg",
        "µg" -> ""
        else -> " "
    }

fun String.trimMultiline() = this.trimIndent().replace(System.lineSeparator(), "")

inline fun <reified T> Gson.fromString(value: String): T = fromJson(value, object : TypeToken<T>() {}.type)
