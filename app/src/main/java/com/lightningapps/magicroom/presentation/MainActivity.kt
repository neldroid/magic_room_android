package com.lightningapps.magicroom.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.lightningapps.magicroom.R
import com.lightningapps.magicroom.presentation.component.UserHomeTopAppBar
import com.lightningapps.magicroom.presentation.component.room.LastOpenRoomsComponent
import com.lightningapps.magicroom.presentation.component.room.OpenSoonRooms
import com.lightningapps.magicroom.presentation.theme.MagicRoomTheme
import com.lightningapps.magicroom.presentation.viewmodel.HomeRoomViewModel
import com.lightningapps.magicroom.presentation.viewmodel.helper.UIResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val homeViewModel by viewModels<HomeRoomViewModel>()

            val availableRoomsResult by homeViewModel.availableRoomsStateFlow.collectAsState()
            val openSoonRoomsResult by homeViewModel.openSoonRoomsStateFlow.collectAsState()
            val basicUserInformation by homeViewModel.basicUserInfoStateFlow.collectAsState()

            MagicRoomTheme(darkTheme = isUserAlive(basicUserInformation)) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    Scaffold(topBar = {
                        UserHomeTopAppBar(basicUserInformation)
                    },
                        floatingActionButton = {
                            FloatingActionButton(onClick = {}) {
                                Icon(
                                    Icons.Filled.Add,
                                    contentDescription = stringResource(id = R.string.addContentDescription)
                                )
                            }
                        }
                    ) { scaffoldPadding ->
                        Column(
                            modifier = Modifier.padding(top = scaffoldPadding.calculateTopPadding()),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            LastOpenRoomsComponent(availableRoomsResult, {})
                            OpenSoonRooms(openSoonResult = openSoonRoomsResult, clickOpenRoomNotify = {})
                        }
                    }
                }
            }
        }
    }

    private fun isUserAlive(basicUserInformation: UIResult) =
        if (basicUserInformation is UIResult.SuccessUser) {
            basicUserInformation.user.life > 0
        } else {
            false
        }

}