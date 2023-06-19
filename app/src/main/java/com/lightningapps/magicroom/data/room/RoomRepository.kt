package com.lightningapps.magicroom.data.room

import com.lightningapps.magicroom.data.helper.FirestoreResult
import com.lightningapps.magicroom.data.room.source.ReactionFirestoreDataSource
import com.lightningapps.magicroom.data.room.source.RoomFirestoreDataSource
import com.lightningapps.magicroom.model.Reaction
import com.lightningapps.magicroom.model.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val roomFirestoreDataSource: RoomFirestoreDataSource,
    private val reactionFirestoreDataSource: ReactionFirestoreDataSource
) : IRoomRepository {

    override fun getAvailableRooms() : Flow<FirestoreResult> {
        roomFirestoreDataSource.getAvailableRooms().onEach {rooms ->
            rooms.forEach { room ->
                reactionFirestoreDataSource.getReactionsForRoom(room.id).collect {
                    room.reactions.addAll(it)
                }
            }
        }
    }


}