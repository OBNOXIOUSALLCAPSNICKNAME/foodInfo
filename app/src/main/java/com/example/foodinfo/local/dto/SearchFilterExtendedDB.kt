package com.example.foodinfo.local.dto


open class SearchFilterExtendedDB(
    open val name: String,
    open val basics: List<BasicOfSearchFilterExtendedDB>,
    open val labels: List<LabelOfSearchFilterExtendedDB>,
    open val nutrients: List<NutrientOfSearchFilterExtendedDB>
)