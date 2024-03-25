package com.kaya.digitalmining.model

import java.io.Serializable

data class New(
    val content: String,
    val date: String,
    val image: String,
    val title: String
) : Serializable