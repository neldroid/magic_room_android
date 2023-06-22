package com.lightningapps.magicroom.presentation.viewmodel.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lightningapps.magicroom.data.helper.FirestoreResult
import com.lightningapps.magicroom.data.room.IRoomRepository
import com.lightningapps.magicroom.model.Room
import com.lightningapps.magicroom.presentation.viewmodel.helper.UIResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeRoomViewModel @Inject constructor(
    private val roomRepository: IRoomRepository
) : ViewModel() {

    private val mutableAvailableRoomsStateFlow: MutableStateFlow<UIResult> =
        MutableStateFlow(UIResult.Loading)
    val availableRoomsStateFlow: StateFlow<UIResult> = mutableAvailableRoomsStateFlow


    private val mutableOpenSoonRoomsStateFlow: MutableStateFlow<UIResult> =
        MutableStateFlow(UIResult.Loading)
    val openSoonRoomsStateFlow: StateFlow<UIResult> = mutableOpenSoonRoomsStateFlow

    init {
        viewModelScope.launch {
            fetchAvailableRooms()
            fetchOpenSoonRooms()
        }
    }

    private fun fetchOpenSoonRooms() {
        roomRepository.getOpenSoonRooms()
    }

    private suspend fun fetchAvailableRooms() {
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