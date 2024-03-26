package com.kaya.digitalmining.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class New(
    val content: String,
    val date: String,
    val image: String,
    val title: String
): Parcelable