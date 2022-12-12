package com.example.foodinfo.ui

import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentRecipeExtendedBinding
import com.example.foodinfo.repository.model.RecipeIngredientModel
import com.example.foodinfo.repository.model.RecipeModel
import com.example.foodinfo.repository.model.RecipeNutrientModel
import com.example.foodinfo.ui.adapter.RecipeCategoriesAdapter
import com.example.foodinfo.ui.custom_view.NonScrollLinearLayoutManager
import com.example.foodinfo.ui.decorator.ListVerticalItemDecoration
import com.example.foodinfo.utils.*
import com.example.foodinfo.utils.glide.GlideApp
import com.example.foodinfo.view_model.RecipeExtendedViewModel
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest


class RecipeExtendedFragment : BaseFragment<FragmentRecipeExtendedBinding>(
    FragmentRecipeExtendedBinding::inflate
) {

    private val args: RecipeExtendedFragmentArgs by navArgs()

    private val viewModel: RecipeExtendedViewModel by viewModels {
        requireActivity().appComponent.viewModelsFactory()
    }

    private lateinit var recyclerAdapter: RecipeCategoriesAdapter


    // no SupervisorJob on purpose
    private val initUiScope = CoroutineScope(Job() + Dispatchers.Default)

    enum class UIElements {
        RECIPE,
        LABELS,
        NUTRIENTS,
        INGREDIENTS
    }

    private val initUiElementsState: HashMap<UIElements, UiState> = hashMapOf(
        Pair(UIElements.RECIPE, UiState.Loading()),
        Pair(UIElements.LABELS, UiState.Loading()),
        Pair(UIElements.NUTRIENTS, UiState.Loading()),
        Pair(UIElements.INGREDIENTS, UiState.Loading())
    )


    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }

    private val onFavoriteClickListener: () -> Unit = {
        viewModel.updateFavoriteMark()
    }

    private val onShareClickListener: () -> Unit = { }

    private val onLabelClickListener: (String, String) -> Unit = { category, label ->
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val labelItem = viewModel.getLabel(category, label)
            withContext(Dispatchers.Main) {
                showDescriptionDialog(
                    labelItem.label,
                    labelItem.description,
                    labelItem.preview
                )
            }
        }
    }

    private val onNutrientsViewAllClickListener: () -> Unit = {
        findNavController().navigate(
            RecipeExtendedFragmentDirections.actionFRecipeExtendedToFRecipeNutrients(
                args.recipeId
            )
        )
    }

    private val onIngredientsViewAllClickListener: () -> Unit = {
        findNavController().navigate(
            RecipeExtendedFragmentDirections.actionFRecipeExtendedToFRecipeIngredients(
                args.recipeId
            )
        )
    }


    private fun updateState(key: UIElements, value: UiState) {
        initUiElementsState[key] = value
        initUiElementsState.values.firstOrNull { it is UiState.Error }.also {
            it?.let { uiState ->
                uiState as UiState.Error
                updateUiState(UiState.Error(uiState.message, uiState.error))
                return
            }
        }
        if (initUiElementsState.map { it.value is UiState.Success }.all { it }) {
            updateUiState(UiState.Success())
        } else {
            updateUiState(UiState.Loading())
        }
    }


    override fun onDestroy() {
        initUiScope.cancel()
        super.onDestroy()
    }


    override fun initUI() {
        recyclerAdapter = RecipeCategoriesAdapter(
            requireContext(),
            onLabelClickListener
        )

        viewModel.recipeId = args.recipeId
        binding.btnBack.setOnClickListener { onBackClickListener() }
        binding.btnShare.setOnClickListener { onShareClickListener() }
        binding.btnFavorite.setOnClickListener { onFavoriteClickListener() }
        binding.tvNutrientsViewAll.setOnClickListener { onNutrientsViewAllClickListener() }
        binding.tvIngredientsViewAll.setOnClickListener { onIngredientsViewAllClickListener() }

        with(binding.llCategories) {
            layoutManager = NonScrollLinearLayoutManager(context).also {
                it.orientation = LinearLayoutManager.VERTICAL
            }
            addItemDecoration(
                ListVerticalItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.recipe_extended_category_item_space)
                )
            )
            adapter = recyclerAdapter
        }
    }

    override fun subscribeUI() {

        observeUiState { uiState ->
            when (uiState) {
                is UiState.Error   -> {}
                is UiState.Success -> {
                    binding.pbContent.isVisible = false
                    binding.svContent.isVisible = true
                    binding.svContent.baseAnimation()
                }
                is UiState.Loading -> {
                    binding.pbContent.isVisible = true
                    binding.svContent.isVisible = false
                }
            }

        }

        initUiScope.launch {
            repeatOn(Lifecycle.State.STARTED) {
                viewModel.recipe.collectLatest { recipe ->
                    when (recipe) {
                        is State.Success -> {
                            initRecipe(recipe.data)
                            updateState(UIElements.RECIPE, UiState.Success())
                        }
                        is State.Error   -> {
                            initUiElementsState[UIElements.RECIPE] = UiState.Error(
                                recipe.message,
                                recipe.error
                            )
                            throw recipe.error
                        }
                        else             -> {}
                    }
                }
            }

            repeatOn(Lifecycle.State.STARTED) {
                viewModel.labels.collectLatest { labels ->
                    when (labels) {
                        is State.Success -> {
                            recyclerAdapter.submitList(labels.data) {
                                updateState(UIElements.LABELS, UiState.Success())
                            }
                        }
                        is State.Error   -> {
                            initUiElementsState[UIElements.LABELS] = UiState.Error(
                                labels.message,
                                labels.error
                            )
                            throw labels.error
                        }
                        else             -> {}
                    }
                }
            }

            repeatOn(Lifecycle.State.STARTED) {
                viewModel.nutrients.collectLatest { nutrients ->
                    when (nutrients) {
                        is State.Success -> {
                            initNutrients(nutrients.data)
                            updateState(UIElements.NUTRIENTS, UiState.Success())
                        }
                        is State.Error   -> {
                            initUiElementsState[UIElements.NUTRIENTS] = UiState.Error(
                                nutrients.message,
                                nutrients.error
                            )
                            throw nutrients.error
                        }
                        else             -> {}
                    }
                }
            }

            repeatOn(Lifecycle.State.STARTED) {
                viewModel.ingredients.collectLatest { ingredients ->
                    when (ingredients) {
                        is State.Success -> {
                            initIngredients(ingredients.data)
                            updateState(UIElements.INGREDIENTS, UiState.Success())
                        }
                        is State.Error   -> {
                            initUiElementsState[UIElements.INGREDIENTS] = UiState.Error(
                                ingredients.message,
                                ingredients.error
                            )
                            throw ingredients.error
                        }
                        else             -> {}
                    }
                }
            }
        }
    }


    private fun initRecipe(recipe: RecipeModel) {
        binding.tvRecipeName.text = recipe.name
        Glide.with(binding.ivRecipePreview.context)
            .load(recipe.previewURL)
            .error(R.drawable.ic_no_image)
            .placeholder(null)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.ivRecipePreview)

        binding.tvServingsValue.text = getString(
            R.string.serving_value,
            recipe.servings
        )
        binding.tvWeightValue.text = getString(
            R.string.gram_int_value,
            recipe.totalWeight
        )
        binding.tvTimeValue.text = getString(
            R.string.time_value,
            recipe.totalTime
        )

        binding.iEnergy.tvTitle.text = resources.getString(R.string.calories_header)
        binding.iEnergy.tvValue.text = recipe.calories
        binding.iEnergy.progressBar.progress = recipe.caloriesDaily

        binding.btnFavorite.setFavorite(
            recipe.isFavorite,
            falseColor = R.attr.appMainFontColor
        )
    }

    private fun initNutrients(nutrients: List<RecipeNutrientModel>) {
        nutrients.findLast { nutrient ->
            nutrient.label == resources.getString(R.string.protein_header)
        }!!.apply {
            binding.iProtein.tvTitle.text = label
            binding.iProtein.tvValue.text = getString(R.string.float_value, totalWeight)
            binding.iProtein.progressBar.progress = dailyPercent
        }

        nutrients.findLast { nutrient ->
            nutrient.label == resources.getString(R.string.carbs_header)
        }!!.apply {
            binding.iCarbs.tvTitle.text = label
            binding.iCarbs.tvValue.text = getString(R.string.float_value, totalWeight)
            binding.iCarbs.progressBar.progress = dailyPercent
        }

        nutrients.findLast { nutrient ->
            nutrient.label == resources.getString(R.string.fat_header)
        }!!.apply {
            binding.iFat.tvTitle.text = label
            binding.iFat.tvValue.text = getString(R.string.float_value, totalWeight)
            binding.iFat.progressBar.progress = dailyPercent
        }
    }

    private fun initIngredients(ingredients: List<RecipeIngredientModel>) {
        for (index in 0..3) {
            GlideApp.with(requireContext())
                .load(ingredients.getOrNull(index)?.previewURL)
                .into(binding.clIngredients[index] as ShapeableImageView)
        }
    }
}