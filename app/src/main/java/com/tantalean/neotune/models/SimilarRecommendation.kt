package com.tantalean.neotune.models

import com.tantalean.innertube.models.YTItem
import com.tantalean.neotune.db.entities.LocalItem

data class SimilarRecommendation(
    val title: LocalItem,
    val items: List<YTItem>,
)
