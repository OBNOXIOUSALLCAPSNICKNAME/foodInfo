package com.example.foodinfo.utils.view_model

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.foodinfo.utils.CoroutineLauncher
import com.example.foodinfo.utils.LaunchStrategy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


class SelectManager<Model : Any, Key>(
    scope: CoroutineScope,
    dataSource: Flow<PagingData<Model>>,
    totalCount: Flow<Int>,
    private val getItemKey: (Model) -> Key,
    private val getAllKeys: suspend () -> Set<Key>
) {

    private val getAllKeysCoroutine = CoroutineLauncher(scope, Dispatchers.IO, LaunchStrategy.CANCEL)

    private val selectedItems: HashSet<Key> = hashSetOf()

    private val selectedCount = MutableStateFlow(0)

    private val editModeState = MutableStateFlow(false)

    private var shouldSelect = false


    val modeState = combine(totalCount, selectedCount, editModeState, ::toModeState)

    val data = combine(dataSource.cachedIn(scope), selectedCount, ::toSelectable)
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
        if (getItemKey(item) in selectedItems) {
            selectedItems.remove(getItemKey(item))
        } else {
            selectedItems.add(getItemKey(item))
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
    private fun toSelectable(pagingData: PagingData<Model>, count: Int): PagingData<Selectable<Model>> {
        return pagingData.map { item ->
            Selectable(
                model = item,
                isSelected = getItemKey(item) in selectedItems
            )
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