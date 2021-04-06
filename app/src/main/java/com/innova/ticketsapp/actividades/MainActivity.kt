package com.innova.ticketsapp.actividades

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mitprinterlibrary.MitTools
import com.example.mitprinterlibrary.wpos.MitPrinterWpos
import com.example.mitprinterlibrary.wpos.MitPrinterWposJava
import com.example.mitprinterlibrary.wpoy.MitPrinterWpoy
import com.mit.api.pr_api_base.model.PrResponse

import mit.com.mpos.model.enums.MitEnum
import mit.com.mpos.model.intelipos.business.print.MitVaucher
import mit.com.mpos.model.intelipos.business.print.MitVaucherLine
import model.Callback
import model.Error
import com.innova.ticketsapp.R;


class MainActivity : AppCompatActivity(), Callback<PrResponse> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    /**
     * Imprime un vaucher
     */
    private fun printerExample() {
        val oVaucher = MitVaucher()

        //Ejemplo de linea de texto, (cuando se utiliza UROVO el texto solo tiene 3 tamaños)
        oVaucher.vaucherLine.add(MitVaucherLine().apply {
            text = "Hola mundo MIT"
            bold = true
            size =  2
        })

        //Linea vacia
        oVaucher.vaucherLine.add(MitVaucherLine())

        oVaucher.vaucherLine.add(MitVaucherLine().apply {
            text = "123456789012345678901234567890abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"
            bold = true
            size =  0
            align = MitEnum.LineAlign.LEFT
        })

        //Linea vacia
        oVaucher.vaucherLine.add(MitVaucherLine())

        //Codigo de barras
        oVaucher.vaucherLine.add(MitVaucherLine().apply {
            text = "70050020020"
            size =  400
            type = MitEnum.LineType.CODE_BAR
        })

        //Linea vacia
        oVaucher.vaucherLine.add(MitVaucherLine())

        //Imagen en array de bytes
        // LAS IMAGENES DEBEN TENER EL TAMAÑO REAL DEL TICKET PARA EVITAR PROBLEMAS
        // POR EL MOMENTO LA CONFIGURACION DE TAMAÑO SOLO SE RESPETA EN LA WPOS
        val logo: Bitmap? = MitTools.getLogoBitmap(this, R.drawable.logo)
        val imageData: ByteArray? = MitTools.getBitmapBytesPNG(logo)
        oVaucher.vaucherLine.add(MitVaucherLine().apply {
            type = MitEnum.LineType.IMG_BYTE
            byteImg = imageData
            size = 300
        })

        //Linea vacia
        oVaucher.vaucherLine.add(MitVaucherLine())

        //Imagen local
        /*oVaucher.vaucherLine.add(MitVaucherLine().apply {
            type = MitEnum.LineType.IMG_LOCAL;
            localImg = "ruta absoluta de la imagen en la terminal"
            size = 100
        })*/

        //Codigo QR
        oVaucher.vaucherLine.add(MitVaucherLine().apply {
            text = "https://www.intelipos.io/i/"
            size =  800
            type = MitEnum.LineType.CODE_QR
        })

        if (false) {
            val oPrinter = MitPrinterWposJava(oVaucher, this)
            oPrinter.printVaucher()
        } else {
            val oPrinter = MitPrinterWpoy(oVaucher, this)
            oPrinter.execute()
        }
    }

    override fun onSuccess(p0: PrResponse?) {
        Log.d("TAG", "Se imprimio de manera correcta")
    }

    override fun onError(p0: Error?) {
        Log.e("TAG", p0?.message ?: "Error no controlado en la impresión")
    }
}
