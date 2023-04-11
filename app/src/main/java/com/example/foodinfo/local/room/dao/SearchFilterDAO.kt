package com.example.foodinfo.local.room.dao

import androidx.room.*
import com.example.foodinfo.local.model.*
import com.example.foodinfo.local.room.model.entity.BasicOfSearchFilterEntity
import com.example.foodinfo.local.room.model.entity.LabelOfSearchFilterEntity
import com.example.foodinfo.local.room.model.entity.NutrientOfSearchFilterEntity
import com.example.foodinfo.local.room.model.entity.SearchFilterEntity
import com.example.foodinfo.local.room.model.pojo.BasicOfSearchFilterExtendedPOJO
import com.example.foodinfo.local.room.model.pojo.LabelOfSearchFilterExtendedPOJO
import com.example.foodinfo.local.room.model.pojo.NutrientOfSearchFilterExtendedPOJO
import com.example.foodinfo.local.room.model.pojo.SearchFilterExtendedPOJO
import kotlinx.coroutines.flow.Flow


@Dao
interface SearchFilterDAO {
    @Transaction
    @Query(
        "SELECT * FROM ${BasicOfSearchFilterDB.TABLE_NAME} WHERE " +
        "${BasicOfSearchFilterDB.Columns.FILTER_NAME} LIKE '%' || :filterName || '%'"
    )
    suspend fun getBasics(filterName: String): List<BasicOfSearchFilterExtendedPOJO>

    @Transaction
    @Query(
        "SELECT * FROM ${LabelOfSearchFilterDB.TABLE_NAME} WHERE " +
        "${LabelOfSearchFilterDB.Columns.FILTER_NAME} LIKE '%' || :filterName || '%'"
    )
    suspend fun getLabels(filterName: String): List<LabelOfSearchFilterExtendedPOJO>

    @Transaction
    @Query(
        "SELECT * FROM ${NutrientOfSearchFilterDB.TABLE_NAME} WHERE " +
        "${NutrientOfSearchFilterDB.Columns.FILTER_NAME} LIKE '%' || :filterName || '%'"
    )
    suspend fun getNutrients(filterName: String): List<NutrientOfSearchFilterExtendedPOJO>

    @Transaction
    @Query(
        "SELECT * FROM ${SearchFilterDB.TABLE_NAME} WHERE " +
        "${SearchFilterDB.Columns.NAME} LIKE '%' || :filterName || '%'"
    )
    suspend fun getFilterExtended(filterName: String): SearchFilterExtendedPOJO


    @Transaction
    @Query(
        "SELECT * FROM ${BasicOfSearchFilterDB.TABLE_NAME} WHERE " +
        "${BasicOfSearchFilterDB.Columns.FILTER_NAME} LIKE '%' || :filterName || '%'"
    )
    fun observeBasics(filterName: String): Flow<List<BasicOfSearchFilterExtendedPOJO>>

    @Transaction
    @Query(
        "SELECT * FROM ${LabelOfSearchFilterDB.TABLE_NAME} WHERE " +
        "${LabelOfSearchFilterDB.Columns.FILTER_NAME} LIKE '%' || :filterName || '%'"
    )
    fun observeLabels(filterName: String): Flow<List<LabelOfSearchFilterExtendedPOJO>>

    @Transaction
    @Query(
        "SELECT * FROM ${NutrientOfSearchFilterDB.TABLE_NAME} WHERE " +
        "${NutrientOfSearchFilterDB.Columns.FILTER_NAME} LIKE '%' || :filterName || '%'"
    )
    fun observeNutrients(filterName: String): Flow<List<NutrientOfSearchFilterExtendedPOJO>>

    @Transaction
    @Query(
        "SELECT * FROM ${SearchFilterDB.TABLE_NAME} WHERE " +
        "${SearchFilterDB.Columns.NAME} LIKE '%' || :filterName || '%'"
    )
    fun observeFilterExtended(filterName: String): Flow<SearchFilterExtendedPOJO>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFilter(filter: SearchFilterEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBasics(baseFields: List<BasicOfSearchFilterEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertLabels(labels: List<LabelOfSearchFilterEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertNutrients(nutrients: List<NutrientOfSearchFilterEntity>)


    @Query(
        "UPDATE ${BasicOfSearchFilterDB.TABLE_NAME} SET " +
        "${BasicOfSearchFilterDB.Columns.MIN_VALUE} = :minValue," +
        "${BasicOfSearchFilterDB.Columns.MAX_VALUE} = :maxValue " +
        "WHERE ${BasicOfSearchFilterDB.Columns.ID} == :id"
    )
    suspend fun updateBasic(id: Int, minValue: Float?, maxValue: Float?)

    @Query(
        "UPDATE ${LabelOfSearchFilterDB.TABLE_NAME} SET " +
        "${LabelOfSearchFilterDB.Columns.IS_SELECTED} = :isSelected " +
        "WHERE ${LabelOfSearchFilterDB.Columns.ID} == :id"
    )
    suspend fun updateLabel(id: Int, isSelected: Boolean)

    @Query(
        "UPDATE ${NutrientOfSearchFilterDB.TABLE_NAME} SET " +
        "${NutrientOfSearchFilterDB.Columns.MIN_VALUE} = :minValue," +
        "${NutrientOfSearchFilterDB.Columns.MAX_VALUE} = :maxValue " +
        "WHERE ${NutrientOfSearchFilterDB.Columns.ID} == :id"
    )
    suspend fun updateNutrient(id: Int, minValue: Float?, maxValue: Float?)


    @Transaction
    suspend fun updateFilter(
        basics: List<BasicOfSearchFilterEntity>,
        labels: List<LabelOfSearchFilterEntity>,
        nutrients: List<NutrientOfSearchFilterEntity>
    ) {
        updateBasics(basics)
        updateLabels(labels)
        updateNutrients(nutrients)
    }

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateBasics(baseFields: List<BasicOfSearchFilterEntity>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateLabels(labels: List<LabelOfSearchFilterEntity>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateNutrients(nutrients: List<NutrientOfSearchFilterEntity>)


    @Transaction
    suspend fun invalidateFilter(
        filterName: String,
        basics: List<BasicOfSearchFilterEntity>?,
        labels: List<LabelOfSearchFilterEntity>?,
        nutrients: List<NutrientOfSearchFilterEntity>?
    ) {
        basics?.let { invalidateBasics(filterName, it) }
        labels?.let { invalidateLabels(filterName, it) }
        nutrients?.let { invalidateNutrients(filterName, it) }
    }

    @Query(
        "DELETE FROM ${BasicOfSearchFilterDB.TABLE_NAME} WHERE " +
        "${BasicOfSearchFilterDB.Columns.FILTER_NAME} LIKE :filterName"
    )
    suspend fun deleteBasics(filterName: String)

    @Transaction
    suspend fun invalidateBasics(filterName: String, basics: List<BasicOfSearchFilterEntity>) {
        deleteBasics(filterName)
        insertBasics(basics)
    }

    @Query(
        "DELETE FROM ${LabelOfSearchFilterDB.TABLE_NAME} WHERE " +
        "${LabelOfSearchFilterDB.Columns.FILTER_NAME} LIKE :filterName"
    )
    suspend fun deleteLabels(filterName: String)

    @Transaction
    suspend fun invalidateLabels(filterName: String, labels: List<LabelOfSearchFilterEntity>) {
        deleteLabels(filterName)
        insertLabels(labels)
    }

    @Query(
        "DELETE FROM ${NutrientOfSearchFilterDB.TABLE_NAME} WHERE " +
        "${NutrientOfSearchFilterDB.Columns.FILTER_NAME} LIKE :filterName"
    )
    suspend fun deleteNutrients(filterName: String)

    @Transaction
    suspend fun invalidateNutrients(filterName: String, nutrients: List<NutrientOfSearchFilterEntity>) {
        deleteNutrients(filterName)
        insertNutrients(nutrients)
    }
}