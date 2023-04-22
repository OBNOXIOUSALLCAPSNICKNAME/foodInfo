package com.example.foodinfo.core.utils.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.foodinfo.core.utils.ApiResponse
import com.example.foodinfo.core.utils.edamam.EdamamInfo
import com.example.foodinfo.data.remote.NetworkResponse
import com.example.foodinfo.data.remote.model.RecipeHitNetwork
import com.example.foodinfo.data.remote.model.RecipePageNetwork
import kotlin.math.ceil


@OptIn(ExperimentalPagingApi::class)
class EdamamRemoteMediator<localInT : Any, localOutT : Any, Key : Any>(
    private val remoteDataProvider: suspend (String) -> ApiResponse<RecipePageNetwork>,
    private val mapToLocalDelegate: suspend (List<RecipeHitNetwork>) -> List<localInT>,
    private val saveRemoteDelegate: suspend (List<localInT>) -> Unit,
) : RemoteMediator<Key, localOutT>() {

    private var nextPageQuery: String? = ""
    private var loadPageCount: Int = 0

    override suspend fun load(loadType: LoadType, state: PagingState<Key, localOutT>): MediatorResult {
        when (loadType) {
            LoadType.REFRESH -> {
                nextPageQuery = ""
                loadPageCount = state.config.initialLoadSize.toPageCount()
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND  -> {
                if (nextPageQuery == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                loadPageCount = state.config.pageSize.toPageCount()
            }
        }
        try {
            val data = mutableListOf<RecipeHitNetwork>()
            while (loadPageCount != 0 && nextPageQuery != null) {
                when (val response = remoteDataProvider(nextPageQuery!!)) {
                    is NetworkResponse.Success -> {
                        data += response.result.hits
                        nextPageQuery = response.result.links.next?.href
                        loadPageCount -= 1
                    }
                    else                       -> {
                        return MediatorResult.Error((response as NetworkResponse.Error).throwable)
                    }
                }
            }
            saveRemoteDelegate(mapToLocalDelegate(data))
            return MediatorResult.Success(endOfPaginationReached = nextPageQuery == null)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private fun Int.toPageCount() = ceil(this.toDouble() / EdamamInfo.PAGE_SIZE).toInt()
}