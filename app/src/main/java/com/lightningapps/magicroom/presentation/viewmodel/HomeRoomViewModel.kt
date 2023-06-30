package com.lightningapps.magicroom.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lightningapps.magicroom.data.helper.FirestoreResult
import com.lightningapps.magicroom.data.room.IRoomRepository
import com.lightningapps.magicroom.model.Room
import com.lightningapps.magicroom.presentation.viewmodel.helper.UIResult
import com.lightningapps.magicroom.presentation.viewmodel.room.RoomViewModel
import com.lightningapps.magicroom.presentation.viewmodel.user.UserViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeRoomViewModel @Inject constructor(
    private val roomViewModel: RoomViewModel,
    private val userViewModel: UserViewModel
) : ViewModel() {

    // User exclusive flows
    val basicUserInfoStateFlow: StateFlow<UIResult> = userViewModel.basicInformation

    // Room exclusive flows
    val availableRoomsStateFlow: StateFlow<UIResult> = roomViewModel.availableRoomsStateFlow
    val openSoonRoomsStateFlow: StateFlow<UIResult> = roomViewModel.openSoonRoomsStateFlow

    init {
        viewModelScope.launch {
            roomViewModel.fetchAvailableRooms()
        }
        viewModelScope.launch {
            roomViewModel.fetchOpenSoonRooms()
        }
        viewModelScope.launch {
            userViewModel.fetchBasicInfo()
        }
    }
}