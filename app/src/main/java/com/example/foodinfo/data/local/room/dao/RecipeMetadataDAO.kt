package com.example.foodinfo.data.local.room.dao

import androidx.room.*
import com.example.foodinfo.data.local.model.*
import com.example.foodinfo.data.local.room.model.entity.BasicOfRecipeMetadataEntity
import com.example.foodinfo.data.local.room.model.entity.CategoryOfRecipeMetadataEntity
import com.example.foodinfo.data.local.room.model.entity.LabelOfRecipeMetadataEntity
import com.example.foodinfo.data.local.room.model.entity.NutrientOfRecipeMetadataEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeMetadataDAO {

    @Query(
        "SELECT * FROM ${LabelOfRecipeMetadataDB.TABLE_NAME} WHERE " +
        "${LabelOfRecipeMetadataDB.Columns.ID} = :ID"
    )
    suspend fun getLabel(ID: Int): LabelOfRecipeMetadataEntity

    @Query(
        "SELECT * FROM ${NutrientOfRecipeMetadataDB.TABLE_NAME} WHERE " +
        "${NutrientOfRecipeMetadataDB.Columns.ID} = :ID"
    )
    suspend fun getNutrient(ID: Int): NutrientOfRecipeMetadataEntity


    @Query("SELECT * FROM ${BasicOfRecipeMetadataDB.TABLE_NAME}")
    fun observeBasicsAll(): Flow<List<BasicOfRecipeMetadataEntity>>

    @Query("SELECT * FROM ${LabelOfRecipeMetadataDB.TABLE_NAME}")
    fun observeLabelsAll(): Flow<List<LabelOfRecipeMetadataEntity>>

    @Query("SELECT * FROM ${NutrientOfRecipeMetadataDB.TABLE_NAME}")
    fun observeNutrientsAll(): Flow<List<NutrientOfRecipeMetadataEntity>>

    @Query("SELECT * FROM ${CategoryOfRecipeMetadataDB.TABLE_NAME}")
    fun observeCategoriesAll(): Flow<List<CategoryOfRecipeMetadataEntity>>

    @Transaction
    @Query(
        "SELECT * FROM ${LabelOfRecipeMetadataDB.TABLE_NAME} WHERE " +
        "${LabelOfRecipeMetadataDB.Columns.CATEGORY_ID} = :categoryID"
    )
    fun observeCategoryLabels(categoryID: Int): Flow<List<LabelOfRecipeMetadataEntity>>


    @Query("DELETE FROM ${BasicOfRecipeMetadataDB.TABLE_NAME}")
    suspend fun clearBasics()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBasics(basics: List<BasicOfRecipeMetadataEntity>)

    @Transaction
    suspend fun addBasics(basics: List<BasicOfRecipeMetadataEntity>) {
        clearBasics()
        insertBasics(basics)
    }

    @Query("DELETE FROM ${LabelOfRecipeMetadataDB.TABLE_NAME}")
    suspend fun clearLabels()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertLabels(labels: List<LabelOfRecipeMetadataEntity>)

    @Transaction
    suspend fun addLabels(labels: List<LabelOfRecipeMetadataEntity>) {
        clearLabels()
        insertLabels(labels)
    }

    @Query("DELETE FROM ${NutrientOfRecipeMetadataDB.TABLE_NAME}")
    suspend fun clearNutrients()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertNutrients(nutrients: List<NutrientOfRecipeMetadataEntity>)

    @Transaction
    suspend fun addNutrients(nutrients: List<NutrientOfRecipeMetadataEntity>) {
        clearNutrients()
        insertNutrients(nutrients)
    }

    @Query("DELETE FROM ${CategoryOfRecipeMetadataDB.TABLE_NAME}")
    suspend fun clearCategories()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCategories(categories: List<CategoryOfRecipeMetadataEntity>)

    @Transaction
    suspend fun addCategories(categories: List<CategoryOfRecipeMetadataEntity>) {
        clearCategories()
        insertCategories(categories)
    }

    @Transaction
    suspend fun addRecipeMetadata(
        basics: List<BasicOfRecipeMetadataEntity>,
        labels: List<LabelOfRecipeMetadataEntity>,
        nutrients: List<NutrientOfRecipeMetadataEntity>,
        categories: List<CategoryOfRecipeMetadataEntity>
    ) {
        addBasics(basics)
        addLabels(labels)
        addNutrients(nutrients)
        addCategories(categories)
    }
}