package com.lightningapps.magicroom.model

data class Room(
    val id: String,
    val title: String,
    val lastMessage: String,
    val reactions: MutableList<Reaction>,
    val capacity: Capacity
) {
    constructor() : this(
        "",
        "",
        "",
        mutableListOf(),
        Capacity(0, 0, 0)
    )
}

data class Capacity(
    val total: Int,
    val current: Int,
    val remaining: Int
) {
    constructor() : this(0, 0, 0)
}
