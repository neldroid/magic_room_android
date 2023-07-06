package com.lightningapps.magicroom.presentation.component.room

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lightningapps.magicroom.R
import com.lightningapps.magicroom.model.LocalSavedRoom
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

            is UIResult.SuccessLocalRooms -> {
                RoomsComponent(openSoonResult.localRooms)
            }

            is UIResult.Error -> {
                Text(openSoonResult.exception?.message.toString())
            }
        }
    }

}

@Composable
fun RoomsComponent(localSavedRooms: List<LocalSavedRoom>) {
    if (localSavedRooms.isEmpty()) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.nonOpenSoonRooms)
        )
    } else {
        LazyColumn {
            items(localSavedRooms) { localSavedRoom ->
                OpenSoonRoomItem(localSavedRoom)
            }
        }
    }
}

@Composable
fun OpenSoonRoomItem(localSavedRoom: LocalSavedRoom) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                val room = localSavedRoom.room
                
                Text(text = room.title)
                Text(text = room.user.nickname)
                Text(text = room.description)
            }
            Icon(
                imageVector = if (localSavedRoom.isActiveNotification) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                contentDescription = stringResource(id = R.string.notificationContentDescription)
            )
        }
    }
}


