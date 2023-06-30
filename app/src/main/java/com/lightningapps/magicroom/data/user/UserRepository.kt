package com.lightningapps.magicroom.data.user

import com.lightningapps.magicroom.data.helper.FirestoreResult
import com.lightningapps.magicroom.data.user.source.UserFirestoreDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userFirestoreDataSource: UserFirestoreDataSource
): IUserRepository {

    override fun getBasicUserInformation(): Flow<FirestoreResult> =
        userFirestoreDataSource.getBasicInformation(getStoredUserId())

    private fun getStoredUserId(): String = "testUserId"
}