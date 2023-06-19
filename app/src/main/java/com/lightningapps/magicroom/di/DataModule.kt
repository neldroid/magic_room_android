package com.lightningapps.magicroom.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lightningapps.magicroom.data.room.IRoomRepository
import com.lightningapps.magicroom.data.room.RoomRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun providesFirebaseFirestore(): FirebaseFirestore =
        Firebase.firestore

}

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModules {
    @Binds
    fun providesRoomRepository(repository: RoomRepository): IRoomRepository
}