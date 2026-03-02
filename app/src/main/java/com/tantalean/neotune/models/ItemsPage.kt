package com.tantalean.neotune.models

import com.tantalean.innertube.models.YTItem

data class ItemsPage(
    val items: List<YTItem>,
    val continuation: String?,
)
