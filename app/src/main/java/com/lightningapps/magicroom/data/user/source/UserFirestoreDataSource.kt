package com.lightningapps.magicroom.data.user.source

import com.google.firebase.firestore.FirebaseFirestore
import com.lightningapps.magicroom.data.firebase.FirebaseDataSourceProvider
import com.lightningapps.magicroom.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserFirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirebaseDataSourceProvider() {

    fun getBasicInformation(userId: String) = retrieveDocument<User>(
        firestore.collection(USERS).document(userId)
    ).flowOn(Dispatchers.IO)

}