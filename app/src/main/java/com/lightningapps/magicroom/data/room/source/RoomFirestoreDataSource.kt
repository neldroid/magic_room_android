package com.lightningapps.magicroom.data.room.source

import com.google.firebase.firestore.FirebaseFirestore
import com.lightningapps.magicroom.data.firebase.FirebaseDataSourceProvider
import com.lightningapps.magicroom.model.Room
import javax.inject.Inject

class RoomFirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
): FirebaseDataSourceProvider() {

    fun getAvailableRooms() =
        retrieveCollection<Room>(firestore.collection(ROOMS))
}