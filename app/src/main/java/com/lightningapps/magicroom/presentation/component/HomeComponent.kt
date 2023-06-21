package com.lightningapps.magicroom.presentation.component

import androidx.compose.runtime.Composable
import com.lightningapps.magicroom.presentation.viewmodel.room.RoomViewModel

@Composable
fun HomeComponent(roomViewModel: RoomViewModel, clickAvailableRoom: () -> Unit) {
    LastOpenRoomsComponent(roomViewModel = roomViewModel, clickAvailableRoom)
}