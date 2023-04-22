package com.example.foodinfo.di.module

import androidx.lifecycle.ViewModel
import com.example.foodinfo.features.category.viewm_model.LabelsOfCategoryViewModel
import com.example.foodinfo.features.explore.view_model.ExploreByLabelViewModel
import com.example.foodinfo.features.explore.view_model.ExploreByQueryViewModel
import com.example.foodinfo.features.favorite.view_model.FavoriteRecipesViewModel
import com.example.foodinfo.features.favorite.view_model.SortFavoriteRecipesViewModel
import com.example.foodinfo.features.home.view_model.HomeViewModel
import com.example.foodinfo.features.planner.PlannerViewModel
import com.example.foodinfo.features.recipe.view_model.IngredientsOfRecipeViewModel
import com.example.foodinfo.features.recipe.view_model.NutrientsOfRecipeViewModel
import com.example.foodinfo.features.recipe.view_model.RecipeExtendedViewModel
import com.example.foodinfo.features.search.view_model.SearchInputViewModel
import com.example.foodinfo.features.search_filter.view_model.CategoryOfSearchFilterViewModel
import com.example.foodinfo.features.search_filter.view_model.NutrientsOfSearchFilterViewModel
import com.example.foodinfo.features.search_filter.view_model.SearchFilterViewModel
import com.example.foodinfo.features.search_filter.view_model.SelectSearchFilterViewModel
import com.example.foodinfo.features.settings.SettingsViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass


@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindsHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecipeExtendedViewModel::class)
    abstract fun bindsRecipeExtendedViewModel(viewModel: RecipeExtendedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IngredientsOfRecipeViewModel::class)
    abstract fun bindsIngredientsOfRecipeViewModel(viewModel: IngredientsOfRecipeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NutrientsOfRecipeViewModel::class)
    abstract fun bindsNutrientsOfRecipeViewModel(viewModel: NutrientsOfRecipeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchFilterViewModel::class)
    abstract fun bindsSearchFilterViewModel(viewModel: SearchFilterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoryOfSearchFilterViewModel::class)
    abstract fun bindsCategoryOfSearchFilterViewModel(viewModel: CategoryOfSearchFilterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NutrientsOfSearchFilterViewModel::class)
    abstract fun bindsNutrientsOfSearchFilterViewModel(viewModel: NutrientsOfSearchFilterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectSearchFilterViewModel::class)
    abstract fun bindsSelectSearchFilterViewModel(viewModel: SelectSearchFilterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchInputViewModel::class)
    abstract fun bindsSearchInputViewModel(viewModel: SearchInputViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExploreByQueryViewModel::class)
    abstract fun bindsExploreByQueryViewModel(viewModel: ExploreByQueryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExploreByLabelViewModel::class)
    abstract fun bindsExploreByLabelViewModel(viewModel: ExploreByLabelViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LabelsOfCategoryViewModel::class)
    abstract fun bindsLabelsOfCategoryViewModel(viewModel: LabelsOfCategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindsSettingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlannerViewModel::class)
    abstract fun bindsPlannerViewModel(viewModel: PlannerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteRecipesViewModel::class)
    abstract fun bindsFavoriteRecipesViewModel(viewModel: FavoriteRecipesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SortFavoriteRecipesViewModel::class)
    abstract fun bindsSortFavoriteRecipesViewModel(viewModel: SortFavoriteRecipesViewModel): ViewModel
}