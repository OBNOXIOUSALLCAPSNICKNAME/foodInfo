package com.example.foodinfo.core.utils.paging

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.foodinfo.core.utils.NoDataException
import kotlinx.coroutines.flow.StateFlow


/**
 * Wrapper for [PagingSource] that will map data from [originalSource] using [mapperDelegate] and return
 * [PagingSource.LoadResult.Error] with [NoDataException] when empty list was collected
 * from initial [originalSource].
 *
 * Main purpose of this wrapper is to be able to distinguish [PagingData.empty] provided by [StateFlow]
 * from empty list collected from [originalSource]'s [PagingSource.LoadParams.Refresh] (meaning no data
 * for a given query).
 */
class MapPagingSource<Key : Any, valueRawT : Any, valueOutT : Any>(
    private val originalSource: PagingSource<Key, valueRawT>,
    private val mapperDelegate: (valueRawT) -> valueOutT
) : PagingSource<Key, valueOutT>() {

    init {
        originalSource.registerInvalidatedCallback {
            this.invalidate()
        }
    }

    override val jumpingSupported = originalSource.jumpingSupported

    override fun getRefreshKey(state: PagingState<Key, valueOutT>): Key? {
        return originalSource.getRefreshKey(
            PagingState(
                pages = emptyList(),
                leadingPlaceholderCount = 0,
                anchorPosition = state.anchorPosition,
                config = state.config,
            )
        )
    }

    override suspend fun load(params: LoadParams<Key>): LoadResult<Key, valueOutT> {
        return when (val loadResult = originalSource.load(params)) {
            is LoadResult.Invalid -> {
                LoadResult.Invalid()
            }
            is LoadResult.Error   -> {
                LoadResult.Error(loadResult.throwable)
            }
            is LoadResult.Page    -> {
                if (loadResult.data.isEmpty() && params.key == null) {
                    LoadResult.Error(NoDataException())
                } else {
                    try {
                        LoadResult.Page(
                            data = loadResult.data.map(mapperDelegate),
                            prevKey = loadResult.prevKey,
                            nextKey = loadResult.nextKey,
                            itemsAfter = loadResult.itemsAfter,
                            itemsBefore = loadResult.itemsBefore,
                        )
                    } catch (e: Exception) {
                        LoadResult.Error(e)
                    }
                }
            }
        }
    }
}