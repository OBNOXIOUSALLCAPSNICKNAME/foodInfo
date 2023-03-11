package com.example.foodinfo.utils

import com.example.foodinfo.remote.dto.RecipeAttrsNetwork
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.*
import javax.inject.Inject


private const val CONTENT = "content"

@Suppress("UNCHECKED_CAST")
class GitHubTypeAdapterFactory @Inject constructor(private val baseGson: Gson) : TypeAdapterFactory {
    override fun <T : Any?> create(gson: Gson, typeToken: TypeToken<T>): TypeAdapter<T>? {
        return when (typeToken.rawType) {
            RecipeAttrsNetwork::class.java -> {
                RecipeAttrsTypeAdapter(baseGson) as TypeAdapter<T>
            }
            else                           -> {
                null
            }
        }
    }
}

private class RecipeAttrsTypeAdapter(private val gson: Gson) : TypeAdapter<RecipeAttrsNetwork>() {
    override fun write(output: JsonWriter?, value: RecipeAttrsNetwork) {
        throw UnsupportedOperationException()
    }

    override fun read(input: JsonReader): RecipeAttrsNetwork {
        input.decode { decoded ->
            return gson.fromString(decoded)
        }
        throw IllegalArgumentException()
    }
}

private inline fun <T> JsonReader.decode(parseDelegate: (String) -> T) {
    var decoded = ""
    this.beginObject()
    while (this.hasNext()) {
        if (this.nextName() == CONTENT) {
            // save decoded string into variable instead of calling parseDelegate() in case that
            // endObject() assertion must successfully complete before read() returns,
            // otherwise JsonIOException will be thrown with message: JSON document was not fully consumed
            // inspired by: https://github.com/google/gson/issues/835#issuecomment-213560446
            decoded = String(Base64.getMimeDecoder().decode(this.nextString()))
        } else {
            this.skipValue()
        }
    }
    this.endObject()
    parseDelegate(decoded)
}