package com.lightningapps.magicroom.presentation.component.room

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lightningapps.magicroom.R
import com.lightningapps.magicroom.model.Room
import com.lightningapps.magicroom.presentation.viewmodel.helper.UIResult

@Composable
fun OpenSoonRooms(openSoonResult: UIResult, clickOpenRoomNotify: () -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = stringResource(id = R.string.openSoonTitle),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp)
        )

        when (openSoonResult) {
            UIResult.Loading -> {
                CircularProgressIndicator()
            }

            is UIResult.SuccessRooms -> {
                RoomsComponent(openSoonResult.rooms)
            }

            is UIResult.Error -> {
                Text(openSoonResult.exception?.message.toString())
            }
        }
    }

}

@Composable
fun RoomsComponent(rooms: List<Room>) {
    if (rooms.isEmpty()) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.nonOpenSoonRooms)
        )
    } else {
        LazyColumn {
            items(rooms) { room ->
                OpenSoonRoomItem(room)
            }
        }
    }
}

@Composable
fun OpenSoonRoomItem(room: Room) {
    TODO("Not yet implemented")
}
