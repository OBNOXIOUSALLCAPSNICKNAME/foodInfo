package com.example.foodinfo.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.foodinfo.local.dao.RecipeAttrDAO
import com.example.foodinfo.local.dto.*
import com.example.foodinfo.local.room.entity.BasicRecipeAttrEntity
import com.example.foodinfo.local.room.entity.CategoryRecipeAttrEntity
import com.example.foodinfo.local.room.entity.LabelRecipeAttrEntity
import com.example.foodinfo.local.room.entity.NutrientRecipeAttrEntity
import kotlinx.coroutines.flow.Flow


@Dao
abstract class RecipeAttrDAORoom : RecipeAttrDAO {

    @Query(
        "SELECT * FROM ${LabelRecipeAttrDB.TABLE_NAME} WHERE " +
        "${LabelRecipeAttrDB.Columns.ID} = :ID"
    )
    abstract override fun getLabel(ID: Int): LabelRecipeAttrEntity

    @Query(
        "SELECT * FROM ${NutrientRecipeAttrDB.TABLE_NAME} WHERE " +
        "${NutrientRecipeAttrDB.Columns.ID} = :ID"
    )
    abstract override fun getNutrient(ID: Int): NutrientRecipeAttrEntity

    @Query(
        "SELECT * FROM ${CategoryRecipeAttrDB.TABLE_NAME} WHERE " +
        "${CategoryRecipeAttrDB.Columns.ID} = :ID"
    )
    abstract override fun getCategory(ID: Int): CategoryRecipeAttrEntity


    @Query("SELECT * FROM ${BasicRecipeAttrDB.TABLE_NAME}")
    abstract override fun getBasicsAll(): List<BasicRecipeAttrEntity>

    @Query("SELECT * FROM ${LabelRecipeAttrDB.TABLE_NAME}")
    abstract override fun getLabelsAll(): List<LabelRecipeAttrEntity>

    @Query("SELECT * FROM ${NutrientRecipeAttrDB.TABLE_NAME}")
    abstract override fun getNutrientsAll(): List<NutrientRecipeAttrEntity>

    @Query("SELECT * FROM ${CategoryRecipeAttrDB.TABLE_NAME}")
    abstract override fun getCategoriesAll(): List<CategoryRecipeAttrEntity>

    @Transaction
    override fun getRecipeAttrs(): RecipeAttrsDB {
        return RecipeAttrsDB(
            basics = getBasicsAll(),
            labels = getLabelsAll(),
            nutrients = getNutrientsAll(),
            categories = getCategoriesAll()
        )
    }


    @Query("SELECT * FROM ${BasicRecipeAttrDB.TABLE_NAME}")
    abstract override fun observeBasicsAll(): Flow<List<BasicRecipeAttrEntity>>

    @Query("SELECT * FROM ${LabelRecipeAttrDB.TABLE_NAME}")
    abstract override fun observeLabelsAll(): Flow<List<LabelRecipeAttrEntity>>

    @Query("SELECT * FROM ${NutrientRecipeAttrDB.TABLE_NAME}")
    abstract override fun observeNutrientsAll(): Flow<List<NutrientRecipeAttrEntity>>

    @Query("SELECT * FROM ${CategoryRecipeAttrDB.TABLE_NAME}")
    abstract override fun observeCategoriesAll(): Flow<List<CategoryRecipeAttrEntity>>

    @Query(
        "SELECT * FROM ${LabelRecipeAttrDB.TABLE_NAME} WHERE " +
        "${LabelRecipeAttrDB.Columns.CATEGORY_ID} = :categoryID"
    )
    abstract override fun observeCategoryLabels(categoryID: Int): Flow<List<LabelRecipeAttrEntity>>


    @Query("DELETE FROM ${BasicRecipeAttrDB.TABLE_NAME}")
    abstract fun clearBasics()

    @Insert
    abstract fun addBasicsEntity(attrs: List<BasicRecipeAttrEntity>)

    override fun addBasics(attrs: List<BasicRecipeAttrDB>) {
        clearBasics()
        addBasicsEntity(attrs.map { BasicRecipeAttrEntity.toEntity(it) })
    }

    @Query("DELETE FROM ${LabelRecipeAttrDB.TABLE_NAME}")
    abstract fun clearLabels()

    @Insert
    abstract fun addLabelsEntity(attrs: List<LabelRecipeAttrEntity>)

    override fun addLabels(attrs: List<LabelRecipeAttrDB>) {
        clearLabels()
        addLabelsEntity(attrs.map { LabelRecipeAttrEntity.toEntity(it) })
    }

    @Query("DELETE FROM ${NutrientRecipeAttrDB.TABLE_NAME}")
    abstract fun clearNutrients()

    @Insert
    abstract fun addNutrientsEntity(attrs: List<NutrientRecipeAttrEntity>)

    override fun addNutrients(attrs: List<NutrientRecipeAttrDB>) {
        clearNutrients()
        addNutrientsEntity(attrs.map { NutrientRecipeAttrEntity.toEntity(it) })
    }

    @Query("DELETE FROM ${CategoryRecipeAttrDB.TABLE_NAME}")
    abstract fun clearCategories()

    @Insert
    abstract fun addCategoriesEntity(attrs: List<CategoryRecipeAttrEntity>)

    override fun addCategories(attrs: List<CategoryRecipeAttrDB>) {
        clearCategories()
        addCategoriesEntity(attrs.map { CategoryRecipeAttrEntity.toEntity(it) })
    }

    @Transaction
    override fun addRecipeAttrs(attrs: RecipeAttrsDB) {
        addBasics(attrs.basics)
        addLabels(attrs.labels)
        addNutrients(attrs.nutrients)
        addCategories(attrs.categories)
    }
}