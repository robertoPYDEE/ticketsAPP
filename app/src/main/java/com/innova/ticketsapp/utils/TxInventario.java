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


public class TxInventario implements Ticket {

    private Context context;
    private JSONObject ticket;
    private Double ImporteTotal=0.0;
    private String line;


    public TxInventario(Context context, JSONObject txInfo) {
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


            for(int i=0;i<ticket.getJSONArray("encabezado").length();i++){
                String line = ticket.getJSONArray("encabezado").get(i).toString();
                //list.add(PrintableString.emptyLine(1));
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setFontSize(27).setAlign(PrintableAlign.LEFT).setBold(true).build());

            }

            list.add(PrintableString.emptyLine(6));

            for (int i=0;i<ticket.getJSONArray("cuerpo").length();i++) {
                line = ticket.getJSONArray("cuerpo").get(i).toString();
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setFontSize(23).setAlign(PrintableAlign.LEFT).setBold(true).build());
            }

            /*

            for (int i=0;i<ticket.getJSONArray("cuerpo").length();i++) {
                list.add(PrintableString.emptyLine(4));
                JSONObject temp=ticket.getJSONArray("cuerpo").getJSONObject(i);
                //System.out.println(String.valueOf(temp.getString("descripcion").length()));
                line = "No de Tanque: " +temp.getString("tanque");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setFontSize(23).setAlign(PrintableAlign.LEFT).setBold(true).build());

                line = "Producto: " +temp.getString("producto");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setFontSize(23).setAlign(PrintableAlign.LEFT).setBold(true).build());

                line = "Capacidad: " +temp.getString("capacidad");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setFontSize(23).setAlign(PrintableAlign.LEFT).setBold(true).build());

                line = "Vol(95%): " +temp.getString("vol(95%)");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setFontSize(23).setAlign(PrintableAlign.LEFT).setBold(true).build());

                line = "Por llenar: " +temp.getString("por llenar");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setFontSize(23).setAlign(PrintableAlign.LEFT).setBold(true).build());

                line = "Volumen: " +temp.getString("volumen");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setFontSize(23).setAlign(PrintableAlign.LEFT).setBold(true).build());

                line = "Nivel: " +temp.getString("nivel");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setFontSize(23).setAlign(PrintableAlign.LEFT).setBold(true).build());

                line = "Nivel Agua: " +temp.getString("nivel agua");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setFontSize(23).setAlign(PrintableAlign.LEFT).setBold(true).build());

                line = "Temperatura: " +temp.getString("temp");
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setFontSize(23).setAlign(PrintableAlign.LEFT).setBold(true).build());

            }
*/


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