package com.lightningapps.magicroom.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lightningapps.magicroom.presentation.component.room.LastOpenRoomsComponent
import com.lightningapps.magicroom.presentation.component.room.OpenSoonRooms
import com.lightningapps.magicroom.presentation.theme.MagicRoomTheme
import com.lightningapps.magicroom.presentation.viewmodel.room.HomeRoomViewModel
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
                    val roomViewModel by viewModels<HomeRoomViewModel>()
                    val availableRoomsResult by roomViewModel.availableRoomsStateFlow.collectAsState()
                    val openSoonRoomsResult by roomViewModel.openSoonRoomsStateFlow.collectAsState()

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        LastOpenRoomsComponent(availableRoomsResult, {})
                        OpenSoonRooms(openSoonResult = openSoonRoomsResult, {})
                    }
                }
            }
        }
    }
}