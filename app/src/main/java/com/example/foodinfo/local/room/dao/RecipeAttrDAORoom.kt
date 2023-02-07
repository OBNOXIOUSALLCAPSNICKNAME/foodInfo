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
import com.example.foodinfo.local.room.pojo.LabelRecipeAttrExtendedPOJO


@Dao
abstract class RecipeAttrDAORoom : RecipeAttrDAO {

    @Query(
        "SELECT * FROM ${NutrientRecipeAttrDB.TABLE_NAME} " +
                "WHERE ${NutrientRecipeAttrDB.Columns.ID} = :ID"
    )
    abstract override fun getNutrient(ID: Int): NutrientRecipeAttrEntity


    @Query(
        "SELECT * FROM ${LabelRecipeAttrDB.TABLE_NAME} " +
                "WHERE ${LabelRecipeAttrDB.Columns.ID} = :ID"
    )
    abstract override fun getLabel(ID: Int): LabelRecipeAttrEntity

    @Query(
        "SELECT * FROM ${LabelRecipeAttrDB.TABLE_NAME} " +
                "WHERE ${LabelRecipeAttrDB.Columns.CATEGORY_ID} = :categoryID"
    )
    abstract fun getCategoryLabelsEntity(categoryID: Int): List<LabelRecipeAttrEntity>

    override fun getCategoryLabels(categoryID: Int): List<LabelRecipeAttrDB> {
        return getCategoryLabelsEntity(categoryID)
    }

    @Query(
        "SELECT * FROM ${CategoryRecipeAttrDB.TABLE_NAME} " +
                "WHERE ${CategoryRecipeAttrDB.Columns.ID} = :ID"
    )
    abstract override fun getCategory(ID: Int): CategoryRecipeAttrEntity


    @Transaction
    override fun getRecipeAttrs(): RecipeAttrsDB {
        return RecipeAttrsDB(
            basics = getBasicsAllEntity(),
            labels = getLabelsAllEntity(),
            nutrients = getNutrientsAllEntity(),
            categories = getCategoriesAllEntity(),
        )
    }


    @Query("SELECT * FROM ${CategoryRecipeAttrDB.TABLE_NAME}")
    abstract fun getCategoriesAllEntity(): List<CategoryRecipeAttrEntity>

    override fun getCategoriesAll(): List<CategoryRecipeAttrDB> {
        return getCategoriesAllEntity()
    }

    @Query("SELECT * FROM ${NutrientRecipeAttrDB.TABLE_NAME}")
    abstract fun getNutrientsAllEntity(): List<NutrientRecipeAttrEntity>

    override fun getNutrientsAll(): List<NutrientRecipeAttrDB> {
        return getNutrientsAllEntity()
    }

    @Query("SELECT * FROM ${BasicRecipeAttrDB.TABLE_NAME}")
    abstract fun getBasicsAllEntity(): List<BasicRecipeAttrEntity>

    override fun getBasicsAll(): List<BasicRecipeAttrDB> {
        return getBasicsAllEntity()
    }

    @Query("SELECT * FROM ${LabelRecipeAttrDB.TABLE_NAME}")
    abstract fun getLabelsAllEntity(): List<LabelRecipeAttrEntity>

    override fun getLabelsAll(): List<LabelRecipeAttrDB> {
        return getLabelsAllEntity()
    }

    @Query("SELECT * FROM ${LabelRecipeAttrDB.TABLE_NAME}")
    abstract fun getLabelsAllPojo(): List<LabelRecipeAttrExtendedPOJO>

    override fun getLabelsExtendedAll(): List<LabelRecipeAttrExtendedDB> {
        return getLabelsAllPojo()
    }


    @Insert
    abstract fun addNutrientsEntity(attrs: List<NutrientRecipeAttrEntity>)

    override fun addNutrients(attrs: List<NutrientRecipeAttrDB>) {
        addNutrientsEntity(attrs.map { NutrientRecipeAttrEntity.toEntity(it) })
    }

    @Insert
    abstract fun addBasicsEntity(attrs: List<BasicRecipeAttrEntity>)

    override fun addBasics(attrs: List<BasicRecipeAttrDB>) {
        addBasicsEntity(attrs.map { BasicRecipeAttrEntity.toEntity(it) })
    }

    @Insert
    abstract fun addLabelsEntity(attrs: List<LabelRecipeAttrEntity>)

    override fun addLabels(attrs: List<LabelRecipeAttrDB>) {
        addLabelsEntity(attrs.map { LabelRecipeAttrEntity.toEntity(it) })
    }

    @Insert
    abstract fun addCategoriesEntity(attrs: List<CategoryRecipeAttrEntity>)

    override fun addCategories(attrs: List<CategoryRecipeAttrDB>) {
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