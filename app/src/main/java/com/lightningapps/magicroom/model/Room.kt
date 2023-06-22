package com.lightningapps.magicroom.model

import java.sql.Timestamp
import java.time.Instant
import java.util.Date

data class Room(
    val id: String,
    val title: String,
    val lastMessage: String,
    val reactions: MutableList<Reaction>,
    val capacity: Capacity,
    val backgroundColor: String,
    val closingTime: Date,
    val user: User
) {
    constructor() : this(
        "",
        "",
        "",
        mutableListOf(),
        Capacity(),
        "",
        Date.from(Instant.now()),
        User()
    )
}

data class Capacity(
    val total: Int,
    val current: Int
) {
    constructor() : this(0, 0)
}
