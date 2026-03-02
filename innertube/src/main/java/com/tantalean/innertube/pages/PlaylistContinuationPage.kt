package com.tantalean.innertube.pages

import com.tantalean.innertube.models.SongItem

data class PlaylistContinuationPage(
    val songs: List<SongItem>,
    val continuation: String?,
)
