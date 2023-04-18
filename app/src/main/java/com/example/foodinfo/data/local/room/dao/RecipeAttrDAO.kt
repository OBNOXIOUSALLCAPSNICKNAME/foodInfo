package com.example.foodinfo.data.local.room.dao

import androidx.room.*
import com.example.foodinfo.data.local.model.*
import com.example.foodinfo.data.local.room.model.entity.BasicRecipeAttrEntity
import com.example.foodinfo.data.local.room.model.entity.CategoryRecipeAttrEntity
import com.example.foodinfo.data.local.room.model.entity.LabelRecipeAttrEntity
import com.example.foodinfo.data.local.room.model.entity.NutrientRecipeAttrEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeAttrDAO {

    @Query(
        "SELECT * FROM ${LabelRecipeAttrDB.TABLE_NAME} WHERE " +
        "${LabelRecipeAttrDB.Columns.ID} = :ID"
    )
    suspend fun getLabel(ID: Int): LabelRecipeAttrEntity

    @Query(
        "SELECT * FROM ${NutrientRecipeAttrDB.TABLE_NAME} WHERE " +
        "${NutrientRecipeAttrDB.Columns.ID} = :ID"
    )
    suspend fun getNutrient(ID: Int): NutrientRecipeAttrEntity


    @Query("SELECT * FROM ${BasicRecipeAttrDB.TABLE_NAME}")
    fun observeBasicsAll(): Flow<List<BasicRecipeAttrEntity>>

    @Query("SELECT * FROM ${LabelRecipeAttrDB.TABLE_NAME}")
    fun observeLabelsAll(): Flow<List<LabelRecipeAttrEntity>>

    @Query("SELECT * FROM ${NutrientRecipeAttrDB.TABLE_NAME}")
    fun observeNutrientsAll(): Flow<List<NutrientRecipeAttrEntity>>

    @Query("SELECT * FROM ${CategoryRecipeAttrDB.TABLE_NAME}")
    fun observeCategoriesAll(): Flow<List<CategoryRecipeAttrEntity>>

    @Transaction
    @Query(
        "SELECT * FROM ${LabelRecipeAttrDB.TABLE_NAME} WHERE " +
        "${LabelRecipeAttrDB.Columns.CATEGORY_ID} = :categoryID"
    )
    fun observeCategoryLabels(categoryID: Int): Flow<List<LabelRecipeAttrEntity>>


    @Query("DELETE FROM ${BasicRecipeAttrDB.TABLE_NAME}")
    suspend fun clearBasics()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBasics(attrs: List<BasicRecipeAttrEntity>)

    @Transaction
    suspend fun addBasics(attrs: List<BasicRecipeAttrEntity>) {
        clearBasics()
        insertBasics(attrs)
    }

    @Query("DELETE FROM ${LabelRecipeAttrDB.TABLE_NAME}")
    suspend fun clearLabels()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertLabels(attrs: List<LabelRecipeAttrEntity>)

    @Transaction
    suspend fun addLabels(attrs: List<LabelRecipeAttrEntity>) {
        clearLabels()
        insertLabels(attrs)
    }

    @Query("DELETE FROM ${NutrientRecipeAttrDB.TABLE_NAME}")
    suspend fun clearNutrients()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertNutrients(attrs: List<NutrientRecipeAttrEntity>)

    @Transaction
    suspend fun addNutrients(attrs: List<NutrientRecipeAttrEntity>) {
        clearNutrients()
        insertNutrients(attrs)
    }

    @Query("DELETE FROM ${CategoryRecipeAttrDB.TABLE_NAME}")
    suspend fun clearCategories()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCategories(attrs: List<CategoryRecipeAttrEntity>)

    @Transaction
    suspend fun addCategories(attrs: List<CategoryRecipeAttrEntity>) {
        clearCategories()
        insertCategories(attrs)
    }

    @Transaction
    suspend fun addRecipeAttrs(
        basics: List<BasicRecipeAttrEntity>,
        labels: List<LabelRecipeAttrEntity>,
        nutrients: List<NutrientRecipeAttrEntity>,
        categories: List<CategoryRecipeAttrEntity>
    ) {
        addBasics(basics)
        addLabels(labels)
        addNutrients(nutrients)
        addCategories(categories)
    }
}