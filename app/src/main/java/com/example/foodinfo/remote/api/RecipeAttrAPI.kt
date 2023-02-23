package com.example.foodinfo.remote.api

import com.example.foodinfo.remote.dto.RecipeAttrsNetwork


abstract class RecipeAttrAPI {

    abstract fun getRecipeAttrs(): RecipeAttrsNetwork
}