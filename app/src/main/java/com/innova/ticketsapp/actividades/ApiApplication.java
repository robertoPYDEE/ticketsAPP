package com.innova.ticketsapp.actividades;

import android.app.Application;
import android.content.Context;


import com.mit.api.pr_api_base.service.PrintCoreServiceImpl;
import com.example.mitprinterlibrary.wpos.MitPrinterWpos;
import com.example.mitprinterlibrary.wpos.MitPrinterWposJava;
import com.example.mitprinterlibrary.wpoy.MitPrinterWpoy;
import mit.com.mpos.model.enums.MitEnum.Device;





public class ApiApplication extends Application {

    static Device DEVICE;
    static Context CONTEXT;

    public void onCreate() {
        super.onCreate();
       // PrintCoreServiceImpl.init(getApplicationContext());

        CONTEXT = this;
        DEVICE = Device.UROVO;




        if (DEVICE == Device.WPOS) {
            MitPrinterWposJava.Initialize(this);
            System.err.println("WPOS");
        } else if (DEVICE == Device.UROVO) {
            MitPrinterWpoy.Initialize(this);
            System.err.println("UROVO----"+DEVICE);
        }


    }


}
