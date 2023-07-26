package com.lightningapps.magicroom.domain

import android.util.Log
import com.lightningapps.magicroom.data.helper.FirestoreResult
import com.lightningapps.magicroom.data.room.IRoomRepository
import com.lightningapps.magicroom.data.user.IUserRepository
import com.lightningapps.magicroom.model.LocalSavedRoom
import com.lightningapps.magicroom.model.Room
import com.lightningapps.magicroom.model.User
import com.lightningapps.magicroom.presentation.viewmodel.helper.UIResult
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
This use case handle the logic to retrieve information to show in the home screen.
The user could access different rooms depending on their life status. The room query must to change depending if it's in the spirit realm or not
 */
class HomeInformationUseCase @Inject constructor(
    private val userRepository: IUserRepository,
    private val roomRepository: IRoomRepository
) {

    private val mutableBasicInfoStateFlow: MutableStateFlow<UIResult> =
        MutableStateFlow(UIResult.Loading)
    val basicInformation: StateFlow<UIResult> = mutableBasicInfoStateFlow

    private val mutableAvailableRoomsStateFlow: MutableStateFlow<UIResult> =
        MutableStateFlow(UIResult.Loading)
    val availableRoomsStateFlow: StateFlow<UIResult> = mutableAvailableRoomsStateFlow

    private val mutableOpenSoonRoomsStateFlow: MutableStateFlow<UIResult> =
        MutableStateFlow(UIResult.Loading)
    val openSoonRoomsStateFlow: StateFlow<UIResult> = mutableOpenSoonRoomsStateFlow

    /*
    Get the user information first. With the user life could determine if need to show rooms normal or from the spirit realm
     */
    suspend operator fun invoke() {
        var isAlive = false
        userRepository.getBasicUserInformation().collect { userRepositoryResult ->
            when (userRepositoryResult) {
                is FirestoreResult.Success<*> -> {
                    val userBasicInformation = userRepositoryResult.result as User
                    isAlive = userBasicInformation.life > 0
                    mutableBasicInfoStateFlow.value = UIResult.SuccessUser(userBasicInformation)
                }
            }
            collectOpenSoonRooms(isAlive)
            collectAvailableRooms(isAlive)
        }
//        coroutineScope {
//            launch {
//            }
//            launch {
//            }
//        }
    }

    private suspend fun collectOpenSoonRooms(isAlive: Boolean) {
        roomRepository.getOpenSoonRooms(isAlive).collect { repositoryResult ->
            when (repositoryResult) {
                is FirestoreResult.ErrorResult -> mutableOpenSoonRoomsStateFlow.value =
                    UIResult.Error(repositoryResult.exception)

                is FirestoreResult.Success<*> -> {
                    val rooms = repositoryResult.result as List<Room>
                    val localSavedRoom = rooms.map { LocalSavedRoom(it, true) }
                    mutableOpenSoonRoomsStateFlow.value = UIResult.SuccessLocalRooms(localSavedRoom)
                }
            }
        }
    }

    private suspend fun collectAvailableRooms(isAlive: Boolean) {
        roomRepository.getAvailableRooms(isAlive)
            .collect { repositoryResult ->
                when (repositoryResult) {
                    is FirestoreResult.ErrorResult -> mutableAvailableRoomsStateFlow.value =
                        UIResult.Error(repositoryResult.exception)

                    is FirestoreResult.Success<*> -> {
                        val rooms = repositoryResult.result as List<Room>
                        mutableAvailableRoomsStateFlow.value = UIResult.SuccessRooms(rooms)
                    }
                }
            }
    }

}