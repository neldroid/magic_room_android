package com.lightningapps.magicroom.data.room.source

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.lightningapps.magicroom.data.firebase.FirebaseHelper.REACTIONS
import com.lightningapps.magicroom.data.firebase.FirebaseHelper.ROOMS
import com.lightningapps.magicroom.data.helper.FirestoreResult
import com.lightningapps.magicroom.model.Reaction
import com.lightningapps.magicroom.model.Room
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ReactionFirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun getReactionsForRoom(roomId: String): Flow<List<Reaction>> = callbackFlow {
        var reactionsCollection: CollectionReference? = null
        try {
            reactionsCollection = firestore.collection(ROOMS).document(roomId).collection(REACTIONS)
        } catch (e: Throwable) {
            close(e)
        }

        val subscription = reactionsCollection?.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                try {
                    trySend(snapshot.toObjects(Reaction::class.java))
                } catch (e: Throwable) {
                    //TODO
                }
            }

        }

        awaitClose { subscription?.remove() }
    }


}