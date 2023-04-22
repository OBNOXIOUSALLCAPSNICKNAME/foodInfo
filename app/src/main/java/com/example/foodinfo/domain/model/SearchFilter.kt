package com.example.foodinfo.domain.model


data class SearchFilter(
    val name: String,
    val basics: List<BasicOfSearchFilter>,
    val nutrients: List<NutrientOfSearchFilter>,
    val categories: List<CategoryOfSearchFilter>
)