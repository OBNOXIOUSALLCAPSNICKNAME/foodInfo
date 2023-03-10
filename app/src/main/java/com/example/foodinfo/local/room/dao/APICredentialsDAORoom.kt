package com.example.foodinfo.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodinfo.local.dao.APICredentialsDAO
import com.example.foodinfo.local.dto.APICredentialsDB
import com.example.foodinfo.local.room.entity.APICredentialsEntity


@Dao
abstract class APICredentialsDAORoom : APICredentialsDAO {

    @Query(
        "SELECT * FROM ${APICredentialsDB.TABLE_NAME} " +
        "WHERE ${APICredentialsDB.Columns.NAME} LIKE '%' || :name || '%' "
    )
    abstract override fun getCredentials(name: String): APICredentialsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addCredentialsEntity(credentials: APICredentialsEntity)

    override fun addCredentials(credentials: APICredentialsDB) {
        addCredentialsEntity(APICredentialsEntity.toEntity(credentials))
    }
}