package com.innova.ticketsapp.actividades


import android.app.Application
import android.content.Context
import com.example.mitprinterlibrary.wpos.MitPrinterWpos
import com.example.mitprinterlibrary.wpos.MitPrinterWposJava
import com.example.mitprinterlibrary.wpoy.MitPrinterWpoy
import mit.com.mpos.model.enums.MitEnum

class ActivityApp : Application() {
    companion object {
        lateinit var DEVICE: MitEnum.Device
        lateinit var CONTEXT: Context
    }

    override fun onCreate() {
        super.onCreate()
        //Inicializa variables globales
        CONTEXT = this
        DEVICE = MitEnum.Device.UROVO

        if (DEVICE == MitEnum.Device.WPOS) {
            MitPrinterWposJava.Initialize(this)
        } else if (DEVICE == MitEnum.Device.UROVO) {
            MitPrinterWpoy.Initialize(this)
        }
    }
}