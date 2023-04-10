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
    abstract override suspend fun getFavoriteIds(): List<String>


    @Query(
        "SELECT COUNT(${RecipeDB.Columns.ID}) FROM ${RecipeDB.TABLE_NAME} WHERE " +
        "${RecipeDB.Columns.IS_FAVORITE} == 1"
    )
    abstract override fun getFavoriteCount(): Flow<Int>

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
    abstract override suspend fun delFromFavorite(recipeIDs: List<String>)

    @Query(
        "UPDATE ${RecipeDB.TABLE_NAME} SET " +
        "${RecipeDB.Columns.IS_FAVORITE} = 1 " +
        "WHERE ${RecipeDB.Columns.ID} IN (:recipeIDs)"
    )
    abstract fun addToFavorite(recipeIDs: List<String>)


    @Query(
        "SELECT ${RecipeDB.Columns.IS_FAVORITE} FROM ${RecipeDB.TABLE_NAME} WHERE " +
        "${RecipeDB.Columns.ID} = :recipeID"
    )
    abstract fun getFavoriteMark(recipeID: String): Boolean

    @Query(
        "SELECT ${RecipeDB.Columns.LAST_UPDATE} FROM ${RecipeDB.TABLE_NAME} WHERE " +
        "${RecipeDB.Columns.ID} = :recipeID"
    )
    abstract fun getLastUpdate(recipeID: String): Long

    @Query(
        "UPDATE ${RecipeDB.TABLE_NAME} SET " +
        "${RecipeDB.Columns.LAST_UPDATE} = :value " +
        "WHERE ${RecipeDB.Columns.ID} = :recipeID"
    )
    abstract fun setLastUpdate(recipeID: String, value: Long)


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
    abstract suspend fun removeLabels(recipeIDs: List<String>)

    @Query(
        "DELETE FROM ${NutrientOfRecipeDB.TABLE_NAME} WHERE " +
        "${NutrientOfRecipeDB.Columns.RECIPE_ID} IN (:recipeIDs)"
    )
    abstract suspend fun removeNutrients(recipeIDs: List<String>)

    @Query(
        "DELETE FROM ${IngredientOfRecipeDB.TABLE_NAME} WHERE " +
        "${IngredientOfRecipeDB.Columns.RECIPE_ID} IN (:recipeIDs)"
    )
    abstract suspend fun removeIngredients(recipeIDs: List<String>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addRecipeEntity(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insertRecipesEntity(recipes: List<RecipeEntity>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun updateRecipesEntity(recipes: List<RecipeEntity>)


    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insertLabelsEntity(labels: List<LabelOfRecipeEntity>)

    @Transaction
    override suspend fun addLabels(labels: List<LabelOfRecipeDB>) {
        removeLabels(labels.map { it.recipeID }.distinct())
        insertLabelsEntity(labels.map { LabelOfRecipeEntity(it) })
    }

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insertNutrientsEntity(nutrients: List<NutrientOfRecipeEntity>)

    @Transaction
    override suspend fun addNutrients(nutrients: List<NutrientOfRecipeDB>) {
        removeNutrients(nutrients.map { it.recipeID }.distinct())
        insertNutrientsEntity(nutrients.map { NutrientOfRecipeEntity(it) })
    }

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insertIngredientsEntity(ingredients: List<IngredientOfRecipeEntity>)

    @Transaction
    override suspend fun addIngredients(ingredients: List<IngredientOfRecipeDB>) {
        removeIngredients(ingredients.map { it.recipeID }.distinct())
        insertIngredientsEntity(ingredients.map { IngredientOfRecipeEntity(it) })
    }

    @Transaction
    override suspend fun addRecipes(recipes: List<RecipeToSaveDB>) {
        val favoriteMarks = getFavoriteMarks(recipes.map { it.ID })

        recipes.map { RecipeEntity(it) }.partition { it.ID in favoriteMarks.keys }
            .also { (toUpdate, toInsert) ->
                insertRecipesEntity(toInsert)
                updateRecipesEntity(toUpdate)
                addToFavorite(favoriteMarks.mapNotNull { (ID, isFavorite) -> ID.takeIf { isFavorite } })
            }

        addLabels(recipes.flatMap { it.labels })
        addNutrients(recipes.flatMap { it.nutrients })
        addIngredients(recipes.flatMap { it.ingredients })
    }

    @Transaction
    override suspend fun addRecipe(recipe: RecipeToSaveDB) {
        val isFavorite = getFavoriteMark(recipe.ID)
        val lastUpdate = getLastUpdate(recipe.ID)

        addRecipeEntity(RecipeEntity(recipe))

        insertLabelsEntity(recipe.labels.map { LabelOfRecipeEntity(it) })
        insertNutrientsEntity(recipe.nutrients.map { NutrientOfRecipeEntity(it) })
        insertIngredientsEntity(recipe.ingredients.map { IngredientOfRecipeEntity(it) })

        if (isFavorite) {
            invertFavoriteStatus(recipe.ID)
        }
        setLastUpdate(recipe.ID, lastUpdate)
    }
}