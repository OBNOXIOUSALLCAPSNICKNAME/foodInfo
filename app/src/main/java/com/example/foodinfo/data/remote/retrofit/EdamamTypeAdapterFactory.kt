package com.example.foodinfo.data.remote.retrofit

import com.example.foodinfo.data.remote.model.*
import com.example.foodinfo.utils.extensions.fromReader
import com.example.foodinfo.utils.extensions.getTypeToken
import com.example.foodinfo.utils.extensions.read
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import javax.inject.Inject


/*
    JvmSuppressWildcards because for generic types like List or Map getTypeToken() returns
    List<? extends T> which is not equal to typeToken: List<T> provided by create().

    Unchecked cast instead of passing typeToken as parameter to classes with generic types like List and Map
    (RecipeNutrientsTypeAdapter and RecipeIngredientsTypeAdapter) because Gson.fromJson unable to get type
    from typeToken provided by create().
 */
@Suppress("UNCHECKED_CAST")
class EdamamTypeAdapterFactory @Inject constructor(private val baseGson: Gson) : TypeAdapterFactory {
    override fun <T : Any?> create(gson: Gson, typeToken: TypeToken<T>): TypeAdapter<T>? {
        return when (typeToken) {
            getTypeToken<RecipeNetwork>()                                              -> {
                RecipeTypeAdapter(baseGson) as TypeAdapter<T>
            }
            getTypeToken<RecipePageNetwork>()                                          -> {
                RecipePageTypeAdapter(baseGson) as TypeAdapter<T>
            }
            getTypeToken<List<@JvmSuppressWildcards IngredientOfRecipeNetwork>>()      -> {
                RecipeIngredientsTypeAdapter(baseGson) as TypeAdapter<T>
            }
            getTypeToken<Map<String, @JvmSuppressWildcards NutrientOfRecipeNetwork>>() -> {
                RecipeNutrientsTypeAdapter(baseGson) as TypeAdapter<T>
            }
            else                                                                       -> {
                null
            }
        }
    }
}

private class RecipePageTypeAdapter(
    private val gson: Gson
) : TypeAdapter<RecipePageNetwork>() {
    override fun write(output: JsonWriter?, value: RecipePageNetwork) {
        throw UnsupportedOperationException()
    }

    override fun read(input: JsonReader): RecipePageNetwork {
        return gson.fromReader(input)
    }
}

private class RecipeTypeAdapter(
    private val gson: Gson
) : TypeAdapter<RecipeNetwork>() {
    override fun write(output: JsonWriter?, value: RecipeNetwork) {
        throw UnsupportedOperationException()
    }

    override fun read(input: JsonReader): RecipeNetwork {
        return gson.fromReader(input)
    }
}

private class RecipeNutrientsTypeAdapter(
    private val gson: Gson
) : TypeAdapter<Map<String, NutrientOfRecipeNetwork>>() {
    override fun write(output: JsonWriter?, value: Map<String, NutrientOfRecipeNetwork>) {
        throw UnsupportedOperationException()
    }

    override fun read(input: JsonReader): Map<String, NutrientOfRecipeNetwork> {
        return input.read { tokenName ->
            when (tokenName) {
                RecipeNetwork.Fields.TOTAL_NUTRIENTS -> {
                    gson.fromReader(input)
                }
                RecipeHitNetwork.Fields.RECIPE       -> {
                    input.beginObject()
                    null
                }
                else                                 -> {
                    input.skipValue()
                    null
                }
            }
        }
    }
}

private class RecipeIngredientsTypeAdapter(
    private val gson: Gson
) : TypeAdapter<List<IngredientOfRecipeNetwork>>() {
    override fun write(output: JsonWriter?, value: List<IngredientOfRecipeNetwork>) {
        throw UnsupportedOperationException()
    }

    override fun read(input: JsonReader): List<IngredientOfRecipeNetwork> {
        return input.read { tokenName ->
            when (tokenName) {
                RecipeNetwork.Fields.INGREDIENTS -> {
                    gson.fromReader(input)
                }
                RecipeHitNetwork.Fields.RECIPE   -> {
                    input.beginObject()
                    null
                }
                else                             -> {
                    input.skipValue()
                    null
                }
            }
        }
    }
}