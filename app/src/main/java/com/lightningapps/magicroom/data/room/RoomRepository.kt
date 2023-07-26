package com.lightningapps.magicroom.data.room

import com.lightningapps.magicroom.data.helper.FirestoreResult
import com.lightningapps.magicroom.data.room.source.ReactionFirestoreDataSource
import com.lightningapps.magicroom.data.room.source.RoomFirestoreDataSource
import com.lightningapps.magicroom.model.LocalSavedRoom
import com.lightningapps.magicroom.model.Reaction
import com.lightningapps.magicroom.model.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val roomFirestoreDataSource: RoomFirestoreDataSource,
    private val reactionFirestoreDataSource: ReactionFirestoreDataSource
) : IRoomRepository {

    override fun getAvailableRooms(isAlive: Boolean): Flow<FirestoreResult> =
        (roomFirestoreDataSource.getAvailableRooms(isAlive)).onEach { firestoreResult ->
            when (firestoreResult){
                is FirestoreResult.Success<*> -> {
                    val rooms = firestoreResult.result as List<Room>
                    rooms.forEach { room ->
                        (reactionFirestoreDataSource.getReactionsForRoom(room.id)).first{reactionResult ->
                            room.reactions.addAll((reactionResult as FirestoreResult.Success<List<*>>).result as List<Reaction>)
                        }
                    }
                }
            }
        }

    override fun getOpenSoonRooms(isAlive: Boolean): Flow<FirestoreResult> =
        roomFirestoreDataSource.getOpenSoonRooms(isAlive)

    override fun getRoomWaitingInformation(roomId: String): Flow<FirestoreResult> {
        TODO("Not yet implemented")
    }


}