package com.example.foodinfo.repository.state_handling

import com.example.foodinfo.utils.ApiResponse
import kotlinx.coroutines.flow.Flow


/**
 * Wrapper interface that helps [BaseRepository.getData] to properly handle different data sources
 */
internal sealed interface DataProvider<T> {
    data class Remote<T : Any>(val response: ApiResponse<T>) : DataProvider<T>
    data class Local<T>(val data: T) : DataProvider<T>
    data class LocalFlow<T>(val flow: Flow<T>) : DataProvider<T>
    object Empty : DataProvider<Unit>
}