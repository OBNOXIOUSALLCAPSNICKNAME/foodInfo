package com.example.foodinfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.foodinfo.databinding.ActivityMainBinding
import com.example.foodinfo.local.dto.EdamamCredentialsDB
import com.example.foodinfo.local.dto.GitHubCredentialsDB
import com.example.foodinfo.local.dto.SearchFilterDB
import com.example.foodinfo.utils.AssetsKeyWords
import com.example.foodinfo.utils.extensions.appComponent
import com.example.foodinfo.utils.extensions.fromString
import com.example.foodinfo.utils.extensions.openAsset


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        NavigationUI.setupWithNavController(binding.navView, navHost.navController)

        navHost.navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            when (destination.id) {
                R.id.f_home,
                R.id.f_favorite,
                R.id.f_planner,
                R.id.f_settings -> binding.navView.isVisible = true
                else            -> binding.navView.isVisible = false
            }
        }

        appComponent.prefUtils.githubCredentials = EdamamCredentialsDB.DEFAULT_NAME
        appComponent.prefUtils.edamamCredentials = EdamamCredentialsDB.DEFAULT_NAME
        appComponent.prefUtils.searchFilter = SearchFilterDB.DEFAULT_NAME

        prepopulateDB()
    }

    private fun prepopulateDB() {
        val dataBase = appComponent.dataBase
        val gson = appComponent.gson

        dataBase.clearAllTables()


        val dbRecipe = this.applicationContext.openAsset(AssetsKeyWords.DB_RECIPES_100)
        val dbRecipeAttrs = this.applicationContext.openAsset(AssetsKeyWords.DB_ATTRS)


        dataBase.recipeDAO.addRecipes(
            gson.fromString(dbRecipe.get(AssetsKeyWords.RECIPES).toString())
        )

        dataBase.recipeDAO.addLabels(
            gson.fromString(dbRecipe.get(AssetsKeyWords.LABELS).toString())
        )

        dataBase.recipeDAO.addNutrients(
            gson.fromString(dbRecipe.get(AssetsKeyWords.NUTRIENTS).toString())
        )

        dataBase.recipeDAO.addIngredients(
            gson.fromString(dbRecipe.get(AssetsKeyWords.INGREDIENTS).toString())
        )

        dataBase.searchHistoryDAO.addHistory(
            gson.fromString(dbRecipe.get(AssetsKeyWords.SEARCH_HISTORY).toString())
        )


        dataBase.recipeAttrDao.addLabels(
            gson.fromString(dbRecipeAttrs.get(AssetsKeyWords.LABELS).toString())
        )

        dataBase.recipeAttrDao.addCategories(
            gson.fromString(dbRecipeAttrs.get(AssetsKeyWords.CATEGORIES).toString())
        )

        dataBase.recipeAttrDao.addBasics(
            gson.fromString(dbRecipeAttrs.get(AssetsKeyWords.BASICS).toString())
        )

        dataBase.recipeAttrDao.addNutrients(
            gson.fromString(dbRecipeAttrs.get(AssetsKeyWords.NUTRIENTS).toString())
        )


        dataBase.apiCredentialsDao.addEdamam(EdamamCredentialsDB())
        dataBase.apiCredentialsDao.addGitHub(GitHubCredentialsDB())

        appComponent.searchFilterRepository.createFilter(
            appComponent.prefUtils.searchFilter,
            attrs = dataBase.recipeAttrDao.getRecipeAttrs(),
        )
    }
}