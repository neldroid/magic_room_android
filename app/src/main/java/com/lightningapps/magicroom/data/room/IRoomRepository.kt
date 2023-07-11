package com.lightningapps.magicroom.data.room

import com.lightningapps.magicroom.data.helper.FirestoreResult
import com.lightningapps.magicroom.model.Room
import kotlinx.coroutines.flow.Flow

interface IRoomRepository {

    fun getAvailableRooms(isAlive: Boolean): Flow<FirestoreResult>

    fun getOpenSoonRooms(isAlive: Boolean): Flow<FirestoreResult>

}