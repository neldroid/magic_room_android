package com.lightningapps.magicroom.presentation.component.room

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.lightningapps.magicroom.model.User
import com.lightningapps.magicroom.presentation.viewmodel.helper.UIResult
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date

@Composable
fun LastOpenRoomsComponent(availableRoomsResult: UIResult, clickAvailableRoom: () -> Unit) {

    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = stringResource(id = R.string.lastOpenTitle),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp)
        )

        when (availableRoomsResult) {
            UIResult.Loading -> {
                CircularProgressIndicator()
            }

            is UIResult.SuccessRooms -> {
                RoomsRow(
                    rooms = availableRoomsResult.rooms,
                    clickAvailableRoom
                )
            }
        }
    }


}

@Composable
fun RoomsRow(rooms: List<Room>, clickAvailableRoom: () -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        items(rooms) { room ->
            OpenRoomItem(room, clickAvailableRoom)
        }
    }
}

/*
Shows the room for the open ones. Use the reaction component
 */
@Composable
fun OpenRoomItem(room: Room, clickAvailableRoom: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = clickAvailableRoom),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = room.title
            )
            if (room.reactions.size > 0) {
                // Show component only when at last is one reaction for the room
                ReactionComponent(room.reactions)
            }
            Text(
                modifier = Modifier
                    .width(150.dp),
                style = MaterialTheme.typography.bodyMedium,
                text = room.lastMessage,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            TimeRoomComponent(room.closingTime)
        }
    }
}

@Composable
fun TimeRoomComponent(closingTime: Date) {
    Text(
        modifier = Modifier.padding(top = 8.dp),
        style = MaterialTheme.typography.bodySmall,
        text = stringResource(
            id = R.string.closingTime,
            SimpleDateFormat("HH:mm").format(closingTime)
        )
    )
}

@Composable
fun ReactionComponent(reactions: MutableList<Reaction>) {
    Row(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (reactions.size == 2) {
            EmojiText(reactions[1].emojiCode)
            Spacer(modifier = Modifier.padding(end = 4.dp))
        }
        // Always set [0] already asked for .size > 0
        EmojiText(reactions[0].emojiCode, true)
        if (reactions.size == 3) {
            Spacer(modifier = Modifier.padding(start = 4.dp))
            EmojiText(reactions[2].emojiCode)
        }
    }
}

@Composable
fun EmojiText(emojiCode: String, mainEmoji: Boolean = false) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary, CircleShape)
            .padding(8.dp),
    ) {
        Text(
            text = emojiCode,
            style = TextStyle(
                fontSize = if (mainEmoji) 30.sp else 20.sp
            )

        )
    }
}

@Preview(widthDp = 250)
@Composable
fun CheckRowPreview() {
    RoomsRow(
        rooms = listOf(
            Room(
                "",
                "Title room",
                "This is the description",
                "This is the last message in the chat room",
                mutableListOf(Reaction("\uD83C\uDF36️", 5), Reaction("❤️", 3)),
                Capacity(20, 15),
                "#9CCC65",
                Date.from(Instant.now()),
                User()
            ),
            Room(
                "",
                "Titulo de sala",
                "Esta es la descripcion",
                "Este es el ultimo mensaje de la sala",
                mutableListOf(Reaction("❤️", 5)),
                Capacity(20, 15),
                "#9CCC65",
                Date.from(Instant.now()),
                User()
            )
        ), {}
    )
}

