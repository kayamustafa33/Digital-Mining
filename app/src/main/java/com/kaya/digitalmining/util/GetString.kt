package com.kaya.digitalmining.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun getString(@StringRes id: Int): String {
    return LocalContext.current.resources.getString(id)
}