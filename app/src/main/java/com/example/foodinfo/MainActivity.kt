package com.example.foodinfo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.foodinfo.databinding.ActivityMainBinding
import com.example.foodinfo.local.dto.APICredentialsDB
import com.example.foodinfo.local.dto.SearchFilterDB
import com.example.foodinfo.utils.AssetsKeyWords
import com.example.foodinfo.utils.JSONLoader
import com.example.foodinfo.utils.appComponent
import com.example.foodinfo.utils.fromString
import kotlin.system.measureTimeMillis


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigationHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_recipes) as NavHostFragment
        val navController = navigationHost.navController


        NavigationUI.setupWithNavController(binding.navView, navController)

        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            when (destination.id) {
                R.id.f_home,
                R.id.f_favorite,
                R.id.f_planner,
                R.id.f_settings -> binding.navView.isVisible = true
                else            -> binding.navView.isVisible = false
            }
        }

        appComponent.prefUtils.apiCredentials = APICredentialsDB.DEFAULT_NAME
        appComponent.prefUtils.searchFilter = SearchFilterDB.DEFAULT_NAME

        Log.d("123", "DB loaded in ${measureTimeMillis { prepopulateDB() }}ms")
    }

    private fun prepopulateDB() {
        val dataBase = appComponent.dataBase
        val jsonLoader = JSONLoader()
        val gson = appComponent.gson

        dataBase.clearAllTables()


        val dbRecipe = jsonLoader.load(appComponent.assetProvider.getAsset(AssetsKeyWords.DB_RECIPES_100))
        val dbRecipeAttrs = jsonLoader.load(appComponent.assetProvider.getAsset(AssetsKeyWords.DB_ATTRS))


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


        dataBase.apiCredentialsDao.addCredentials(APICredentialsDB())

        appComponent.searchFilterRepository.createFilter(
            appComponent.prefUtils.searchFilter,
            attrs = dataBase.recipeAttrDao.getRecipeAttrs(),
        )
    }
}