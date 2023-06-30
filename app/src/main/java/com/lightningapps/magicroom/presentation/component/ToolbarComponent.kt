package com.lightningapps.magicroom.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lightningapps.magicroom.model.MessageConfiguration
import com.lightningapps.magicroom.model.User
import com.lightningapps.magicroom.presentation.viewmodel.helper.UIResult
import kotlinx.coroutines.flow.StateFlow

@Composable
fun UserHomeTopAppBar(basicUserInfoStateFlow: UIResult) {
    when (basicUserInfoStateFlow) {
        is UIResult.SuccessUser -> {
            val user = basicUserInfoStateFlow.user

            UserInformationSection(user)
        }
    }
}

@Composable
fun UserInformationSection(user: User) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 8.dp, bottom = 8.dp, end = 4.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(text = user.gems.toString())
    }
}


@Preview
@Composable
fun testUserSection() {
    val user = User(
        "",
        "UserNickname",
        25,
        5,
        MessageConfiguration()
    )

    UserInformationSection(user = user)
}
