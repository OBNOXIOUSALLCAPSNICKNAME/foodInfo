package com.example.foodinfo.local.room.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.foodinfo.local.model.GitHubCredentialsDB


@Entity(
    tableName = GitHubCredentialsDB.TABLE_NAME,
    indices = [Index(value = arrayOf(GitHubCredentialsDB.Columns.NAME), unique = true)]
)
data class GitHubCredentialsEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.NAME)
    override val name: String,

    @ColumnInfo(name = Columns.TOKEN)
    override val token: String

) : GitHubCredentialsDB(
    name = name,
    token = token
) {
    companion object {
        operator fun invoke(item: GitHubCredentialsDB): GitHubCredentialsEntity {
            return GitHubCredentialsEntity(
                name = item.name,
                token = item.token
            )
        }
    }
}