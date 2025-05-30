// app/src/main/java/com/example/mob_project/MobApplication.kt
package com.example.mob_project

import android.app.Application
import com.example.mob_project.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MobApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Force database creation }
}
}