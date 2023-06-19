package com.lightningapps.magicroom.data.room

import com.lightningapps.magicroom.data.helper.FirestoreResult
import kotlinx.coroutines.flow.Flow

interface IRoomRepository {

    fun getAvailableRooms(): Flow<FirestoreResult>

}