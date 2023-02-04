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


@Dao
interface RecipeAttrDAORoom : RecipeAttrDAO {

    @Query(
        "SELECT * FROM ${NutrientRecipeAttrDB.TABLE_NAME} " +
                "WHERE ${NutrientRecipeAttrDB.Columns.ID} = :ID"
    )
    override fun getNutrient(ID: Int): NutrientRecipeAttrEntity


    @Query(
        "SELECT * FROM ${LabelRecipeAttrDB.TABLE_NAME} " +
                "WHERE ${LabelRecipeAttrDB.Columns.ID} = :ID"
    )
    override fun getLabel(ID: Int): LabelRecipeAttrEntity

    @Query(
        "SELECT * FROM ${LabelRecipeAttrDB.TABLE_NAME} " +
                "WHERE ${LabelRecipeAttrDB.Columns.CATEGORY_ID} = :categoryID"
    )
    fun getCategoryLabelsEntity(categoryID: Int): List<LabelRecipeAttrEntity>

    override fun getCategoryLabels(categoryID: Int): List<LabelRecipeAttrDB> {
        return getCategoryLabelsEntity(categoryID)
    }

    @Query(
        "SELECT * FROM ${CategoryRecipeAttrDB.TABLE_NAME} " +
                "WHERE ${CategoryRecipeAttrDB.Columns.ID} = :ID"
    )
    override fun getCategory(ID: Int): CategoryRecipeAttrEntity


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
    fun getCategoriesAllEntity(): List<CategoryRecipeAttrEntity>

    override fun getCategoriesAll(): List<CategoryRecipeAttrDB> {
        return getCategoriesAllEntity()
    }

    @Query("SELECT * FROM ${NutrientRecipeAttrDB.TABLE_NAME}")
    fun getNutrientsAllEntity(): List<NutrientRecipeAttrEntity>

    override fun getNutrientsAll(): List<NutrientRecipeAttrDB> {
        return getNutrientsAllEntity()
    }

    @Query("SELECT * FROM ${BasicRecipeAttrDB.TABLE_NAME}")
    fun getBasicsAllEntity(): List<BasicRecipeAttrEntity>

    override fun getBasicsAll(): List<BasicRecipeAttrDB> {
        return getBasicsAllEntity()
    }

    @Query("SELECT * FROM ${LabelRecipeAttrDB.TABLE_NAME}")
    fun getLabelsAllEntity(): List<LabelRecipeAttrEntity>

    override fun getLabelsAll(): List<LabelRecipeAttrDB> {
        return getLabelsAllEntity()
    }


    @Insert
    fun addNutrientsEntity(attrs: List<NutrientRecipeAttrEntity>)

    override fun addNutrients(attrs: List<NutrientRecipeAttrDB>) {
        addNutrientsEntity(attrs.map { NutrientRecipeAttrEntity.toEntity(it) })
    }

    @Insert
    fun addBasicsEntity(attrs: List<BasicRecipeAttrEntity>)

    override fun addBasics(attrs: List<BasicRecipeAttrDB>) {
        addBasicsEntity(attrs.map { BasicRecipeAttrEntity.toEntity(it) })
    }

    @Insert
    fun addLabelsEntity(attrs: List<LabelRecipeAttrEntity>)

    override fun addLabels(attrs: List<LabelRecipeAttrDB>) {
        addLabelsEntity(attrs.map { LabelRecipeAttrEntity.toEntity(it) })
    }

    @Insert
    fun addCategoriesEntity(attrs: List<CategoryRecipeAttrEntity>)

    override fun addCategories(attrs: List<CategoryRecipeAttrDB>) {
        addCategoriesEntity(attrs.map { CategoryRecipeAttrEntity.toEntity(it) })
    }
}