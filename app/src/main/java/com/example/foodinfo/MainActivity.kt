package com.example.foodinfo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.foodinfo.databinding.ActivityMainBinding
import com.example.foodinfo.local.dto.*
import com.example.foodinfo.utils.AssetsKeyWords
import com.example.foodinfo.utils.JSONLoader
import com.example.foodinfo.utils.appComponent
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
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

        Log.d("123", "DB loaded in ${measureTimeMillis { prepopulateDB() }}ms")
    }

    private fun prepopulateDB() {
        val dataBase = appComponent.dataBase
        val jsonLoader = JSONLoader()
        val gson = GsonBuilder().create()

        dataBase.clearAllTables()


        val dbRecipes = jsonLoader.load(
            appComponent.assetProvider.getAsset(AssetsKeyWords.DB_RECIPES_100)
        )
        dataBase.recipeDAO.addRecipes(
            gson.fromJson(
                dbRecipes.get(AssetsKeyWords.RECIPES).toString(),
                object : TypeToken<List<RecipeDB>>() {}.type
            ),
            gson.fromJson(
                dbRecipes.get(AssetsKeyWords.NUTRIENTS).toString(),
                object : TypeToken<List<NutrientOfRecipeDB>>() {}.type
            ),
            gson.fromJson(
                dbRecipes.get(AssetsKeyWords.INGREDIENTS).toString(),
                object : TypeToken<List<IngredientOfRecipeDB>>() {}.type
            ),
            gson.fromJson(
                dbRecipes.get(AssetsKeyWords.LABELS).toString(),
                object : TypeToken<List<LabelOfRecipeDB>>() {}.type
            )
        )
        dataBase.searchHistoryDAO.addHistory(
            gson.fromJson(
                dbRecipes.get(AssetsKeyWords.SEARCH_HISTORY).toString(),
                object : TypeToken<List<SearchInputDB>>() {}.type
            )
        )


        val dbRecipeFieldsInfo = jsonLoader.load(
            appComponent.assetProvider.getAsset(AssetsKeyWords.DB_FIELDS_INFO)
        )
        dataBase.recipeAttrDao.addLabels(
            gson.fromJson(
                dbRecipeFieldsInfo.get(AssetsKeyWords.LABELS).toString(),
                object : TypeToken<List<LabelRecipeAttrDB>>() {}.type
            )
        )
        dataBase.recipeAttrDao.addCategories(
            gson.fromJson(
                dbRecipeFieldsInfo.get(AssetsKeyWords.CATEGORIES).toString(),
                object : TypeToken<List<CategoryRecipeAttrDB>>() {}.type
            )
        )
        dataBase.recipeAttrDao.addBasics(
            gson.fromJson(
                dbRecipeFieldsInfo.get(AssetsKeyWords.BASIC).toString(),
                object : TypeToken<List<BasicRecipeAttrDB>>() {}.type
            )
        )
        dataBase.recipeAttrDao.addNutrients(
            gson.fromJson(
                dbRecipeFieldsInfo.get(AssetsKeyWords.NUTRIENTS).toString(),
                object : TypeToken<List<NutrientRecipeAttrDB>>() {}.type
            )
        )

        appComponent.searchFilterRepository.createFilter()
    }
}