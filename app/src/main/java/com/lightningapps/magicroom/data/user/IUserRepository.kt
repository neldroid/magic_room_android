package com.lightningapps.magicroom.data.user

import com.lightningapps.magicroom.data.helper.FirestoreResult
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun getBasicUserInformation(): Flow<FirestoreResult>

}