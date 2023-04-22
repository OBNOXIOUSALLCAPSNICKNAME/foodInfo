package com.example.foodinfo.features.recipe.fragment

import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodinfo.R
import com.example.foodinfo.core.ui.base.adapter.AppListAdapter
import com.example.foodinfo.core.ui.base.adapter.appListAdapter
import com.example.foodinfo.core.utils.extensions.*
import com.example.foodinfo.core.utils.glide.GlideApp
import com.example.foodinfo.databinding.FragmentRecipeExtendedBinding
import com.example.foodinfo.features.recipe.adapter.categoryAdapterDelegate
import com.example.foodinfo.features.recipe.model.RecipeModel
import com.example.foodinfo.features.recipe.view_model.RecipeExtendedViewModel
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.min


class RecipeExtendedFragment : com.example.foodinfo.core.ui.base.BaseFragment<FragmentRecipeExtendedBinding>(
    FragmentRecipeExtendedBinding::inflate
) {

    private val onBackClickListener: () -> Unit = {
        findNavController().navigateUp()
    }

    private val onFavoriteClickListener: () -> Unit = {
        viewModel.invertFavoriteStatus()
    }

    private val onShareClickListener: () -> Unit = { }

    private val onLabelClickListener: (Int) -> Unit = { infoID ->
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val labelItem = viewModel.getLabelHint(infoID)
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
                args.recipeID
            )
        )
    }

    private val onIngredientsViewAllClickListener: () -> Unit = {
        findNavController().navigate(
            RecipeExtendedFragmentDirections.actionFRecipeExtendedToFRecipeIngredients(
                args.recipeID
            )
        )
    }


    private val args: RecipeExtendedFragmentArgs by navArgs()

    private val viewModel: RecipeExtendedViewModel by appViewModels()

    private val recyclerAdapter: AppListAdapter by appListAdapter(
        categoryAdapterDelegate(onLabelClickListener)
    )


    override fun initUI() {
        viewModel.recipeID = args.recipeID
        binding.btnBack.setOnClickListener { onBackClickListener() }
        binding.btnShare.setOnClickListener { onShareClickListener() }
        binding.btnFavorite.setOnClickListener { onFavoriteClickListener() }
        binding.tvNutrientsViewAll.setOnClickListener { onNutrientsViewAllClickListener() }
        binding.tvIngredientsViewAll.setOnClickListener { onIngredientsViewAllClickListener() }

        with(binding.llCategories) {
            layoutManager = com.example.foodinfo.core.ui.NonScrollLinearLayoutManager(context).also {
                it.orientation = LinearLayoutManager.VERTICAL
            }
            addItemDecoration(
                com.example.foodinfo.core.ui.ListItemDecoration(
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


    private fun initRecipe(recipe: RecipeModel) {
        with(binding) {
            tvRecipeName.text = recipe.name
            Glide.with(ivRecipePreview.context)
                .load(recipe.preview)
                .error(R.drawable.ic_no_image)
                .placeholder(null)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivRecipePreview)

            tvServingsValue.text = recipe.servings
            tvWeightValue.text = recipe.weight
            tvTimeValue.text = recipe.cookingTime

            btnFavorite.setFavorite(recipe.isFavorite, falseColor = R.attr.appMainFontColor)

            recipe.energy.apply {
                iEnergy.tvTitle.text = name
                iEnergy.tvValue.text = weight
                iEnergy.progressBar.progress = dailyPercentValue
            }

            recipe.protein.apply {
                iProtein.tvTitle.text = name
                iProtein.tvValue.text = weight
                iProtein.progressBar.progress = dailyPercentValue
            }

            recipe.carb.apply {
                iCarbs.tvTitle.text = name
                iCarbs.tvValue.text = weight
                iCarbs.progressBar.progress = dailyPercentValue
            }

            recipe.fat.apply {
                iFat.tvTitle.text = name
                iFat.tvValue.text = weight
                iFat.progressBar.progress = dailyPercentValue
            }


            for (index in 0 until min(clIngredients.size, recipe.ingredientPreviews.size)) {
                GlideApp.with(requireContext())
                    .load(recipe.ingredientPreviews[index])
                    .error(R.drawable.ic_no_image)
                    .into(clIngredients[index] as ShapeableImageView)
            }

            recyclerAdapter.submitList(recipe.categories)
        }
    }
}