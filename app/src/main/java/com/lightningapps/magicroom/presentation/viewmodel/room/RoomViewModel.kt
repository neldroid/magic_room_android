package com.lightningapps.magicroom.presentation.viewmodel.room

import androidx.lifecycle.ViewModel
import com.lightningapps.magicroom.data.helper.FirestoreResult
import com.lightningapps.magicroom.data.room.IRoomRepository
import com.lightningapps.magicroom.model.LocalSavedRoom
import com.lightningapps.magicroom.model.Room
import com.lightningapps.magicroom.presentation.viewmodel.helper.UIResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class RoomViewModel @Inject constructor(
    private val roomRepository: IRoomRepository
):ViewModel() {

    private val mutableAvailableRoomsStateFlow: MutableStateFlow<UIResult> =
        MutableStateFlow(UIResult.Loading)
    val availableRoomsStateFlow: StateFlow<UIResult> = mutableAvailableRoomsStateFlow


    private val mutableOpenSoonRoomsStateFlow: MutableStateFlow<UIResult> =
        MutableStateFlow(UIResult.Loading)
    val openSoonRoomsStateFlow: StateFlow<UIResult> = mutableOpenSoonRoomsStateFlow

    suspend fun fetchOpenSoonRooms() {
        roomRepository.getOpenSoonRooms().collect{
                repositoryResult ->
            when (repositoryResult) {
                is FirestoreResult.ErrorResult -> mutableOpenSoonRoomsStateFlow.value =
                    UIResult.Error(repositoryResult.exception)

                is FirestoreResult.Success<*> -> {
                    val rooms = repositoryResult.result as List<Room>
                    val localSavedRoom = rooms.map { LocalSavedRoom(it, true) }
                    mutableOpenSoonRoomsStateFlow.value = UIResult.SuccessLocalRooms(localSavedRoom)
                }
            }
        }
    }

    suspend fun fetchAvailableRooms() {
        roomRepository.getAvailableRooms()
            .collect { repositoryResult ->
                when (repositoryResult) {
                    is FirestoreResult.ErrorResult -> mutableAvailableRoomsStateFlow.value =
                        UIResult.Error(repositoryResult.exception)

                    is FirestoreResult.Success<*> -> {
                        val rooms = repositoryResult.result as List<Room>
                        mutableAvailableRoomsStateFlow.value = UIResult.SuccessRooms(rooms)
                    }
                }
            }
    }

}