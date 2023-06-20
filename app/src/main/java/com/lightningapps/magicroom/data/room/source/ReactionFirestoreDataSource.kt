package com.lightningapps.magicroom.data.room.source

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.lightningapps.magicroom.data.firebase.FirebaseHelper.REACTIONS
import com.lightningapps.magicroom.data.firebase.FirebaseHelper.ROOMS
import com.lightningapps.magicroom.data.helper.FirestoreResult
import com.lightningapps.magicroom.model.Reaction
import com.lightningapps.magicroom.model.Room
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReactionFirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun getReactionsForRoom(roomId: String): Flow<FirestoreResult> = callbackFlow {

        val reactionCollection: CollectionReference =
            firestore.collection(ROOMS).document(roomId).collection(REACTIONS)
        
        val subscription = reactionCollection.addSnapshotListener { snapshot, error ->
            if (error == null) {
                if (snapshot != null) {
                    trySend(FirestoreResult.Success(snapshot.toObjects(Reaction::class.java)))
                }
            } else {
                trySend(FirestoreResult.ErrorResult(error))
                cancel(error.message.toString())
            }
        }

        awaitClose { subscription.remove() }
    }
}