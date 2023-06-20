package com.lightningapps.magicroom.data.room.source

import com.google.firebase.firestore.FirebaseFirestore
import com.lightningapps.magicroom.data.firebase.FirebaseDataSourceProvider
import com.lightningapps.magicroom.data.helper.FirestoreResult
import com.lightningapps.magicroom.model.Reaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReactionFirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
): FirebaseDataSourceProvider() {

    fun getReactionsForRoom(roomId: String): Flow<FirestoreResult> =
        retrieveCollection<Reaction>(firestore.collection(ROOMS).document(roomId).collection(REACTIONS))
}