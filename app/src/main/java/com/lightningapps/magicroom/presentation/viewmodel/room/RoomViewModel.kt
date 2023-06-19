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
class RoomViewModel @Inject constructor(
    private val roomRepository: IRoomRepository
) : ViewModel() {

    private val mutableRoomStateFlow: MutableStateFlow<UIResult> =
        MutableStateFlow(UIResult.Loading)
    val roomStateFlow: StateFlow<UIResult> = mutableRoomStateFlow

    init {
        viewModelScope.launch {
            roomRepository.getAvailableRooms()
                .collect { repositoryResult ->
                    when (repositoryResult) {
                        is FirestoreResult.ErrorResult -> mutableRoomStateFlow.value =
                            UIResult.Error(repositoryResult.exception)

                        is FirestoreResult.Success<*> -> {
                            val rooms = repositoryResult.result as List<Room>
                            mutableRoomStateFlow.value = UIResult.SuccessRooms(rooms)
                        }
                    }
                }
        }
    }

}