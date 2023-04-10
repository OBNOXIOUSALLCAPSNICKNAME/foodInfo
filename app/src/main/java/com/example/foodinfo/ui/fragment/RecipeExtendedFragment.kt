package com.example.foodinfo.ui.fragment

import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodinfo.R
import com.example.foodinfo.databinding.FragmentRecipeExtendedBinding
import com.example.foodinfo.repository.model.RecipeExtendedModel
import com.example.foodinfo.ui.adapter.RecipeCategoriesAdapter
import com.example.foodinfo.ui.base.BaseFragment
import com.example.foodinfo.ui.custom_view.NonScrollLinearLayoutManager
import com.example.foodinfo.ui.decorator.ListItemDecoration
import com.example.foodinfo.utils.extensions.*
import com.example.foodinfo.utils.glide.GlideApp
import com.example.foodinfo.view_model.RecipeExtendedViewModel
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.min


class RecipeExtendedFragment : BaseFragment<FragmentRecipeExtendedBinding>(
    FragmentRecipeExtendedBinding::inflate
) {

    private val args: RecipeExtendedFragmentArgs by navArgs()

    private val viewModel: RecipeExtendedViewModel by appViewModels()

    private lateinit var recyclerAdapter: RecipeCategoriesAdapter


    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }

    private val onFavoriteClickListener: () -> Unit = {
        viewModel.invertFavoriteStatus()
    }

    private val onShareClickListener: () -> Unit = { }

    private val onLabelClickListener: (Int) -> Unit = { infoID ->
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val labelItem = viewModel.getLabel(infoID)
            withContext(Dispatchers.Main) {
                showDescriptionDialog(
                    labelItem.name,
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


    override fun initUI() {
        recyclerAdapter = RecipeCategoriesAdapter(
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
                ListItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.recipe_extended_category_item_space),
                    RecyclerView.VERTICAL
                )
            )
            adapter = recyclerAdapter
        }
    }

    override fun subscribeUI() {
        observeState(
            dataFlow = viewModel.recipe,
            useLoadingData = true,
            onStart = {
                binding.svContent.isVisible = false
                binding.pbContent.isVisible = true
            },
            onFinish = {
                binding.pbContent.isVisible = false
                binding.svContent.baseAnimation()
            },
            onSuccess = ::initRecipe
        )
    }


    private fun initRecipe(recipe: RecipeExtendedModel) {
        with(binding) {
            tvRecipeName.text = recipe.name
            Glide.with(ivRecipePreview.context)
                .load(recipe.preview.toString())
                .error(R.drawable.ic_no_image)
                .placeholder(null)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivRecipePreview)

            tvServingsValue.text = getString(R.string.serving_value, recipe.servings)
            tvWeightValue.text = getString(R.string.gram_int_value, recipe.weight)
            tvTimeValue.text = recipe.cookingTime.toString()

            btnFavorite.setFavorite(recipe.isFavorite, falseColor = R.attr.appMainFontColor)

            recipe.energy.apply {
                iEnergy.tvTitle.text = name
                iEnergy.tvValue.text = totalWeight.toInt().toString()
                iEnergy.progressBar.progress = dailyPercent
            }

            recipe.protein.apply {
                iProtein.tvTitle.text = name
                iProtein.tvValue.text = getString(R.string.float_value, totalWeight)
                iProtein.progressBar.progress = dailyPercent
            }

            recipe.carb.apply {
                iCarbs.tvTitle.text = name
                iCarbs.tvValue.text = getString(R.string.float_value, totalWeight)
                iCarbs.progressBar.progress = dailyPercent
            }

            recipe.fat.apply {
                iFat.tvTitle.text = name
                iFat.tvValue.text = getString(R.string.float_value, totalWeight)
                iFat.progressBar.progress = dailyPercent
            }


            for (index in 0 until min(clIngredients.size, recipe.ingredientsPreviews.size)) {
                GlideApp.with(requireContext())
                    .load(recipe.ingredientsPreviews[index])
                    .error(R.drawable.ic_no_image)
                    .into(clIngredients[index] as ShapeableImageView)
            }

            recyclerAdapter.submitList(recipe.categories)
        }
    }
}