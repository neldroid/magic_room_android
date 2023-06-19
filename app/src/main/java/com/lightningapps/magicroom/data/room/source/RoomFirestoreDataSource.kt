package com.lightningapps.magicroom.data.room.source

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.lightningapps.magicroom.data.firebase.FirebaseHelper
import com.lightningapps.magicroom.data.firebase.FirebaseHelper.ROOMS
import com.lightningapps.magicroom.data.firebase.FirebaseHelper.USERS
import com.lightningapps.magicroom.data.helper.FirestoreResult
import com.lightningapps.magicroom.model.Room
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RoomFirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun getAvailableRooms(): Flow<List<Room>> = callbackFlow {
        var roomsCollection: CollectionReference? = null
        try {
            roomsCollection = firestore.collection(ROOMS)
        } catch (e: Throwable) {
            close(e)
        }

        val subscription = roomsCollection?.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                try {
//                    trySend(FirestoreResult.Success(snapshot.toObjects(Room::class.java)))
                    trySend(snapshot.toObjects(Room::class.java))
                } catch (e: Throwable) {
//                    trySend(FirestoreResult.ErrorResult(e))
                    // TODO
                }
            }
//            else {
//                trySend(FirestoreResult.ErrorResult(Throwable(FirestoreResult.ErrorResult.SNAPSHOT_NULL_ERROR)))
//            }

        }

        awaitClose { subscription?.remove() }
    }


}