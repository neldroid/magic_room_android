package com.lightningapps.magicroom.presentation.viewmodel.user

import androidx.lifecycle.ViewModel
import com.lightningapps.magicroom.data.helper.FirestoreResult
import com.lightningapps.magicroom.data.user.IUserRepository
import com.lightningapps.magicroom.model.Room
import com.lightningapps.magicroom.model.User
import com.lightningapps.magicroom.presentation.viewmodel.helper.UIResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class UserViewModel @Inject constructor(
    private val userRepository: IUserRepository
): ViewModel(){
    private val mutableBasicInfoStateFlow: MutableStateFlow<UIResult> =
        MutableStateFlow(UIResult.Loading)
    val basicInformation: StateFlow<UIResult> = mutableBasicInfoStateFlow

    suspend fun fetchBasicInfo() {
        userRepository.getBasicUserInformation()
            .collect { repositoryResult ->
                when (repositoryResult) {
                    is FirestoreResult.ErrorResult -> mutableBasicInfoStateFlow.value =
                        UIResult.Error(repositoryResult.exception)

                    is FirestoreResult.Success<*> -> {
                        val user = repositoryResult.result as User
                        mutableBasicInfoStateFlow.value = UIResult.SuccessUser(user)
                    }
                }
            }
    }
}