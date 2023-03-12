package com.example.foodinfo.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.foodinfo.local.dto.GitHubCredentialsDB


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
        fun toEntity(item: GitHubCredentialsDB): GitHubCredentialsEntity {
            return GitHubCredentialsEntity(
                name = item.name,
                token = item.token
            )
        }
    }
}