package com.tantalean.neotune.ui.screens.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.tantalean.neotune.R
import com.tantalean.neotune.constants.AudioNormalizationKey
import com.tantalean.neotune.constants.AudioQuality
import com.tantalean.neotune.constants.AudioQualityKey
import com.tantalean.neotune.constants.AutoLoadMoreKey
import com.tantalean.neotune.constants.AutoSkipNextOnErrorKey
import com.tantalean.neotune.constants.PersistentQueueKey
import com.tantalean.neotune.constants.SimilarContent
import com.tantalean.neotune.constants.SkipSilenceKey
import com.tantalean.neotune.constants.StopMusicOnTaskClearKey
import com.tantalean.neotune.ui.component.EnumListPreference
import com.tantalean.neotune.ui.component.SettingsGeneralCategory
import com.tantalean.neotune.ui.component.SettingsPage
import com.tantalean.neotune.ui.component.SwitchPreference
import com.tantalean.neotune.utils.rememberEnumPreference
import com.tantalean.neotune.utils.rememberPreference

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerSettings(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val (audioQuality, onAudioQualityChange) = rememberEnumPreference(
        AudioQualityKey,
        defaultValue = AudioQuality.AUTO
    )
    val (persistentQueue, onPersistentQueueChange) = rememberPreference(
        PersistentQueueKey,
        defaultValue = true
    )
    val (skipSilence, onSkipSilenceChange) = rememberPreference(
        SkipSilenceKey,
        defaultValue = false
    )
    val (audioNormalization, onAudioNormalizationChange) = rememberPreference(
        AudioNormalizationKey,
        defaultValue = true
    )
    val (autoLoadMore, onAutoLoadMoreChange) = rememberPreference(
        AutoLoadMoreKey,
        defaultValue = true
    )
    val (similarContentEnabled, similarContentEnabledChange) = rememberPreference(
        key = SimilarContent,
        defaultValue = true
    )
    val (autoSkipNextOnError, onAutoSkipNextOnErrorChange) = rememberPreference(
        AutoSkipNextOnErrorKey,
        defaultValue = false
    )
    val (stopMusicOnTaskClear, onStopMusicOnTaskClearChange) = rememberPreference(
        StopMusicOnTaskClearKey,
        defaultValue = false
    )

    SettingsPage(
        title = stringResource(R.string.player_and_audio),
        navController = navController,
        scrollBehavior = scrollBehavior
    ) {
        SettingsGeneralCategory(
            title = stringResource(R.string.player),
            items = listOf(
                {EnumListPreference(
                    title = { Text(stringResource(R.string.audio_quality)) },
                    icon = { Icon(painterResource(R.drawable.graphic_eq), null) },
                    selectedValue = audioQuality,
                    onValueSelected = onAudioQualityChange,
                    valueText = {
                        when (it) {
                            AudioQuality.AUTO -> stringResource(R.string.audio_quality_auto)
                            AudioQuality.HIGH -> stringResource(R.string.audio_quality_high)
                            AudioQuality.LOW -> stringResource(R.string.audio_quality_low)
                        }
                    }
                )},

                {SwitchPreference(
                    title = { Text(stringResource(R.string.skip_silence)) },
                    icon = { Icon(painterResource(R.drawable.fast_forward), null) },
                    checked = skipSilence,
                    onCheckedChange = onSkipSilenceChange
                )},

                {SwitchPreference(
                    title = { Text(stringResource(R.string.audio_normalization)) },
                    icon = { Icon(painterResource(R.drawable.volume_up), null) },
                    checked = audioNormalization,
                    onCheckedChange = onAudioNormalizationChange
                )},
            )
        )

        SettingsGeneralCategory(
            title = stringResource(R.string.queue),
            items = listOf(
                {SwitchPreference(
                    title = { Text(stringResource(R.string.persistent_queue)) },
                    description = stringResource(R.string.persistent_queue_desc),
                    icon = { Icon(painterResource(R.drawable.queue_music), null) },
                    checked = persistentQueue,
                    onCheckedChange = onPersistentQueueChange
                )},

                {SwitchPreference(
                    title = { Text(stringResource(R.string.auto_load_more)) },
                    description = stringResource(R.string.auto_load_more_desc),
                    icon = { Icon(painterResource(R.drawable.playlist_add), null) },
                    checked = autoLoadMore,
                    onCheckedChange = onAutoLoadMoreChange
                )},

                {SwitchPreference(
                    title = { Text(stringResource(R.string.enable_similar_content)) },
                    description = stringResource(R.string.similar_content_desc),
                    icon = { Icon(painterResource(R.drawable.similar), null) },
                    checked = similarContentEnabled,
                    onCheckedChange = similarContentEnabledChange,
                )},

                {SwitchPreference(
                    title = { Text(stringResource(R.string.auto_skip_next_on_error)) },
                    description = stringResource(R.string.auto_skip_next_on_error_desc),
                    icon = { Icon(painterResource(R.drawable.skip_next), null) },
                    checked = autoSkipNextOnError,
                    onCheckedChange = onAutoSkipNextOnErrorChange
                )},
            )
        )

        SettingsGeneralCategory(
            title = stringResource(R.string.misc),
            items = listOf(
                {SwitchPreference(
                    title = { Text(stringResource(R.string.stop_music_on_task_clear)) },
                    icon = { Icon(painterResource(R.drawable.clear_all), null) },
                    checked = stopMusicOnTaskClear,
                    onCheckedChange = onStopMusicOnTaskClearChange
                )},
            )
        )
    }
}