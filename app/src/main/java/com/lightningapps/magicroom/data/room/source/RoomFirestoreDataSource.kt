package com.lightningapps.magicroom.data.room.source

import com.google.firebase.firestore.FirebaseFirestore
import com.lightningapps.magicroom.data.firebase.FirebaseDataSourceProvider
import com.lightningapps.magicroom.data.helper.FirestoreResult
import com.lightningapps.magicroom.model.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RoomFirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
): FirebaseDataSourceProvider() {

    fun getAvailableRooms(isAlive: Boolean): Flow<FirestoreResult> {
        val query = firestore.collection(ROOMS)
            .whereEqualTo(ROOM_OPEN, true)
            .whereEqualTo(SPIRIT_REALM, !isAlive)
            .limit(10)

        return retrieveQuery<Room>(query).flowOn(Dispatchers.IO)
    }

    fun getOpenSoonRooms(isAlive: Boolean): Flow<FirestoreResult> {
        val query = firestore.collection(ROOMS)
            .whereEqualTo(ROOM_OPEN, false)
            .whereEqualTo(SPIRIT_REALM, !isAlive)

        return retrieveQuery<Room>(query).flowOn(Dispatchers.IO)
    }
}