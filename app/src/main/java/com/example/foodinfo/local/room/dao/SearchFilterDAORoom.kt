package com.example.foodinfo.local.room.dao

import androidx.room.*
import com.example.foodinfo.local.dao.SearchFilterDAO
import com.example.foodinfo.local.dto.*
import com.example.foodinfo.local.room.entity.*
import com.example.foodinfo.local.room.pojo.*
import kotlinx.coroutines.flow.Flow


@Dao
abstract class SearchFilterDAORoom : SearchFilterDAO {
    @Transaction
    @Query(
        "SELECT * FROM ${BasicOfSearchFilterDB.TABLE_NAME} WHERE " +
        "${BasicOfSearchFilterDB.Columns.FILTER_NAME} LIKE '%' || :filterName || '%'"
    )
    abstract override fun getBasics(filterName: String): List<BasicOfSearchFilterExtendedPOJO>

    @Transaction
    @Query(
        "SELECT * FROM ${LabelOfSearchFilterDB.TABLE_NAME} WHERE " +
        "${LabelOfSearchFilterDB.Columns.FILTER_NAME} LIKE '%' || :filterName || '%'"
    )
    abstract override fun getLabels(filterName: String): List<LabelOfSearchFilterExtendedPOJO>

    @Transaction
    @Query(
        "SELECT * FROM ${NutrientOfSearchFilterDB.TABLE_NAME} WHERE " +
        "${NutrientOfSearchFilterDB.Columns.FILTER_NAME} LIKE '%' || :filterName || '%'"
    )
    abstract override fun getNutrients(filterName: String): List<NutrientOfSearchFilterExtendedPOJO>

    @Transaction
    @Query(
        "SELECT * FROM ${SearchFilterDB.TABLE_NAME} WHERE " +
        "${SearchFilterDB.Columns.NAME} LIKE '%' || :filterName || '%'"
    )
    abstract override fun getFilterExtended(filterName: String): SearchFilterExtendedPOJO


    @Transaction
    @Query(
        "SELECT * FROM ${BasicOfSearchFilterDB.TABLE_NAME} WHERE " +
        "${BasicOfSearchFilterDB.Columns.FILTER_NAME} LIKE '%' || :filterName || '%'"
    )
    abstract override fun observeBasics(filterName: String): Flow<List<BasicOfSearchFilterExtendedPOJO>>

    @Transaction
    @Query(
        "SELECT * FROM ${LabelOfSearchFilterDB.TABLE_NAME} WHERE " +
        "${LabelOfSearchFilterDB.Columns.FILTER_NAME} LIKE '%' || :filterName || '%'"
    )
    abstract override fun observeLabels(filterName: String): Flow<List<LabelOfSearchFilterExtendedPOJO>>

    @Transaction
    @Query(
        "SELECT * FROM ${NutrientOfSearchFilterDB.TABLE_NAME} WHERE " +
        "${NutrientOfSearchFilterDB.Columns.FILTER_NAME} LIKE '%' || :filterName || '%'"
    )
    abstract override fun observeNutrients(filterName: String): Flow<List<NutrientOfSearchFilterExtendedPOJO>>

    @Transaction
    @Query(
        "SELECT * FROM ${SearchFilterDB.TABLE_NAME} WHERE " +
        "${SearchFilterDB.Columns.NAME} LIKE '%' || :filterName || '%'"
    )
    abstract override fun observeFilterExtended(filterName: String): Flow<SearchFilterExtendedPOJO>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertFilterEntity(filter: SearchFilterEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insertBasicsEntity(baseFields: List<BasicOfSearchFilterEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insertLabelsEntity(labels: List<LabelOfSearchFilterEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insertNutrientsEntity(nutrients: List<NutrientOfSearchFilterEntity>)


    @Query(
        "UPDATE ${BasicOfSearchFilterDB.TABLE_NAME} SET " +
        "${BasicOfSearchFilterDB.Columns.MIN_VALUE} = :minValue," +
        "${BasicOfSearchFilterDB.Columns.MAX_VALUE} = :maxValue " +
        "WHERE ${BasicOfSearchFilterDB.Columns.ID} == :id"
    )
    abstract override fun updateBasic(id: Int, minValue: Float?, maxValue: Float?)

    @Query(
        "UPDATE ${LabelOfSearchFilterDB.TABLE_NAME} SET " +
        "${LabelOfSearchFilterDB.Columns.IS_SELECTED} = :isSelected " +
        "WHERE ${LabelOfSearchFilterDB.Columns.ID} == :id"
    )
    abstract override fun updateLabel(id: Int, isSelected: Boolean)

    @Query(
        "UPDATE ${NutrientOfSearchFilterDB.TABLE_NAME} SET " +
        "${NutrientOfSearchFilterDB.Columns.MIN_VALUE} = :minValue," +
        "${NutrientOfSearchFilterDB.Columns.MAX_VALUE} = :maxValue " +
        "WHERE ${NutrientOfSearchFilterDB.Columns.ID} == :id"
    )
    abstract override fun updateNutrient(id: Int, minValue: Float?, maxValue: Float?)


    @Transaction
    override fun updateFilter(
        basics: List<BasicOfSearchFilterDB>,
        labels: List<LabelOfSearchFilterDB>,
        nutrients: List<NutrientOfSearchFilterDB>
    ) {
        updateBasicsEntity(basics.map { BasicOfSearchFilterEntity.fromDB(it) })
        updateLabelsEntity(labels.map { LabelOfSearchFilterEntity.fromDB(it) })
        updateNutrientsEntity(nutrients.map { NutrientOfSearchFilterEntity.fromDB(it) })
    }

    @Update(onConflict = OnConflictStrategy.ABORT)
    abstract fun updateBasicsEntity(baseFields: List<BasicOfSearchFilterEntity>)

    override fun updateBasics(basics: List<BasicOfSearchFilterDB>) {
        updateBasicsEntity(basics.map { BasicOfSearchFilterEntity.fromDB(it) })
    }

    @Update(onConflict = OnConflictStrategy.ABORT)
    abstract fun updateLabelsEntity(labels: List<LabelOfSearchFilterEntity>)

    override fun updateLabels(labels: List<LabelOfSearchFilterDB>) {
        updateLabelsEntity(labels.map { LabelOfSearchFilterEntity.fromDB(it) })
    }

    @Update(onConflict = OnConflictStrategy.ABORT)
    abstract fun updateNutrientsEntity(nutrients: List<NutrientOfSearchFilterEntity>)

    override fun updateNutrients(nutrients: List<NutrientOfSearchFilterDB>) {
        updateNutrientsEntity(nutrients.map { NutrientOfSearchFilterEntity.fromDB(it) })
    }


    @Transaction
    override suspend fun invalidateFilter(
        filterName: String,
        basics: List<BasicOfSearchFilterDB>?,
        labels: List<LabelOfSearchFilterDB>?,
        nutrients: List<NutrientOfSearchFilterDB>?
    ) {
        if (basics != null) {
            deleteBasics(filterName)
            insertBasicsEntity(basics.map { BasicOfSearchFilterEntity.fromDB(it) })
        }
        if (labels != null) {
            deleteLabels(filterName)
            insertLabelsEntity(labels.map { LabelOfSearchFilterEntity.fromDB(it) })
        }
        if (nutrients != null) {
            deleteNutrients(filterName)
            insertNutrientsEntity(nutrients.map { NutrientOfSearchFilterEntity.fromDB(it) })
        }
    }

    @Query(
        "DELETE FROM ${BasicOfSearchFilterDB.TABLE_NAME} WHERE " +
        "${BasicOfSearchFilterDB.Columns.FILTER_NAME} LIKE :filterName"
    )
    abstract fun deleteBasics(filterName: String)

    @Transaction
    override suspend fun invalidateBasics(filterName: String, basics: List<BasicOfSearchFilterDB>) {
        deleteBasics(filterName)
        insertBasicsEntity(basics.map { BasicOfSearchFilterEntity.fromDB(it) })
    }

    @Query(
        "DELETE FROM ${LabelOfSearchFilterDB.TABLE_NAME} WHERE " +
        "${LabelOfSearchFilterDB.Columns.FILTER_NAME} LIKE :filterName"
    )
    abstract fun deleteLabels(filterName: String)

    @Transaction
    override suspend fun invalidateLabels(filterName: String, labels: List<LabelOfSearchFilterDB>) {
        deleteLabels(filterName)
        insertLabelsEntity(labels.map { LabelOfSearchFilterEntity.fromDB(it) })
    }

    @Query(
        "DELETE FROM ${NutrientOfSearchFilterDB.TABLE_NAME} WHERE " +
        "${NutrientOfSearchFilterDB.Columns.FILTER_NAME} LIKE :filterName"
    )
    abstract fun deleteNutrients(filterName: String)

    @Transaction
    override suspend fun invalidateNutrients(filterName: String, nutrients: List<NutrientOfSearchFilterDB>) {
        deleteNutrients(filterName)
        insertNutrientsEntity(nutrients.map { NutrientOfSearchFilterEntity.fromDB(it) })
    }


    override suspend fun initializeEmptyFilter(filterName: String) {
        insertFilterEntity(SearchFilterEntity(name = filterName))
    }
}