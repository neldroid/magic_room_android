package com.lightningapps.magicroom.model


data class User(
    val id: String,
    val nickname: String
){
    constructor(): this(
        "",
        ""
    )
}
