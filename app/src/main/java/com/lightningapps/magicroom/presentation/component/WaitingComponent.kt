package com.lightningapps.magicroom.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.lightningapps.magicroom.model.Room
import com.lightningapps.magicroom.presentation.viewmodel.helper.UIResult

@Composable
fun WaitingScreen(waitingScreenResult: UIResult) =
    when (waitingScreenResult) {
        is UIResult.SuccessRoom -> {
            val information = waitingScreenResult.room
            ScreenContent(information)
        }

        else -> {}
    }


@Composable
fun ScreenContent(information: Room) {
    TitleSection(information.title, information.description)
}

@Composable
fun TitleSection(title: String, description: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Text(text = description, style = MaterialTheme.typography.bodyMedium)
    }
}
