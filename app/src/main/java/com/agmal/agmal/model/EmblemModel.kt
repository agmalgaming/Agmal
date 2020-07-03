package com.agmal.agmal.model

import org.json.JSONArray

data class EmblemModel (
    val emblemID: String,
    val emblemName: String,
    val emblemIcon: String,
    val emblemRole: String,
    val emblemAttributes:JSONArray
)