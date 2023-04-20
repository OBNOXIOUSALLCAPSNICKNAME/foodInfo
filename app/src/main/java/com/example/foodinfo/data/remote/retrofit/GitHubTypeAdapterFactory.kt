package com.example.foodinfo.data.remote.retrofit

import android.util.Base64
import com.example.foodinfo.data.remote.model.RecipeMetadataNetwork
import com.example.foodinfo.utils.extensions.fromString
import com.example.foodinfo.utils.extensions.getTypeToken
import com.example.foodinfo.utils.extensions.read
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import javax.inject.Inject


private const val CONTENT = "content"

@Suppress("UNCHECKED_CAST")
class GitHubTypeAdapterFactory @Inject constructor(private val baseGson: Gson) : TypeAdapterFactory {
    override fun <T : Any?> create(gson: Gson, typeToken: TypeToken<T>): TypeAdapter<T>? {
        return when (typeToken) {
            getTypeToken<RecipeMetadataNetwork>() -> {
                RecipeMetadataTypeAdapter(baseGson) as TypeAdapter<T>
            }
            else                                  -> {
                null
            }
        }
    }
}

private class RecipeMetadataTypeAdapter(private val gson: Gson) : TypeAdapter<RecipeMetadataNetwork>() {
    override fun write(output: JsonWriter?, value: RecipeMetadataNetwork) {
        throw UnsupportedOperationException()
    }

    override fun read(input: JsonReader): RecipeMetadataNetwork {
        return input.read { tokenName ->
            if (tokenName == CONTENT) {
                gson.fromString(String(Base64.decode(input.nextString(), Base64.NO_PADDING)))
            } else {
                input.skipValue()
                null
            }
        }
    }
}