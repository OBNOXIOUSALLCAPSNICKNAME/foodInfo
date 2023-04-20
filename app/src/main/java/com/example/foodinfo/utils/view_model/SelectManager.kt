package com.example.foodinfo.utils.view_model

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.foodinfo.utils.CoroutineLauncher
import com.example.foodinfo.utils.LaunchStrategy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


class SelectManager<Key, Model : Selectable<Key>>(
    scope: CoroutineScope,
    dataSource: Flow<PagingData<Model>>,
    totalCount: Flow<Int>,
    private val getAllKeys: suspend () -> Set<Key>
) {

    private val getAllKeysCoroutine = CoroutineLauncher(scope, Dispatchers.IO, LaunchStrategy.CANCEL)

    private val selectedItems: HashSet<Key> = hashSetOf()

    private val selectedCount = MutableStateFlow(0)

    private val editModeState = MutableStateFlow(false)

    private var shouldSelect = false


    val modeState = combine(totalCount, selectedCount, editModeState, ::toModeState)

    val data = combine(dataSource.cachedIn(scope), selectedCount, ::setSelected)
        .stateIn(scope, SharingStarted.Lazily, PagingData.empty())

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
        if (shouldSelect) selectAll() else unselectAll()
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
            item
        }
    }

    private fun toModeState(total: Int, selected: Int, isSelectedMode: Boolean): SelectModeState {
        shouldSelect = total != selected
        return SelectModeState(
            total = total,
            selected = selected,
            isSelectedMode = isSelectedMode,
        )
    }
}