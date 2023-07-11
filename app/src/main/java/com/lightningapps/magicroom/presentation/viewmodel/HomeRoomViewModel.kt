package com.lightningapps.magicroom.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lightningapps.magicroom.domain.HomeInformationUseCase
import com.lightningapps.magicroom.presentation.viewmodel.helper.UIResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeRoomViewModel @Inject constructor(
    private val homeInformationUseCase: HomeInformationUseCase
) : ViewModel() {

    // User exclusive flows
    val basicUserInfoStateFlow: StateFlow<UIResult> = homeInformationUseCase.basicInformation

    // Room exclusive flows
    val availableRoomsStateFlow: StateFlow<UIResult> = homeInformationUseCase.availableRoomsStateFlow
    val openSoonRoomsStateFlow: StateFlow<UIResult> = homeInformationUseCase.openSoonRoomsStateFlow

    init {
        viewModelScope.launch {
            homeInformationUseCase.invoke()
        }
    }
}