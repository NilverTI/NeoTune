package com.tantalean.innertube.pages

import com.tantalean.innertube.models.YTItem

data class LibraryContinuationPage(
    val items: List<YTItem>,
    val continuation: String?,
)
