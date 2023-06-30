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

    fun getAvailableRooms(): Flow<FirestoreResult> {
        val query = firestore.collection(ROOMS)
            .whereEqualTo(ROOM_OPEN, true)
            .limit(10)

        return retrieveQuery<Room>(query).flowOn(Dispatchers.IO)
    }

    fun getOpenSoonRooms(): Flow<FirestoreResult> {
        val query = firestore.collection(ROOMS)
            .whereEqualTo(ROOM_OPEN, false)

        return retrieveQuery<Room>(query).flowOn(Dispatchers.IO)
    }
}