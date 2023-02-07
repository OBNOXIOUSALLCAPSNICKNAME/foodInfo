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
        "SELECT * FROM ${LabelOfSearchFilterDB.TABLE_NAME} WHERE " +
                "${LabelOfSearchFilterDB.Columns.FILTER_NAME} LIKE '%' || :filterName || '%'"
    )
    abstract fun getLabelsPOJO(filterName: String): List<LabelOfSearchFilterExtendedPOJO>

    override fun getLabels(filterName: String): List<LabelOfSearchFilterExtendedDB> {
        return getLabelsPOJO(filterName)
    }

    @Transaction
    @Query(
        "SELECT * FROM ${NutrientOfSearchFilterDB.TABLE_NAME} WHERE " +
                "${NutrientOfSearchFilterDB.Columns.FILTER_NAME} LIKE '%' || :filterName || '%'"
    )
    abstract fun getNutrientsPOJO(filterName: String): List<NutrientOfSearchFilterExtendedPOJO>

    override fun getNutrients(filterName: String): List<NutrientOfSearchFilterExtendedDB> {
        return getNutrientsPOJO(filterName)
    }

    @Transaction
    @Query(
        "SELECT * FROM ${BasicOfSearchFilterDB.TABLE_NAME} WHERE " +
                "${BasicOfSearchFilterDB.Columns.FILTER_NAME} LIKE '%' || :filterName || '%'"
    )
    abstract fun getBasicsPOJO(filterName: String): List<BasicOfSearchFilterExtendedPOJO>

    override fun getBasics(filterName: String): List<BasicOfSearchFilterExtendedDB> {
        return getBasicsPOJO(filterName)
    }

    @Transaction
    @Query(
        "SELECT * FROM ${SearchFilterDB.TABLE_NAME} WHERE " +
                "${SearchFilterDB.Columns.NAME} LIKE '%' || :filterName || '%'"
    )
    abstract fun getFilterExtendedPOJO(filterName: String): SearchFilterExtendedPOJO

    override fun getFilterExtended(filterName: String): SearchFilterExtendedDB {
        return getFilterExtendedPOJO(filterName)
    }


    @Transaction
    @Query(
        "SELECT * FROM ${LabelOfSearchFilterDB.TABLE_NAME} WHERE " +
                "${LabelOfSearchFilterDB.Columns.FILTER_NAME} LIKE '%' || :filterName || '%'"
    )
    abstract fun observeLabelsPOJO(filterName: String): Flow<List<LabelOfSearchFilterExtendedPOJO>>

    override fun observeLabels(filterName: String): Flow<List<LabelOfSearchFilterExtendedDB>> {
        return observeLabelsPOJO(filterName)
    }

    @Transaction
    @Query(
        "SELECT * FROM ${NutrientOfSearchFilterDB.TABLE_NAME} WHERE " +
                "${NutrientOfSearchFilterDB.Columns.FILTER_NAME} LIKE '%' || :filterName || '%'"
    )
    abstract fun observeNutrientsPOJO(filterName: String): Flow<List<NutrientOfSearchFilterExtendedPOJO>>

    override fun observeNutrients(filterName: String): Flow<List<NutrientOfSearchFilterExtendedDB>> {
        return observeNutrientsPOJO(filterName)
    }

    @Transaction
    @Query(
        "SELECT * FROM ${SearchFilterDB.TABLE_NAME} WHERE " +
                "${SearchFilterDB.Columns.NAME} LIKE '%' || :filterName || '%'"
    )
    abstract fun observeFilterExtendedPOJO(filterName: String): Flow<SearchFilterExtendedPOJO>

    override fun observeFilterExtended(filterName: String): Flow<SearchFilterExtendedDB> {
        return observeFilterExtendedPOJO(filterName)
    }


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertFilterEntity(filter: SearchFilterEntity): Long

    @Insert
    abstract fun insertBasicsEntity(baseFields: List<BasicOfSearchFilterEntity>)

    @Insert
    abstract fun insertLabelsEntity(labels: List<LabelOfSearchFilterEntity>)

    @Insert
    abstract fun insertNutrientsEntity(nutrients: List<NutrientOfSearchFilterEntity>)


    @Query(
        "UPDATE ${BasicOfSearchFilterDB.TABLE_NAME} SET " +
                "${BasicOfSearchFilterDB.Columns.MIN_VALUE} = :minValue," +
                "${BasicOfSearchFilterDB.Columns.MAX_VALUE} = :maxValue " +
                "WHERE ${BasicOfSearchFilterDB.Columns.ID} == :id"
    )
    abstract override fun updateBasic(id: Int, minValue: Float, maxValue: Float)

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
    abstract override fun updateNutrient(id: Int, minValue: Float, maxValue: Float)


    @Update
    abstract fun updateBasicsEntity(baseFields: List<BasicOfSearchFilterEntity>)

    override fun updateBasics(basics: List<BasicOfSearchFilterDB>) {
        updateBasicsEntity(basics.map { BasicOfSearchFilterEntity.toEntity(it) })
    }

    @Update
    abstract fun updateLabelsEntity(labels: List<LabelOfSearchFilterEntity>)

    override fun updateLabels(labels: List<LabelOfSearchFilterDB>) {
        updateLabelsEntity(labels.map { LabelOfSearchFilterEntity.toEntity(it) })
    }

    @Update
    abstract fun updateNutrientsEntity(nutrients: List<NutrientOfSearchFilterEntity>)

    override fun updateNutrients(nutrients: List<NutrientOfSearchFilterDB>) {
        updateNutrientsEntity(nutrients.map { NutrientOfSearchFilterEntity.toEntity(it) })
    }


    @Transaction
    override fun invalidateFilter(
        filterName: String,
        basics: List<BasicOfSearchFilterDB>?,
        labels: List<LabelOfSearchFilterDB>?,
        nutrients: List<NutrientOfSearchFilterDB>?
    ) {
        if (basics != null) {
            deleteBasics(filterName)
            insertBasicsEntity(basics.map { BasicOfSearchFilterEntity.toEntity(it) })
        }
        if (labels != null) {
            deleteLabels(filterName)
            insertLabelsEntity(labels.map { LabelOfSearchFilterEntity.toEntity(it) })
        }
        if (nutrients != null) {
            deleteNutrients(filterName)
            insertNutrientsEntity(nutrients.map { NutrientOfSearchFilterEntity.toEntity(it) })
        }
    }

    @Query(
        "DELETE FROM ${BasicOfSearchFilterDB.TABLE_NAME} WHERE " +
                "${BasicOfSearchFilterDB.Columns.FILTER_NAME} LIKE :filterName"
    )
    abstract fun deleteBasics(filterName: String)

    @Transaction
    override fun invalidateBasics(filterName: String, basics: List<BasicOfSearchFilterDB>) {
        deleteBasics(filterName)
        insertBasicsEntity(basics.map { BasicOfSearchFilterEntity.toEntity(it) })
    }

    @Query(
        "DELETE FROM ${LabelOfSearchFilterDB.TABLE_NAME} WHERE " +
                "${LabelOfSearchFilterDB.Columns.FILTER_NAME} LIKE :filterName"
    )
    abstract fun deleteLabels(filterName: String)

    @Transaction
    override fun invalidateLabels(filterName: String, labels: List<LabelOfSearchFilterDB>) {
        deleteLabels(filterName)
        insertLabelsEntity(labels.map { LabelOfSearchFilterEntity.toEntity(it) })
    }

    @Query(
        "DELETE FROM ${NutrientOfSearchFilterDB.TABLE_NAME} WHERE " +
                "${NutrientOfSearchFilterDB.Columns.FILTER_NAME} LIKE :filterName"
    )
    abstract fun deleteNutrients(filterName: String)

    @Transaction
    override fun invalidateNutrients(filterName: String, nutrients: List<NutrientOfSearchFilterDB>) {
        deleteNutrients(filterName)
        insertNutrientsEntity(nutrients.map { NutrientOfSearchFilterEntity.toEntity(it) })
    }


    @Transaction
    override fun insertFilter(
        filterName: String,
        basics: List<BasicOfSearchFilterDB>,
        labels: List<LabelOfSearchFilterDB>,
        nutrients: List<NutrientOfSearchFilterDB>
    ) {
        val success = insertFilterEntity(SearchFilterEntity(name = filterName))
        if (success > 0) {
            insertBasicsEntity(basics.map { BasicOfSearchFilterEntity.toEntity(it) })
            insertLabelsEntity(labels.map { LabelOfSearchFilterEntity.toEntity(it) })
            insertNutrientsEntity(nutrients.map { NutrientOfSearchFilterEntity.toEntity(it) })
        }
    }
}