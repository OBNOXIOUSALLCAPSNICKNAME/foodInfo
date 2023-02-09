package com.example.foodinfo.repository

import com.example.foodinfo.local.dto.LabelRecipeAttrDB
import com.example.foodinfo.local.dto.NutrientRecipeAttrDB
import com.example.foodinfo.local.dto.RecipeAttrsDB
import com.example.foodinfo.local.dto.SearchFilterDB
import com.example.foodinfo.repository.model.CategoryOfSearchFilterEditModel
import com.example.foodinfo.repository.model.NutrientOfSearchFilterEditModel
import com.example.foodinfo.repository.model.SearchFilterEditModel
import com.example.foodinfo.utils.ErrorMessages
import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


abstract class SearchFilterRepository : BaseRepository() {

    abstract fun getQueryByFilter(
        filterName: String = SearchFilterDB.DEFAULT_NAME,
        inputText: String = ""
    ): Flow<State<String>>

    abstract fun getQueryByLabel(labelID: Int): Flow<State<String>>


    abstract fun getCategoryEdit(
        filterName: String = SearchFilterDB.DEFAULT_NAME,
        categoryID: Int,
        attrs: List<LabelRecipeAttrDB>
    ): Flow<State<CategoryOfSearchFilterEditModel>>

    abstract fun getNutrientsEdit(
        filterName: String = SearchFilterDB.DEFAULT_NAME,
        attrs: List<NutrientRecipeAttrDB>
    ): Flow<State<List<NutrientOfSearchFilterEditModel>>>

    abstract fun getFilterEdit(
        filterName: String = SearchFilterDB.DEFAULT_NAME,
        attrs: RecipeAttrsDB
    ): Flow<State<SearchFilterEditModel>>


    abstract fun createFilter(
        filterName: String = SearchFilterDB.DEFAULT_NAME,
        attrs: RecipeAttrsDB
    )

    abstract fun resetFilter(filterName: String = SearchFilterDB.DEFAULT_NAME)

    abstract fun resetBasics(filterName: String = SearchFilterDB.DEFAULT_NAME)

    abstract fun resetNutrients(filterName: String = SearchFilterDB.DEFAULT_NAME)

    abstract fun resetCategory(
        filterName: String = SearchFilterDB.DEFAULT_NAME,
        categoryID: Int
    )


    abstract fun updateBasic(id: Int, minValue: Float, maxValue: Float)

    abstract fun updateNutrient(id: Int, minValue: Float, maxValue: Float)

    abstract fun updateLabel(id: Int, isSelected: Boolean)


    /**
     * Helper function that will wrap provided [dataFlow] from [getData]
     *
     * Main purpose is to verify data collected from [dataFlow] and if data is outdated - update data source
     * instead of emitting them and wait until next [dataFlow] emission.
     * So the first data that will comes to UI would be already updated one
     *
     * On each emission of [State.Success] from [dataFlow] this function will pass [State.Success.data]
     * to [verificationDelegate], emit result if it's not null, do nothing if null and emit [State.Error] if
     * an error occurred inside [verificationDelegate]
     *
     * NOTE that if [verificationDelegate] return null but this will not be followed by any changes inside
     * datasource, it will lead to infinite [State.Loading] due to [dataFlow] will not emit anything
     *
     * Use case:
     *
     *  ```
     *  fun getVerifiedData(): Flow<State<DataModel>> {
     *      return getVerified<DataLocal, DataModel>(
     *          verificationDelegate = { localData ->
     *              val result = verifyData(localData)
     *              if (result != null) {
     *                  // if data is outdated - update DB, return null and wait
     *                  // until next getData emission
     *                  dao.update(result)
     *                  null
     *              } else {
     *                  // if data is not outdated - map it to Model and return.
     *                  // getVerified will emit them and UI will receive actual data
     *                  mapToDataModel(result)
     *              }
     *          },
     *          dataFlow = getData(
     *              context = context,
     *              remoteDataProvider = { },
     *              localDataFlowProvider = { dao.observeData() },
     *              updateLocalDelegate = { },
     *              mapRemoteToLocalDelegate = { },
     *              mapLocalToModelDelegate = { localData ->
     *                  // do not map data here to be able to work
     *                  // with raw data inside verificationDelegate
     *                  localData
     *              }
     *          )
     *      )
     *  }
     *  ```
     */
    protected fun <inT, outT> getVerified(
        verificationDelegate: (inT) -> outT?,
        dataFlow: Flow<State<inT>>
    ): Flow<State<outT>> {
        return flow {
            emit(State.Loading())
            dataFlow.collect { state ->
                if (state is State.Success) {
                    try {
                        verificationDelegate(state.data)?.let { verified ->
                            emit(State.Success(verified))
                        }
                    } catch (e: Exception) {
                        emit(State.Error(ErrorMessages.UNKNOWN_ERROR, e))
                    }
                }
            }
        }
    }
}
