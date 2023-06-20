package com.lightningapps.magicroom.data.firebase

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object FirebaseHelper {

    const val USERS = "users"
    const val ROOMS = "rooms"
    const val CARDS = "cards"
    const val REACTIONS = "reactions"


}