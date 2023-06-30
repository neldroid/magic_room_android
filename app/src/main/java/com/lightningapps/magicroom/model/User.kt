package com.lightningapps.magicroom.model

import androidx.compose.ui.text.TextStyle


data class User(
    val id: String,
    val nickname: String,
    val gems: Int,
    val life: Int,
    val messageConfiguration: MessageConfiguration
){
    constructor(): this(
        "",
        "",
        0,
        0,
        MessageConfiguration()
    )
}

data class MessageConfiguration(
    val characters: Int,
    val highlight: Boolean,
    val nickname: String,
    val size: Int,
    val waitingTime: Int
){
    constructor(): this(
        0,
        false,
        "",
        0,
        0
    )
}
