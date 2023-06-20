package com.lightningapps.magicroom.data.room.source

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.lightningapps.magicroom.data.firebase.FirebaseHelper.ROOMS
import com.lightningapps.magicroom.data.helper.FirestoreResult
import com.lightningapps.magicroom.model.Reaction
import com.lightningapps.magicroom.model.Room
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RoomFirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun getAvailableRooms(): Flow<FirestoreResult> = callbackFlow {
        val roomsCollection: CollectionReference = firestore.collection(ROOMS)
        val subscription = roomsCollection.addSnapshotListener { snapshot, error ->
            if (error == null){
                if (snapshot != null) {
                    trySend(FirestoreResult.Success(snapshot.toObjects(Room::class.java)))
                }
            }
            else {
                trySend(FirestoreResult.ErrorResult(error))
                cancel(error.message.toString())
            }
        }

        awaitClose { subscription.remove() }

    }
}