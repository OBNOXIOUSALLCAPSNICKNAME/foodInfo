package com.example.foodinfo.local.room.dao

import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.foodinfo.local.dao.RecipeDAO
import com.example.foodinfo.local.dto.*
import com.example.foodinfo.local.room.entity.*
import com.example.foodinfo.local.room.pojo.*
import kotlinx.coroutines.flow.Flow


@Dao
abstract class RecipeDAORoom : RecipeDAO {

    @Query(
        "SELECT * FROM ${RecipeDB.TABLE_NAME} WHERE " +
        "${RecipeDB.Columns.IS_FAVORITE} == 1"
    )
    abstract override fun getFavorite(): PagingSource<Int, RecipeEntity>

    @Query(
        "SELECT ${RecipeDB.Columns.ID} FROM ${RecipeDB.TABLE_NAME} WHERE " +
        "${RecipeDB.Columns.IS_FAVORITE} == 1"
    )
    abstract override fun getFavoriteIds(): List<String>

    @Transaction
    @RawQuery(
        observedEntities = [
            RecipeEntity::class,
            NutrientOfRecipeEntity::class,
            IngredientOfRecipeEntity::class,
            LabelOfRecipeEntity::class
        ]
    )
    abstract override fun getByFilter(query: SupportSQLiteQuery): PagingSource<Int, RecipeEntity>

    @Transaction
    @Query(
        "SELECT * FROM ${RecipeDB.TABLE_NAME} WHERE " +
        "${RecipeDB.Columns.ID} LIKE '%' || :recipeID || '%'"
    )
    abstract override fun getByIdExtended(recipeID: String): Flow<RecipeExtendedPOJO>

    @Query(
        "SELECT * FROM ${IngredientOfRecipeDB.TABLE_NAME} WHERE " +
        "${IngredientOfRecipeDB.Columns.RECIPE_ID} LIKE '%' || :recipeID || '%'"
    )
    abstract override fun getIngredients(recipeID: String): Flow<List<IngredientOfRecipeEntity>>

    @Transaction
    @Query(
        "SELECT * FROM ${NutrientOfRecipeDB.TABLE_NAME} WHERE " +
        "${NutrientOfRecipeDB.Columns.RECIPE_ID} LIKE '%' || :recipeID || '%'"
    )
    abstract override fun getNutrients(recipeID: String): Flow<List<NutrientOfRecipeExtendedPOJO>>

    @Transaction
    @Query(
        "SELECT * FROM ${LabelOfRecipeDB.TABLE_NAME} WHERE " +
        "${LabelOfRecipeDB.Columns.RECIPE_ID} LIKE '%' || :recipeID || '%'"
    )
    abstract override fun getLabels(recipeID: String): Flow<List<LabelOfRecipeExtendedPOJO>>


    @Query(
        "UPDATE ${RecipeDB.TABLE_NAME} SET " +
        "${RecipeDB.Columns.IS_FAVORITE} = NOT ${RecipeDB.Columns.IS_FAVORITE} " +
        "WHERE ${RecipeDB.Columns.ID} = :recipeID"
    )
    abstract override fun invertFavoriteStatus(recipeID: String)

    @Query(
        "UPDATE ${RecipeDB.TABLE_NAME} SET " +
        "${RecipeDB.Columns.IS_FAVORITE} = 0 " +
        "WHERE ${RecipeDB.Columns.ID} IN (:recipeIDs)"
    )
    abstract override fun delFromFavorite(recipeIDs: List<String>)

    @Query(
        "UPDATE ${RecipeDB.TABLE_NAME} SET " +
        "${RecipeDB.Columns.IS_FAVORITE} = 1 " +
        "WHERE ${RecipeDB.Columns.ID} IN (:recipeIDs)"
    )
    abstract fun addToFavorite(recipeIDs: List<String>)


    @Query(
        "SELECT ${RecipeDB.Columns.ID}, ${RecipeDB.Columns.IS_FAVORITE} FROM ${RecipeDB.TABLE_NAME} WHERE " +
        "${RecipeDB.Columns.ID} = :recipeID"
    )
    abstract fun getFavoriteMark(recipeID: String): Boolean

    @MapInfo(keyColumn = RecipeDB.Columns.ID, valueColumn = RecipeDB.Columns.IS_FAVORITE)
    @Query(
        "SELECT ${RecipeDB.Columns.ID}, ${RecipeDB.Columns.IS_FAVORITE} FROM ${RecipeDB.TABLE_NAME} WHERE " +
        "${RecipeDB.Columns.ID} IN (:recipeIDs)"
    )
    abstract fun getFavoriteMarks(recipeIDs: List<String>): Map<String, Boolean>


    @Query(
        "DELETE FROM ${LabelOfRecipeDB.TABLE_NAME} WHERE " +
        "${LabelOfRecipeDB.Columns.RECIPE_ID} IN (:recipeIDs)"
    )
    abstract fun removeLabels(recipeIDs: List<String>)

    @Query(
        "DELETE FROM ${NutrientOfRecipeDB.TABLE_NAME} WHERE " +
        "${NutrientOfRecipeDB.Columns.RECIPE_ID} IN (:recipeIDs)"
    )
    abstract fun removeNutrients(recipeIDs: List<String>)

    @Query(
        "DELETE FROM ${IngredientOfRecipeDB.TABLE_NAME} WHERE " +
        "${IngredientOfRecipeDB.Columns.RECIPE_ID} IN (:recipeIDs)"
    )
    abstract fun removeIngredients(recipeIDs: List<String>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addRecipeEntity(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun insertRecipesEntity(recipes: List<RecipeEntity>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract fun updateRecipesEntity(recipes: List<RecipeEntity>)


    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun insertLabelsEntity(labels: List<LabelOfRecipeEntity>)

    @Transaction
    override fun addLabels(labels: List<LabelOfRecipeDB>) {
        removeLabels(labels.map { it.recipeID }.distinct())
        insertLabelsEntity(labels.map { LabelOfRecipeEntity.toEntity(it) })
    }

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun insertNutrientsEntity(nutrients: List<NutrientOfRecipeEntity>)

    @Transaction
    override fun addNutrients(nutrients: List<NutrientOfRecipeDB>) {
        removeNutrients(nutrients.map { it.recipeID }.distinct())
        insertNutrientsEntity(nutrients.map { NutrientOfRecipeEntity.toEntity(it) })
    }

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun insertIngredientsEntity(ingredients: List<IngredientOfRecipeEntity>)

    @Transaction
    override fun addIngredients(ingredients: List<IngredientOfRecipeDB>) {
        removeIngredients(ingredients.map { it.recipeID }.distinct())
        insertIngredientsEntity(ingredients.map { IngredientOfRecipeEntity.toEntity(it) })
    }

    @Transaction
    override fun addRecipes(recipes: List<RecipeDB>) {
        val favoriteMarks = getFavoriteMarks(recipes.map { it.ID })

        recipes.map { RecipeEntity.toEntity(it) }.partition { it.ID in favoriteMarks.keys }
            .also { (toUpdate, toInsert) ->
                insertRecipesEntity(toInsert)
                updateRecipesEntity(toUpdate)
                addToFavorite(favoriteMarks.mapNotNull { (ID, isFavorite) -> ID.takeIf { isFavorite } })
            }
    }

    @Transaction
    override fun addRecipeExtended(recipe: RecipeExtendedDB) {
        val favoriteMark = getFavoriteMark(recipe.ID)

        addRecipeEntity(RecipeExtendedPOJO.toEntity(recipe))

        insertLabelsEntity(recipe.labels.map { LabelOfRecipeExtendedPOJO.toEntity(it) })
        insertNutrientsEntity(recipe.nutrients.map { NutrientOfRecipeExtendedPOJO.toEntity(it) })
        insertIngredientsEntity(recipe.ingredients.map { IngredientOfRecipeEntity.toEntity(it) })

        if (favoriteMark) {
            invertFavoriteStatus(recipe.ID)
        }
    }
}