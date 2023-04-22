package com.example.foodinfo

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.foodinfo.data.local.model.EdamamCredentialsDB
import com.example.foodinfo.data.local.model.GitHubCredentialsDB
import com.example.foodinfo.data.local.room.model.entity.EdamamCredentialsEntity
import com.example.foodinfo.data.local.room.model.entity.GitHubCredentialsEntity
import com.example.foodinfo.data.local.room.model.entity.SearchFilterEntity
import com.example.foodinfo.data.local.room.model.entity.SearchInputEntity
import com.example.foodinfo.databinding.ActivityInitializationBinding
import com.example.foodinfo.ui.base.BaseActivity
import com.example.foodinfo.utils.extensions.appComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class InitializationActivity : BaseActivity<ActivityInitializationBinding>(
    ActivityInitializationBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                prepopulateDB()
            }
            startActivity(Intent(this@InitializationActivity, MainActivity::class.java))
            finish()
        }
    }

    private suspend fun prepopulateDB() {
        delay(1000) //TODO replace after tests

        appComponent.dataBase.apply {

            searchHistoryDAO.addHistory(
                listOf(
                    SearchInputEntity(inputText = "beer"),
                    SearchInputEntity(inputText = "meal"),
                    SearchInputEntity(inputText = "fast dinner"),
                    SearchInputEntity(inputText = "healthy drinks"),
                    SearchInputEntity(inputText = "fresh meat"),
                    SearchInputEntity(inputText = "fresh drinks"),
                    SearchInputEntity(inputText = "beef"),
                    SearchInputEntity(inputText = "boiled beef")
                )
            )

            apiCredentialsDAO.addEdamam(EdamamCredentialsEntity(EdamamCredentialsDB()))
            apiCredentialsDAO.addGitHub(GitHubCredentialsEntity(GitHubCredentialsDB()))

            searchFilterDAO.insertFilter(SearchFilterEntity(appComponent.prefUtils.searchFilter))
        }
    }
}