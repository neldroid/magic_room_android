package com.lightningapps.magicroom.data.firebase

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.lightningapps.magicroom.data.helper.FirestoreResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

open class FirebaseDataSourceProvider constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    inline fun <reified T> retrieveQuery(reference: Query): Flow<FirestoreResult> = callbackFlow{
        val subscription = reference.addSnapshotListener { snapshot, error ->
            if (error == null) {
                if (snapshot != null) {
                    trySend(FirestoreResult.Success(snapshot.toObjects(T::class.java)))
                }
            } else {
                trySend(FirestoreResult.ErrorResult(error))
                cancel(error.message.toString())
            }
        }

        awaitClose { subscription.remove() }
    }
    inline fun <reified T> retrieveCollection(reference: CollectionReference): Flow<FirestoreResult> =
       retrieveQuery<T>(reference)

    inline fun <reified T> retrieveDocument(reference: DocumentReference): Flow<FirestoreResult> =
        callbackFlow {
            val subscription = reference.addSnapshotListener { snapshot, error ->
                if (error == null) {
                    if (snapshot != null) {
                        trySend(FirestoreResult.Success(snapshot.toObject<T>()))
                    }
                } else {
                    trySend(FirestoreResult.ErrorResult(error))
                    cancel(error.message.toString())
                }
            }

            awaitClose {
                subscription.remove()
            }
        }

    companion object {
        const val USERS = "users"
        const val ROOMS = "rooms"
        const val ROOM_OPEN = "open"
        const val SPIRIT_REALM = "spiritRealm"
        const val CARDS = "cards"
        const val REACTIONS = "reactions"
    }


}