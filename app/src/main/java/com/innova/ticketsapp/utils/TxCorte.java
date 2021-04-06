package com.innova.ticketsapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mit.api.pr_api_base.model.Ticket;
import com.mit.api.pr_api_base.model.printableElement.PrintableElement;
import com.mit.api.pr_api_base.model.printableElement.PrintableImage;
import com.mit.api.pr_api_base.model.printableElement.PrintableString;
import com.mit.api.pr_api_base.model.printableElement.PrintableStringExt;
import com.mit.api.pr_api_base.model.printableOption.PrintableAlign;
import com.mit.api.pr_api_base.model.printableOption.PrintableFont;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TxCorte implements Ticket {

    private Context context;
    private JSONObject ticket;
    private Double ImporteTotal=0.0;


    public TxCorte(Context context, JSONObject txInfo) {
        this.context = context;
        this.ticket = txInfo;
    }

    @Override
    public List<PrintableElement> toPrint() {
        try{
            // Default builder style to make PrintableString objects
            PrintableString.Builder defaultStringStyleBuilder = new PrintableString.Builder();
            ArrayList<PrintableElement> list = new ArrayList<>();

            String data=ticket.getString("logo");
            byte[] base64converted=android.util.Base64.decode(data.trim(), android.util.Base64.DEFAULT);

            BitmapFactory.Options options = new BitmapFactory.Options();
            /*
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(base64converted,0,base64converted.length,options);
*/
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, 500, 500);
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            Bitmap logo=BitmapFactory.decodeByteArray(base64converted,0,base64converted.length,options);
            list.add(new PrintableImage(logo, 120, PrintableAlign.CENTER));
            list.add(PrintableString.emptyLine(8));


            // Imprimir titulo


            String line = ticket.getString("encabezado");
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setBold(true).setFontSize(27).setAlign(PrintableAlign.CENTER).build());


            line = "Fecha Inicio:" +ticket.getString("fechaInicio");
            list.add(PrintableString.emptyLine(12));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

            line = "Hora Inicio:" +ticket.getString("horaInicio");
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

            line = "Fecha Fin:" +ticket.getString("fechaFin");
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

            line = "Hora Fin:" +ticket.getString("horaFin");
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

            line = "Fecha Impresion:" +ticket.getString("fechaImpresion");
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());


            line = "Hora Impresion:" +ticket.getString("horaImpresion");
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

            line = "Corte:" +ticket.getString("corte");
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

            line = "Detalle del Corte:";
            list.add(PrintableString.emptyLine(8));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(25).setBold(true).setAlign(PrintableAlign.CENTER).build());


            for (int i=0;i<ticket.getJSONArray("detalleCorte").length();i++) {
                list.add(PrintableString.emptyLine(4));
                JSONObject temp=ticket.getJSONArray("detalleCorte").getJSONObject(i);
                //System.out.println(String.valueOf(temp.getString("descripcion").length()));
                line = "Posición:" +temp.getString("posicion");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

                line = "Isla:" +temp.getString("isla");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

                line = "Producto:" +temp.getString("producto");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

                line = "Totalizador Inicial:" +temp.getString("totInicial");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

                line = "Totalizador Final:" +temp.getString("totFinal");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

                line = "Litros:" +temp.getString("litros");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

                line = "Precio:" +temp.getString("precio");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

                line = "Importe:" +temp.getString("importe");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

            }

            line = "Concentrado Corte:";
            list.add(PrintableString.emptyLine(8));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(25).setBold(true).setAlign(PrintableAlign.LEFT).build());


            for (int i=0;i<ticket.getJSONArray("concentradoCorte").length();i++) {
                list.add(PrintableString.emptyLine(4));
                JSONObject temp=ticket.getJSONArray("concentradoCorte").getJSONObject(i);
                //System.out.println(String.valueOf(temp.getString("descripcion").length()));
                line = "Producto:" +temp.getString("producto");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

                line = "Litros:" +temp.getString("litros");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

                line = "Importe:" +temp.getString("importe");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

            }

            line = "Concentrado Isla:";
            list.add(PrintableString.emptyLine(8));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(25).setBold(true).setAlign(PrintableAlign.LEFT).build());


            for (int i=0;i<ticket.getJSONArray("concentradoIsla").length();i++) {
                list.add(PrintableString.emptyLine(4));
                JSONObject temp=ticket.getJSONArray("concentradoIsla").getJSONObject(i);
                //System.out.println(String.valueOf(temp.getString("descripcion").length()));

                line = "Isla:" +temp.getString("isla");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

                line = "Producto:" +temp.getString("producto");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

                line = "Litros:" +temp.getString("litros");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

                line = "Precio:" +temp.getString("precio");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

                line = "Importe:" +temp.getString("importe");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

            }

            return list;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }


    }

    private String espacio(int a,int b){
        String s="";
        for(int i=0;i<(b-a);i++){
            s+=" ";
        }
        return s;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;

        int stretch_width = Math.round((float)width / (float)reqWidth);
        int stretch_height = Math.round((float)height / (float)reqHeight);

        if (stretch_width <= stretch_height)
            return stretch_height;
        else
            return stretch_width;
    }

}