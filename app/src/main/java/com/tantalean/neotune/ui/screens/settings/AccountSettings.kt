package com.tantalean.neotune.ui.screens.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.tantalean.innertube.YouTube
import com.tantalean.innertube.utils.parseCookieString
import com.tantalean.neotune.App.Companion.forgetAccount
import com.tantalean.neotune.R
import com.tantalean.neotune.constants.AccountChannelHandleKey
import com.tantalean.neotune.constants.AccountEmailKey
import com.tantalean.neotune.constants.AccountNameKey
import com.tantalean.neotune.constants.DataSyncIdKey
import com.tantalean.neotune.constants.InnerTubeCookieKey
import com.tantalean.neotune.constants.UseLoginForBrowse
import com.tantalean.neotune.constants.VisitorDataKey
import com.tantalean.neotune.constants.YtmSyncKey
import com.tantalean.neotune.ui.component.InfoLabel
import com.tantalean.neotune.ui.component.PreferenceEntry
import com.tantalean.neotune.ui.component.SettingsGeneralCategory
import com.tantalean.neotune.ui.component.SettingsPage
import com.tantalean.neotune.ui.component.SwitchPreference
import com.tantalean.neotune.ui.component.TextFieldDialog
import com.tantalean.neotune.utils.rememberPreference

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSettings(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val context = LocalContext.current

    val (accountName, onAccountNameChange) = rememberPreference(AccountNameKey, "")
    val (accountEmail, onAccountEmailChange) = rememberPreference(AccountEmailKey, "")
    val (accountChannelHandle, onAccountChannelHandleChange) = rememberPreference(
        AccountChannelHandleKey,
        ""
    )
    val (innerTubeCookie, onInnerTubeCookieChange) = rememberPreference(InnerTubeCookieKey, "")
    val (visitorData, onVisitorDataChange) = rememberPreference(VisitorDataKey, "")
    val (dataSyncId, onDataSyncIdChange) = rememberPreference(DataSyncIdKey, "")

    val isLoggedIn = remember(innerTubeCookie) {
        innerTubeCookie.isNotEmpty() && "SAPISID" in parseCookieString(innerTubeCookie)
    }

    // Function to securely obtain the account name
    val getAccountDisplayName =
        remember(accountName, accountEmail, accountChannelHandle, isLoggedIn) {
            when {
                !isLoggedIn -> ""
                accountName.isNotBlank() -> accountName
                accountEmail.isNotBlank() -> accountEmail.substringBefore("@")
                accountChannelHandle.isNotBlank() -> accountChannelHandle
                else -> "No username" // Fallback to prevent crashes
            }
        }

    // Function to securely obtain the account description
    val getAccountDescription = remember(accountEmail, accountChannelHandle, isLoggedIn) {
        when {
            !isLoggedIn -> null
            accountEmail.isNotBlank() -> accountEmail
            accountChannelHandle.isNotBlank() -> accountChannelHandle
            else -> null
        }
    }

    val (useLoginForBrowse, onUseLoginForBrowseChange) = rememberPreference(UseLoginForBrowse, true)
    val (ytmSync, onYtmSyncChange) = rememberPreference(YtmSyncKey, defaultValue = true)

    var showToken: Boolean by remember {
        mutableStateOf(false)
    }
    var showTokenEditor by remember {
        mutableStateOf(false)
    }

    SettingsPage(
        title = stringResource(R.string.account),
        navController = navController,
        scrollBehavior = scrollBehavior
    ) {
        SettingsGeneralCategory(
            title = stringResource(R.string.google),
            items = listOf(
                {PreferenceEntry(
                    title = {
                        Text(
                            if (isLoggedIn) {
                                getAccountDisplayName.takeIf { it.isNotBlank() }
                                    ?: stringResource(R.string.login)
                            } else {
                                stringResource(R.string.login)
                            }
                        )
                    },
                    description = if (isLoggedIn) getAccountDescription else null,
                    icon = { Icon(painterResource(R.drawable.login), null) },
                    trailingContent = {
                        if (isLoggedIn) {
                            OutlinedButton(onClick = {
                                // Clear all account data
                                onInnerTubeCookieChange("")
                                onAccountNameChange("")
                                onAccountEmailChange("")
                                onAccountChannelHandleChange("")
                                onVisitorDataChange("")
                                onDataSyncIdChange("")
                                forgetAccount(context)
                            }
                            ) {
                                Text(stringResource(R.string.logout))
                            }
                        }
                    },
                    onClick = { if (!isLoggedIn) navController.navigate("login") }
                )},

                {if (showTokenEditor) {
                    val text =
                        "***INNERTUBE COOKIE*** =${innerTubeCookie}\n\n***VISITOR DATA*** =${visitorData}\n\n***DATASYNC ID*** =${dataSyncId}\n\n***ACCOUNT NAME*** =${accountName}\n\n***ACCOUNT EMAIL*** =${accountEmail}\n\n***ACCOUNT CHANNEL HANDLE*** =${accountChannelHandle}"
                    TextFieldDialog(
                        modifier = Modifier,
                        initialTextFieldValue = TextFieldValue(text),
                        onDone = { data ->
                            data.split("\n").forEach {
                                when {
                                    it.startsWith("***INNERTUBE COOKIE*** =") -> {
                                        val cookie =
                                            it.substringAfter("***INNERTUBE COOKIE*** =").trim()
                                        onInnerTubeCookieChange(cookie)
                                    }

                                    it.startsWith("***VISITOR DATA*** =") -> {
                                        val visitorDataValue =
                                            it.substringAfter("***VISITOR DATA*** =").trim()
                                        onVisitorDataChange(visitorDataValue)
                                    }

                                    it.startsWith("***DATASYNC ID*** =") -> {
                                        val dataSyncIdValue =
                                            it.substringAfter("***DATASYNC ID*** =").trim()
                                        onDataSyncIdChange(dataSyncIdValue)
                                    }

                                    it.startsWith("***ACCOUNT NAME*** =") -> {
                                        val name = it.substringAfter("***ACCOUNT NAME*** =").trim()
                                        onAccountNameChange(name)
                                    }

                                    it.startsWith("***ACCOUNT EMAIL*** =") -> {
                                        val email = it.substringAfter("***ACCOUNT EMAIL*** =").trim()
                                        onAccountEmailChange(email)
                                    }

                                    it.startsWith("***ACCOUNT CHANNEL HANDLE*** =") -> {
                                        val handle =
                                            it.substringAfter("***ACCOUNT CHANNEL HANDLE*** =").trim()
                                        onAccountChannelHandleChange(handle)
                                    }
                                }
                            }
                        },
                        onDismiss = { showTokenEditor = false },
                        singleLine = false,
                        maxLines = 20,
                        isInputValid = { input ->
                            input.isNotEmpty() &&
                                    try {
                                        val cookieLine = input.lines()
                                            .find { it.startsWith("***INNERTUBE COOKIE*** =") }
                                        if (cookieLine != null) {
                                            val cookie =
                                                cookieLine.substringAfter("***INNERTUBE COOKIE*** =")
                                                    .trim()
                                            cookie.isEmpty() || "SAPISID" in parseCookieString(cookie)
                                        } else {
                                            false
                                        }
                                    } catch (e: Exception) {
                                        false
                                    }
                        },
                        extraContent = {
                            InfoLabel(text = stringResource(R.string.token_adv_login_description))
                        }
                    )
                }},

                {PreferenceEntry(
                    title = {
                        if (!isLoggedIn) {
                            Text(stringResource(R.string.advanced_login))
                        } else {
                            if (showToken) {
                                Text(stringResource(R.string.token_shown))
                            } else {
                                Text(stringResource(R.string.token_hidden))
                            }
                        }
                    },
                    icon = { Icon(painterResource(R.drawable.token), null) },
                    onClick = {
                        if (!isLoggedIn) {
                            showTokenEditor = true
                        } else {
                            if (!showToken) {
                                showToken = true
                            } else {
                                showTokenEditor = true
                            }
                        }
                    },
                )},

                {if (isLoggedIn) {
                    SwitchPreference(
                        title = { Text(stringResource(R.string.use_login_for_browse)) },
                        description = stringResource(R.string.use_login_for_browse_desc),
                        icon = { Icon(painterResource(R.drawable.person), null) },
                        checked = useLoginForBrowse,
                        onCheckedChange = {
                            YouTube.useLoginForBrowse = it
                            onUseLoginForBrowseChange(it)
                        }
                    )
                }},

                {if (isLoggedIn) {
                    SwitchPreference(
                        title = { Text(stringResource(R.string.ytm_sync)) },
                        icon = { Icon(painterResource(R.drawable.cached), null) },
                        checked = ytmSync,
                        onCheckedChange = onYtmSyncChange,
                        isEnabled = isLoggedIn
                    )
                }},

                )
        )
    }
}