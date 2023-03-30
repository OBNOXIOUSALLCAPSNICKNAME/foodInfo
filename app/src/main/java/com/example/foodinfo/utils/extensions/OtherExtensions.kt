package com.example.foodinfo.utils.extensions

import com.example.foodinfo.repository.state_handling.State
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot


val String.measureSpacer
    get() = when (this) {
        "g",
        "mg",
        "µg" -> ""
        else -> " "
    }

fun String.trimMultiline() = this.trimIndent().replace(System.lineSeparator(), "")

inline fun <reified T> Gson.fromString(value: String): T = fromJson(value, object : TypeToken<T>() {}.type)

fun <T> Flow<State<T>>.filterState(useLoadingData: Boolean) = this
    .filterNot(State.Utils::isEmptyLoading)
    .distinctUntilChanged(if (useLoadingData) State.Utils::isEqualInsensitive else State.Utils::isEqual)

fun Float.toPercent(total: Float) = if (total == 0f) 100 else (this * 100 / total).toInt()