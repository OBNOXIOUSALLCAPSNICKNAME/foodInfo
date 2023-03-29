package com.example.foodinfo.utils.paging

import com.example.foodinfo.local.dto.EdamamCredentialsDB
import com.example.foodinfo.local.dto.RecipeAttrsDB
import com.example.foodinfo.repository.model.SearchFilterPresetModel
import com.example.foodinfo.utils.edamam.FieldSet


/**
 * Class that contains all necessary data (such as [RoomPageQuery] and [EdamamPageURL] to perform query
 * and [RecipeAttrsDB] to map remote model into local) to fetch recipe page.
 */
class PageFetchHelper(
    val attrs: RecipeAttrsDB,
    val isOnline: Boolean,
    searchFilterPreset: SearchFilterPresetModel,
    apiCredentials: EdamamCredentialsDB,
    fieldSet: FieldSet,
    inputText: String,
) {
    val localQuery = RoomPageQuery(searchFilterPreset, inputText, isOnline)
    val remoteQuery = EdamamPageURL(searchFilterPreset, apiCredentials, fieldSet, inputText)

    override fun equals(other: Any?) =
        other is PageFetchHelper &&
        this.isOnline == other.isOnline &&
        this.attrs == other.attrs

    override fun hashCode(): Int {
        var result = attrs.hashCode()
        result = 31 * result + isOnline.hashCode()
        return result
    }
}