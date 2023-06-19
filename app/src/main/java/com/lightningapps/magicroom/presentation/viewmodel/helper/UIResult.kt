package com.lightningapps.magicroom.presentation.viewmodel.helper

import com.lightningapps.magicroom.model.Room

sealed class UIResult{
    data class SuccessRooms(val value: List<Room>): UIResult()

    data class Error(val exception: Throwable?): UIResult()
    object Loading: UIResult()
}
