package com.lightningapps.magicroom.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.lightningapps.magicroom.R
import com.lightningapps.magicroom.model.Capacity
import com.lightningapps.magicroom.model.Reaction
import com.lightningapps.magicroom.model.Room
import com.lightningapps.magicroom.presentation.viewmodel.helper.UIResult
import com.lightningapps.magicroom.presentation.viewmodel.room.RoomViewModel

@Composable
fun LastOpenRoomsComponent(roomViewModel: RoomViewModel, clickAvailableRoom: () -> Unit) {
    val availableRoomsResult by roomViewModel.roomStateFlow.collectAsState()

    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = stringResource(id = R.string.lastOpenTitle),
            modifier = Modifier.padding(end = 8.dp)
        )

        when (availableRoomsResult) {
            is UIResult.Error -> {
//            Text(text = (availableRoomsResult as UIResult.Error).exception?.message.toString())
            }

            UIResult.Loading -> {
                CircularProgressIndicator()
            }

            is UIResult.SuccessRooms -> {
                RoomsRow(
                    rooms = (availableRoomsResult as UIResult.SuccessRooms).value,
                    clickAvailableRoom
                )
            }
        }
    }


}

@Composable
fun RoomsRow(rooms: List<Room>, clickAvailableRoom: () -> Unit) {
    Row {
        rooms.forEach { room ->
            OpenRoomItem(room, clickAvailableRoom)
        }
    }
}

/*
Shows the room for the open ones. Use the reaction component
 */
@Composable
fun OpenRoomItem(room: Room, clickAvailableRoom: () -> Unit) {
    val roomColor = Color(room.backgroundColor.toColorInt())

    Card(
        colors = CardDefaults.cardColors(
            containerColor = roomColor
        ),
        modifier = Modifier
            .padding(4.dp)
            .clickable(onClick = clickAvailableRoom)
            .width(150.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = room.title
            )
            if (room.reactions.size > 0) {
                // Show component only when at last is one reaction for the room
                ReactionComponent(room.reactions, roomColor)
            }
            Text(
                style = MaterialTheme.typography.bodySmall,
                text = room.lastMessage,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ReactionComponent(reactions: List<Reaction>, roomColor: Color) {
    Row(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (reactions[1] != null) {
            EmojiText(reactions[1].emojiCode)
            Spacer(modifier = Modifier.padding(end = 4.dp))
        }
        EmojiText(reactions[1].emojiCode, roomColor, true)
        if (reactions[2] != null) {
            Spacer(modifier = Modifier.padding(start = 4.dp))
            EmojiText(reactions[2].emojiCode)
        }
    }
}

@Composable
fun EmojiText(emojiCode: String, roomColor: Color = Color.Black, mainEmoji: Boolean = false) {
    Box(
        modifier = Modifier
            .background(Color.White, CircleShape),
    ) {
        Text(
            text = emojiCode,
            modifier = Modifier
                .padding(if (mainEmoji) 8.dp else 4.dp),
            style = TextStyle(
                fontSize = if (mainEmoji) 15.sp else 10.sp
            )

        )
    }
}

@Preview
@Composable
fun CheckRowPreview() {
    OpenRoomItem(
        room = Room(
            "",
            "Title room",
            "This is the last message in the chat room",
            mutableListOf(Reaction("\uD83C\uDF36️", 5), Reaction("❤️", 3)),
            Capacity(20, 15),
            "#9CCC65"
        ),
        clickAvailableRoom = { }
    )
}

