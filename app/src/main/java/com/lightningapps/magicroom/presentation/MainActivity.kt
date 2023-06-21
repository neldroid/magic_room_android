package com.lightningapps.magicroom.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.lightningapps.magicroom.presentation.component.LastOpenRoomsComponent
import com.lightningapps.magicroom.presentation.theme.MagicRoomTheme
import com.lightningapps.magicroom.presentation.viewmodel.room.RoomViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MagicRoomTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val roomViewModel by viewModels<RoomViewModel>()
                    LastOpenRoomsComponent(roomViewModel = roomViewModel, {})
                }
            }
        }
    }
}