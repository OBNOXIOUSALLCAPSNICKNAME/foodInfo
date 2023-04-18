package com.example.foodinfo.data.local.room.dao

import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.foodinfo.data.local.model.*
import com.example.foodinfo.data.local.room.model.entity.IngredientOfRecipeEntity
import com.example.foodinfo.data.local.room.model.entity.LabelOfRecipeEntity
import com.example.foodinfo.data.local.room.model.entity.NutrientOfRecipeEntity
import com.example.foodinfo.data.local.room.model.entity.RecipeEntity
import com.example.foodinfo.data.local.room.model.pojo.LabelOfRecipeExtendedPOJO
import com.example.foodinfo.data.local.room.model.pojo.NutrientOfRecipeExtendedPOJO
import com.example.foodinfo.data.local.room.model.pojo.RecipeExtendedPOJO
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeDAO {

    @Query(
        "SELECT * FROM ${RecipeDB.TABLE_NAME} WHERE " +
        "${RecipeDB.Columns.IS_FAVORITE} == 1"
    )
    fun getFavorite(): PagingSource<Int, RecipeEntity>

    @Query(
        "SELECT ${RecipeDB.Columns.ID} FROM ${RecipeDB.TABLE_NAME} WHERE " +
        "${RecipeDB.Columns.IS_FAVORITE} == 1"
    )
    suspend fun getFavoriteIds(): List<String>


    @Query(
        "SELECT COUNT(${RecipeDB.Columns.ID}) FROM ${RecipeDB.TABLE_NAME} WHERE " +
        "${RecipeDB.Columns.IS_FAVORITE} == 1"
    )
    fun getFavoriteCount(): Flow<Int>

    @Transaction
    @RawQuery(
        observedEntities = [
            RecipeEntity::class,
            NutrientOfRecipeEntity::class,
            IngredientOfRecipeEntity::class,
            LabelOfRecipeEntity::class
        ]
    )
    fun getByFilter(query: SupportSQLiteQuery): PagingSource<Int, RecipeEntity>

    @Transaction
    @Query(
        "SELECT * FROM ${RecipeDB.TABLE_NAME} WHERE " +
        "${RecipeDB.Columns.ID} LIKE '%' || :recipeID || '%'"
    )
    fun getByIdExtended(recipeID: String): Flow<RecipeExtendedPOJO>

    @Query(
        "SELECT * FROM ${IngredientOfRecipeDB.TABLE_NAME} WHERE " +
        "${IngredientOfRecipeDB.Columns.RECIPE_ID} LIKE '%' || :recipeID || '%'"
    )
    fun getIngredients(recipeID: String): Flow<List<IngredientOfRecipeEntity>>

    @Transaction
    @Query(
        "SELECT * FROM ${NutrientOfRecipeDB.TABLE_NAME} WHERE " +
        "${NutrientOfRecipeDB.Columns.RECIPE_ID} LIKE '%' || :recipeID || '%'"
    )
    fun getNutrients(recipeID: String): Flow<List<NutrientOfRecipeExtendedPOJO>>

    @Transaction
    @Query(
        "SELECT * FROM ${LabelOfRecipeDB.TABLE_NAME} WHERE " +
        "${LabelOfRecipeDB.Columns.RECIPE_ID} LIKE '%' || :recipeID || '%'"
    )
    fun getLabels(recipeID: String): Flow<List<LabelOfRecipeExtendedPOJO>>


    @Query(
        "UPDATE ${RecipeDB.TABLE_NAME} SET " +
        "${RecipeDB.Columns.IS_FAVORITE} = NOT ${RecipeDB.Columns.IS_FAVORITE} " +
        "WHERE ${RecipeDB.Columns.ID} = :recipeID"
    )
    suspend fun invertFavoriteStatus(recipeID: String)

    @Query(
        "UPDATE ${RecipeDB.TABLE_NAME} SET " +
        "${RecipeDB.Columns.IS_FAVORITE} = 0 " +
        "WHERE ${RecipeDB.Columns.ID} IN (:recipeIDs)"
    )
    suspend fun delFromFavorite(recipeIDs: List<String>)

    @Query(
        "UPDATE ${RecipeDB.TABLE_NAME} SET " +
        "${RecipeDB.Columns.IS_FAVORITE} = 1 " +
        "WHERE ${RecipeDB.Columns.ID} IN (:recipeIDs)"
    )
    suspend fun addToFavorite(recipeIDs: List<String>)


    @Query(
        "SELECT ${RecipeDB.Columns.IS_FAVORITE} FROM ${RecipeDB.TABLE_NAME} WHERE " +
        "${RecipeDB.Columns.ID} = :recipeID"
    )
    suspend fun getFavoriteMark(recipeID: String): Boolean

    @Query(
        "SELECT ${RecipeDB.Columns.LAST_UPDATE} FROM ${RecipeDB.TABLE_NAME} WHERE " +
        "${RecipeDB.Columns.ID} = :recipeID"
    )
    suspend fun getLastUpdate(recipeID: String): Long

    @Query(
        "UPDATE ${RecipeDB.TABLE_NAME} SET " +
        "${RecipeDB.Columns.LAST_UPDATE} = :value " +
        "WHERE ${RecipeDB.Columns.ID} = :recipeID"
    )
    suspend fun setLastUpdate(recipeID: String, value: Long)


    @MapInfo(keyColumn = RecipeDB.Columns.ID, valueColumn = RecipeDB.Columns.IS_FAVORITE)
    @Query(
        "SELECT ${RecipeDB.Columns.ID}, ${RecipeDB.Columns.IS_FAVORITE} FROM ${RecipeDB.TABLE_NAME} WHERE " +
        "${RecipeDB.Columns.ID} IN (:recipeIDs)"
    )
    suspend fun getFavoriteMarks(recipeIDs: List<String>): Map<String, Boolean>


    @Query(
        "DELETE FROM ${LabelOfRecipeDB.TABLE_NAME} WHERE " +
        "${LabelOfRecipeDB.Columns.RECIPE_ID} IN (:recipeIDs)"
    )
    suspend fun removeLabels(recipeIDs: List<String>)

    @Query(
        "DELETE FROM ${NutrientOfRecipeDB.TABLE_NAME} WHERE " +
        "${NutrientOfRecipeDB.Columns.RECIPE_ID} IN (:recipeIDs)"
    )
    suspend fun removeNutrients(recipeIDs: List<String>)

    @Query(
        "DELETE FROM ${IngredientOfRecipeDB.TABLE_NAME} WHERE " +
        "${IngredientOfRecipeDB.Columns.RECIPE_ID} IN (:recipeIDs)"
    )
    suspend fun removeIngredients(recipeIDs: List<String>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertRecipes(recipes: List<RecipeEntity>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateRecipes(recipes: List<RecipeEntity>)


    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertLabels(labels: List<LabelOfRecipeEntity>)

    @Transaction
    suspend fun addLabels(labels: List<LabelOfRecipeEntity>) {
        removeLabels(labels.map { it.recipeID }.distinct())
        insertLabels(labels)
    }

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertNutrients(nutrients: List<NutrientOfRecipeEntity>)

    @Transaction
    suspend fun addNutrients(nutrients: List<NutrientOfRecipeEntity>) {
        removeNutrients(nutrients.map { it.recipeID }.distinct())
        insertNutrients(nutrients)
    }

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertIngredients(ingredients: List<IngredientOfRecipeEntity>)

    @Transaction
    suspend fun addIngredients(ingredients: List<IngredientOfRecipeEntity>) {
        removeIngredients(ingredients.map { it.recipeID }.distinct())
        insertIngredients(ingredients)
    }

    @Transaction
    suspend fun addRecipes(
        recipes: List<RecipeEntity>,
        labels: List<LabelOfRecipeEntity>,
        nutrients: List<NutrientOfRecipeEntity>,
        ingredients: List<IngredientOfRecipeEntity>
    ) {
        val favoriteMarks = getFavoriteMarks(recipes.map { it.ID })

        recipes.partition { it.ID in favoriteMarks.keys }
            .also { (toUpdate, toInsert) ->
                insertRecipes(toInsert)
                updateRecipes(toUpdate)
                addToFavorite(favoriteMarks.mapNotNull { (ID, isFavorite) -> ID.takeIf { isFavorite } })
            }

        addLabels(labels)
        addNutrients(nutrients)
        addIngredients(ingredients)
    }

    @Transaction
    suspend fun addRecipe(
        recipe: RecipeEntity,
        labels: List<LabelOfRecipeEntity>,
        nutrients: List<NutrientOfRecipeEntity>,
        ingredients: List<IngredientOfRecipeEntity>
    ) {
        val isFavorite = getFavoriteMark(recipe.ID)
        val lastUpdate = getLastUpdate(recipe.ID)

        insertRecipe(RecipeEntity(recipe))

        insertLabels(labels)
        insertNutrients(nutrients)
        insertIngredients(ingredients)

        if (isFavorite) {
            invertFavoriteStatus(recipe.ID)
        }
        setLastUpdate(recipe.ID, lastUpdate)
    }
}