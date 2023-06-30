package com.lightningapps.magicroom.presentation.viewmodel.helper

import com.lightningapps.magicroom.model.Room
import com.lightningapps.magicroom.model.User

open class UIResult{
    data class SuccessRooms(val rooms: List<Room>): UIResult()

    data class SuccessUser(val user: User): UIResult()
    data class Error(val exception: Throwable?): UIResult()
    object Loading: UIResult()
}
