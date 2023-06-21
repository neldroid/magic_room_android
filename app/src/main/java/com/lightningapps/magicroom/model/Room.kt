package com.lightningapps.magicroom.model

data class Room(
    val id: String,
    val title: String,
    val lastMessage: String,
    val reactions: MutableList<Reaction>,
    val capacity: Capacity,
    val backgroundColor: String
) {
    constructor() : this(
        "",
        "",
        "",
        mutableListOf(),
        Capacity(0, 0),
        ""
    )
}

data class Capacity(
    val total: Int,
    val current: Int
) {
    constructor() : this(0, 0)
}
