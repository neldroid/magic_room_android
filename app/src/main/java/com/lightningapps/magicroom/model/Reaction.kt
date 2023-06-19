package com.lightningapps.magicroom.model

data class Reaction(
    val emojiCode: String,
    val quantity: Int
){
    constructor(): this("",0)
}
