package com.example.foodinfo.repository

import com.example.foodinfo.local.dto.*
import com.example.foodinfo.repository.model.CategorySearchModel
import com.example.foodinfo.repository.model.LabelHintModel
import com.example.foodinfo.repository.model.LabelSearchModel
import com.example.foodinfo.repository.model.NutrientHintModel
import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.Flow


abstract class RecipeAttrRepository : BaseRepository() {

    abstract fun getNutrientHint(ID: Int): NutrientHintModel

    abstract fun getLabelHint(ID: Int): LabelHintModel

    abstract fun getLabelsSearch(categoryID: Int): Flow<State<List<LabelSearchModel>>>

    abstract fun getCategory(ID: Int): Flow<State<CategorySearchModel>>

    abstract fun getCategories(): Flow<State<List<CategorySearchModel>>>


    abstract fun getBasicsDB(): Flow<State<List<BasicRecipeAttrDB>>>

    abstract fun getLabelsDB(): Flow<State<List<LabelRecipeAttrDB>>>

    abstract fun getNutrientsDB(): Flow<State<List<NutrientRecipeAttrDB>>>

    abstract fun getCategoriesDB(): Flow<State<List<CategoryRecipeAttrDB>>>

    abstract fun getRecipeAttrsDB(): Flow<State<RecipeAttrsDB>>
}