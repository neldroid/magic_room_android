package com.lightningapps.magicroom.presentation.component.room

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.lightningapps.magicroom.R
import com.lightningapps.magicroom.model.Capacity
import com.lightningapps.magicroom.model.LocalSavedRoom
import com.lightningapps.magicroom.model.MessageConfiguration
import com.lightningapps.magicroom.model.Reaction
import com.lightningapps.magicroom.model.Room
import com.lightningapps.magicroom.model.User
import com.lightningapps.magicroom.presentation.viewmodel.helper.UIResult
import java.time.Instant
import java.util.Date

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
    val room = localSavedRoom.room

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(room.backgroundColor.toColorInt())
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {

                Text(style = MaterialTheme.typography.titleMedium, text = room.title)
                Text(
                    style = MaterialTheme.typography.labelSmall,
                    text = stringResource(id = R.string.createdBy, room.user.nickname)
                )
                Text(style = MaterialTheme.typography.bodyMedium, text = room.description)
            }
            Icon(
                imageVector = if (localSavedRoom.isActiveNotification) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                contentDescription = stringResource(id = R.string.notificationContentDescription)
            )
        }
    }
}

@Preview
@Composable
fun CheckOpenSoonRowPreview() {
    OpenSoonRoomItem(
        LocalSavedRoom(
            room = Room(
                "",
                "Title room",
                "This is the description",
                "This is the last message in the chat room",
                mutableListOf(Reaction("\uD83C\uDF36️", 5), Reaction("❤️", 3)),
                Capacity(20, 15),
                "#A0D8E0",
                Date.from(Instant.now()),
                User(
                    "",
                    "nickname",
                    0,
                    0,
                    MessageConfiguration()
                )
            ), true
        )
    )
}


