package com.example.foodinfo.utils.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken


val String.measureSpacer
    get() = when (this) {
        "g",
        "mg",
        "µg" -> ""
        else -> " "
    }

fun String.trimMultiline() = this.trimIndent().replace(System.lineSeparator(), "")

fun Float.toPercent(total: Float) = if (total == 0f) 100 else (this * 100 / total).toInt()


inline fun <reified T> getTypeToken(): TypeToken<T> = object : TypeToken<T>() {}

inline fun <reified T> Gson.fromString(value: String): T {
    return fromJson(value, object : TypeToken<T>() {}.type)
}

inline fun <reified T> Gson.fromReader(value: JsonReader): T {
    return fromJson(value, object : TypeToken<T>() {}.type)
}

inline fun <T> JsonReader.read(crossinline onNextToken: (String) -> T?): T {
    var result: T? = null
    beginObject()
    while (peek() != JsonToken.END_DOCUMENT) {
        onNextToken(nextName())?.let { result = it }
        when (peek()) {
            JsonToken.END_OBJECT -> endObject()
            JsonToken.END_ARRAY  -> endArray()
            else                 -> {} // no-op
        }
    }
    return result!!
}