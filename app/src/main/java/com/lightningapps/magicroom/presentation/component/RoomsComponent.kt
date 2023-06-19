package com.lightningapps.magicroom.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lightningapps.magicroom.model.Room
import com.lightningapps.magicroom.presentation.viewmodel.helper.UIResult
import com.lightningapps.magicroom.presentation.viewmodel.room.RoomViewModel

@Composable
fun RoomsComponent(roomViewModel: RoomViewModel) {
    val availableRoomsResult by roomViewModel.roomStateFlow.collectAsState()

    when (availableRoomsResult) {
        is UIResult.Error -> {
            Text(text = (availableRoomsResult as UIResult.Error).exception?.message.toString())
        }

        UIResult.Loading -> {
            Text(text = "Loading...")
        }

        is UIResult.SuccessRooms -> {
            RoomsRow(rooms = (availableRoomsResult as UIResult.SuccessRooms).value)
        }
    }
}

@Composable
fun RoomsRow(rooms: List<Room>) {
    Row {
        rooms.forEach { room ->
            RoomItem(room)
        }
    }
}

@Composable
fun RoomItem(room: Room) {
    Card(modifier = Modifier.padding(8.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Text(text = room.title)
        Text(text = room.capacity.total.toString())
        Text(text = room.capacity.remaining.toString())
        Text(text = room.capacity.current.toString())

        Text(text = room.lastMessage)

        Column {
            room.reactions.forEach { 
                Text(text = it.emojiCode)
            }
        }
    }
}
