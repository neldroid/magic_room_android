package com.lightningapps.magicroom.domain

import com.lightningapps.magicroom.data.helper.FirestoreResult
import com.lightningapps.magicroom.data.room.IRoomRepository
import com.lightningapps.magicroom.model.Room
import com.lightningapps.magicroom.presentation.viewmodel.helper.UIResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class WaitingInformationUseCase @Inject constructor(
    private val roomRepository: IRoomRepository
) {

    private val mutableRoomInformationStateFlow: MutableStateFlow<UIResult> =
        MutableStateFlow(UIResult.Loading)
    val roomInformationRoomsStateFlow: StateFlow<UIResult> = mutableRoomInformationStateFlow

    suspend operator fun invoke(roomId: String) {
        roomRepository.getRoomWaitingInformation(roomId).collect {repositoryResult ->
            when(repositoryResult){
                is FirestoreResult.Success<*> -> {
                    val room = repositoryResult.result as Room
                    mutableRoomInformationStateFlow.value = UIResult.SuccessRoom(room)
                }
            }
        }
    }

}