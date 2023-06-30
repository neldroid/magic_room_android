package com.lightningapps.magicroom.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.lightningapps.magicroom.presentation.component.room.LastOpenRoomsComponent
import com.lightningapps.magicroom.presentation.viewmodel.HomeRoomViewModel

@Composable
fun HomeComponent(roomViewModel: HomeRoomViewModel, clickAvailableRoom: () -> Unit) {
    val availableRoomsResult by roomViewModel.availableRoomsStateFlow.collectAsState()

    LastOpenRoomsComponent(availableRoomsResult, clickAvailableRoom)
}