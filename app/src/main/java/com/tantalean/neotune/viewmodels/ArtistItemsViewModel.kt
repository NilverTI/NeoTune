package com.tantalean.neotune.viewmodels

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tantalean.innertube.YouTube
import com.tantalean.innertube.models.BrowseEndpoint
import com.tantalean.innertube.models.filterExplicit
import com.tantalean.neotune.constants.HideExplicitKey
import com.tantalean.neotune.models.ItemsPage
import com.tantalean.neotune.utils.dataStore
import com.tantalean.neotune.utils.get
import com.tantalean.neotune.utils.reportException
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistItemsViewModel
@Inject
constructor(
    @ApplicationContext val context: Context,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val browseId = savedStateHandle.get<String>("browseId")!!
    private val params = savedStateHandle.get<String>("params")

    val title = MutableStateFlow("")
    val itemsPage = MutableStateFlow<ItemsPage?>(null)

    init {
        viewModelScope.launch {
            YouTube
                .artistItems(
                    BrowseEndpoint(
                        browseId = browseId,
                        params = params,
                    ),
                ).onSuccess { artistItemsPage ->
                    title.value = artistItemsPage.title
                    itemsPage.value =
                        ItemsPage(
                            items = artistItemsPage.items.distinctBy { it.id },
                            continuation = artistItemsPage.continuation,
                        )
                }.onFailure {
                    reportException(it)
                }
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            val oldItemsPage = itemsPage.value ?: return@launch
            val continuation = oldItemsPage.continuation ?: return@launch
            YouTube
                .artistItemsContinuation(continuation)
                .onSuccess { artistItemsContinuationPage ->
                    itemsPage.update {
                        ItemsPage(
                            items =
                                (oldItemsPage.items + artistItemsContinuationPage.items)
                                    .distinctBy { it.id }
                                    .filterExplicit(context.dataStore.get(HideExplicitKey, false)),
                            continuation = artistItemsContinuationPage.continuation,
                        )
                    }
                }.onFailure {
                    reportException(it)
                }
        }
    }
}
