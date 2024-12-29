package com.example.roomdatabase

import android.app.Application
import com.example.roomdatabase.repository.AppContainer
import com.example.roomdatabase.repository.MahasiswaContainer

class MahasiswaApplications:Application() {
    lateinit var container: AppContainer

    override fun onCreate(){
            super.onCreate()
            container = MahasiswaContainer()
        }
    }