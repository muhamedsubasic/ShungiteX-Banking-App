// app/src/main/java/com/example/mob_project/MobApplication.kt
package com.example.mob_project

import android.app.Application
import com.example.mob_project.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import jakarta.inject.Inject
import kotlinx.coroutines.*

// Trigger database creation in the background
@HiltAndroidApp
class MobApplication : Application() {

    @Inject
    lateinit var appDatabase: AppDatabase

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.userDao().getUserById(1)
        }
    }
}
