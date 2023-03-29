package com.example.foodinfo.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodinfo.local.dao.APICredentialsDAO
import com.example.foodinfo.local.dto.EdamamCredentialsDB
import com.example.foodinfo.local.dto.GitHubCredentialsDB
import com.example.foodinfo.local.room.entity.EdamamCredentialsEntity
import com.example.foodinfo.local.room.entity.GitHubCredentialsEntity


@Dao
abstract class APICredentialsDAORoom : APICredentialsDAO {

    @Query(
        "SELECT * FROM ${EdamamCredentialsDB.TABLE_NAME} WHERE " +
        "${EdamamCredentialsDB.Columns.NAME} LIKE '%' || :name || '%' "
    )
    abstract override fun getEdamam(name: String): EdamamCredentialsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addEdamamEntity(credentials: EdamamCredentialsEntity)

    override fun addEdamam(credentials: EdamamCredentialsDB) {
        addEdamamEntity(EdamamCredentialsEntity.fromDB(credentials))
    }


    @Query(
        "SELECT * FROM ${GitHubCredentialsDB.TABLE_NAME} WHERE " +
        "${GitHubCredentialsDB.Columns.NAME} LIKE '%' || :name || '%' "
    )
    abstract override fun getGitHub(name: String): GitHubCredentialsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addGitHubEntity(credentials: GitHubCredentialsEntity)

    override fun addGitHub(credentials: GitHubCredentialsDB) {
        addGitHubEntity(GitHubCredentialsEntity.fromDB(credentials))
    }
}