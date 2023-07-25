package com.lightningapps.magicroom.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lightningapps.magicroom.domain.HomeInformationUseCase
import com.lightningapps.magicroom.domain.WaitingInformationUseCase
import com.lightningapps.magicroom.presentation.viewmodel.helper.UIResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaitingScreenViewModel @Inject constructor(
    private val waitingInformationUseCase: WaitingInformationUseCase
) : ViewModel() {

    // User exclusive flows
    val roomInfoStateFlow: StateFlow<UIResult> =
        waitingInformationUseCase.roomInformationRoomsStateFlow

    operator fun invoke(roomId: String) {
        viewModelScope.launch {
            waitingInformationUseCase.invoke(roomId)
        }
    }
}