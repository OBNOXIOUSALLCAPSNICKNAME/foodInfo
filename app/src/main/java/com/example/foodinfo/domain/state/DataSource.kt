package com.example.foodinfo.domain.state

import com.example.foodinfo.utils.ApiResponse
import kotlinx.coroutines.flow.Flow


/**
 * Wrapper interface that helps [BaseRepository.getData] to properly handle different data sources
 */
internal sealed interface DataSource<T> {
    data class Remote<T : Any>(val response: ApiResponse<T>) : DataSource<T>
    data class Local<T>(val data: T) : DataSource<T>
    data class LocalFlow<T>(val flow: Flow<T>) : DataSource<T>
    object Empty : DataSource<Unit>
}