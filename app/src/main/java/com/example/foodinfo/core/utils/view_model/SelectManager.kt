package com.example.foodinfo.core.utils.view_model

import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.foodinfo.core.utils.CoroutineLauncher
import com.example.foodinfo.core.utils.LaunchStrategy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


/**
 * When [PagingDataAdapter.submitData] receives [PagingData] with items that have the same reference as items
 * from previous [PagingData], [PagingDataAdapter] will ignores them and DiffUtil will not be called. To
 * make sure that [PagingDataAdapter] will not ignore selected items, [SelectManager] will copy them using
 * [copyItem].
 *
 * @param scope [CoroutineScope] that will be used to apply [cachedIn] for provided dataSource.
 * @param dataSource [Flow] of [PagingData] to observe.
 * @param totalCount [Flow] of total number of items in data source.
 * @param copyItem Function that can return a deep copy of provided object.
 * @param getAllKeys Function that provide [Set] of keys for all items in data source.
 */
class SelectManager<Key, Model : Selectable<Key>>(
    scope: CoroutineScope,
    dataSource: Flow<PagingData<Model>>,
    totalCount: Flow<Int>,
    private val copyItem: (Model) -> Model,
    private val getAllKeys: suspend () -> Set<Key>
) {

    private val getAllKeysCoroutine = CoroutineLauncher(scope, Dispatchers.IO, LaunchStrategy.CANCEL)

    private val selectedItems: HashSet<Key> = hashSetOf()

    private val selectedCount = MutableStateFlow(0)

    private val editModeState = MutableStateFlow(false)

    private var allSelected = false


    /**
     * [Flow] of [SelectModeState] that represents common information about selected/total items count and
     * edit mode state.
     *
     * Emits new [SelectModeState] each time any of it's fields changes.
     */
    val modeState = combine(totalCount, selectedCount, editModeState, ::toModeState)

    /**
     * Emits new [PagingData] into [data] each call of [toggleAll] or [toggleItem].
     */
    val data = combine(dataSource.cachedIn(scope), selectedCount, ::setSelected)
        .stateIn(scope, SharingStarted.WhileSubscribed(), PagingData.empty())

    /**
     * Enters or exits selection mode.
     * Setting this property to **false** will automatically unselect all items.
     */
    var isSelectMode: Boolean
        get() = editModeState.value
        set(value) {
            editModeState.value = value
            if (!editModeState.value) {
                unselectAll()
            }
        }


    fun toggleItem(item: Model) {
        if (item.ID in selectedItems) {
            selectedItems.remove(item.ID)
        } else {
            selectedItems.add(item.ID)
        }
        selectedCount.value = selectedItems.size
    }


    fun toggleAll() {
        if (allSelected) unselectAll() else selectAll()
    }

    fun getSelected(): List<Key> {
        return selectedItems.toList()
    }


    private fun selectAll() {
        selectedItems.clear()
        getAllKeysCoroutine.launch {
            selectedItems.addAll(getAllKeys())
            selectedCount.value = selectedItems.size
        }
    }

    private fun unselectAll() {
        selectedItems.clear()
        selectedCount.value = selectedItems.size
    }

    @Suppress("UNUSED_PARAMETER")
    private fun setSelected(pagingData: PagingData<Model>, count: Int): PagingData<Model> {
        return pagingData.map { item ->
            item.isSelected = item.ID in selectedItems
            copyItem(item)
        }
    }

    private fun toModeState(total: Int, selected: Int, isSelectedMode: Boolean): SelectModeState {
        allSelected = total == selected
        return SelectModeState(
            total = total,
            selected = selected,
            isSelectedMode = isSelectedMode,
        )
    }
}