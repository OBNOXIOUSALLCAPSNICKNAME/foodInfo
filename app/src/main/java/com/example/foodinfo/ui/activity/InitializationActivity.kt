package com.example.foodinfo.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.foodinfo.databinding.ActivityInitializationBinding
import com.example.foodinfo.local.dto.EdamamCredentialsDB
import com.example.foodinfo.local.dto.GitHubCredentialsDB
import com.example.foodinfo.ui.base.BaseActivity
import com.example.foodinfo.utils.AssetsKeyWords
import com.example.foodinfo.utils.extensions.appComponent
import com.example.foodinfo.utils.extensions.fromString
import com.example.foodinfo.utils.extensions.openAsset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class InitializationActivity : BaseActivity<ActivityInitializationBinding>(
    ActivityInitializationBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val result = async(Dispatchers.IO) {
                prepopulateDB()
            }
            result.await()
            startActivity(Intent(this@InitializationActivity, MainActivity::class.java))
            finish()
        }
    }

    private suspend fun prepopulateDB() {
        delay(1000) //TODO replace after tests

        appComponent.dataBase.apply {

            searchHistoryDAO.addHistory(
                appComponent.gson.fromString(
                    this@InitializationActivity.applicationContext.openAsset(AssetsKeyWords.SEARCH_HISTORY)
                        .get(AssetsKeyWords.CONTENT)
                        .toString()
                )
            )

            apiCredentialsDao.addEdamam(EdamamCredentialsDB())
            apiCredentialsDao.addGitHub(GitHubCredentialsDB())

            searchFilterDAO.initializeEmptyFilter(appComponent.prefUtils.searchFilter)
        }
    }
}