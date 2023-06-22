package com.lightningapps.magicroom.data.helper

import java.lang.Exception

open class FirestoreResult {

    data class Success<T>(val result: T) : FirestoreResult()

    data class ErrorResult(val exception: Throwable) : FirestoreResult(){
        companion object {
            const val SNAPSHOT_NULL_ERROR = "FIRESTORE Snapshot null exception"
        }
    }
}
