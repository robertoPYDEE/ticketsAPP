package com.innova.ticketsapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mit.api.pr_api_base.model.Ticket;
import com.mit.api.pr_api_base.model.printableElement.PrintableBarCodeBase;
import com.mit.api.pr_api_base.model.printableElement.PrintableElement;
import com.mit.api.pr_api_base.model.printableElement.PrintableImage;
import com.mit.api.pr_api_base.model.printableElement.PrintableString;
import com.mit.api.pr_api_base.model.printableElement.PrintableStringExt;
import com.mit.api.pr_api_base.model.printableOption.PrintableAlign;
import com.mit.api.pr_api_base.model.printableOption.PrintableBarCodeType;
import com.mit.api.pr_api_base.model.printableOption.PrintableBarCodeWidth;
import com.mit.api.pr_api_base.model.printableOption.PrintableFont;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import com.innova.ticketsapp.utils.Formatters;



public class TxTicket implements Ticket {

    private Context context;
    private JSONObject ticket;
    private Double ImporteTotal=0.0;


    public TxTicket(Context context, JSONObject txInfo) {
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
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(27).setAlign(PrintableAlign.LEFT).build());

            }

            String line = "Cuenta:" +ticket.getString("cuenta");
            list.add(PrintableString.emptyLine(10));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());
            //list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(28).setBold(false).build());

            // Fuente en negritas
            line = "Subcuenta:" +ticket.getString("subcuenta");
            //list.add(PrintableString.emptyLine(1));
            //list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(28).setBold(true).build());
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());
            // Diferentes tamaños - subtitulo
            line = "Hora:" + ticket.getString("hora") + "  Fecha: " + ticket.getString("fecha");
            //list.add(PrintableString.emptyLine(1));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());
            // Diferentes tamaños - regular
            line = "Posicion: " + ticket.getString("posicion") + "  Turno: " + ticket.getString("turno");
            //list.add(PrintableString.emptyLine(1));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

            // Diferentes tamaños - pequeña
            line = "Transaccion:" +ticket.getString("folio");
            //list.add(PrintableString.emptyLine(1));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

            // Alineado a la izquierda
            line = "Forma de pago:" +ticket.getString("mop");
            //list.add(PrintableString.emptyLine(1));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

            line ="Recibo: " + ((ticket.getString("copias").equalsIgnoreCase("0")) ? ticket.getString("recibo") : ticket.getString("recibo")+"("+ticket.getString("copias")+")");
            //list.add(PrintableString.emptyLine(1));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

            // Alineado al centro
            line = "Folio Web: " + ticket.getString("folioWeb");
            //list.add(PrintableString.emptyLine(1));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

            line =  "Cant  Descripcion    P.U.   Importe";
            list.add(PrintableString.emptyLine(8));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(21).setAlign(PrintableAlign.LEFT).build());

            //printer.DLL_PrnStr("\n");
            for (int i=0;i<ticket.getJSONArray("items").length();i++) {
                JSONObject temp=ticket.getJSONArray("items").getJSONObject(i);
                System.out.println(String.valueOf(temp.getString("descripcion").length()));
                line=temp.getString("cantidad")
                        +espacio(temp.getString("cantidad").length(),6)
                        + temp.getString("descripcion").substring(0,(temp.getString("descripcion").length()<13)?temp.getString("descripcion").length():13)
                        +espacio((temp.getString("descripcion").length()<13)?temp.getString("descripcion").length():13,15)
                        +temp.getString("precio")
                        +espacio(temp.getString("precio").length(),8)
                        +temp.getString("importe");
                ImporteTotal += (Double.valueOf(temp.getString("importe")));
                //list.add(PrintableString.emptyLine(1));
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(21).setAlign(PrintableAlign.LEFT).build());

            }

            line="Total: " + Formatters.TwoDecimalsFormatWithDolarSign(ImporteTotal);
            list.add(PrintableString.emptyLine(7));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setBold(true).setAlign(PrintableAlign.LEFT).build());
            list.add(PrintableString.emptyLine(10));

            for(int i=0;i<ticket.getJSONArray("cuerpo").length();i++){
                line=ticket.getJSONArray("cuerpo").get(i).toString();
                //list.add(PrintableString.emptyLine(1));
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.CENTER).build());

            }

            list.add(PrintableString.emptyLine(10));

            for(int i=0;i<ticket.getJSONArray("pie").length();i++){
                line=ticket.getJSONArray("pie").get(i).toString();
                //list.add(PrintableString.emptyLine(1));
                list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(23).setAlign(PrintableAlign.LEFT).build());

            }
            
            //list.add(PrintableString.emptyLine(1));

            /*
            // Alineado a la derecha
            line = "Derecha";
            //list.add(PrintableString.emptyLine(1));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(21).setAlign(PrintableAlign.RIGHT).build());

            // Texto subrayado
            line = "Subrayado";
            //list.add(PrintableString.emptyLine(1));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(21).setAlign(PrintableAlign.LEFT).setUnderLine(true).build());

            // Texto en cursiva
            line = "Cursiva";
            //list.add(PrintableString.emptyLine(1));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(21).setAlign(PrintableAlign.LEFT).setItalic(true).build());

            // Tipo de fuente SANS_SERIF
            line = "Fuente SANS_SERIF";
            //list.add(PrintableString.emptyLine(1));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(21).setBold(false).build());

            // Tipo de fuente SANS SERIF
            line = "Fuente SANS SERIF";
            //list.add(PrintableString.emptyLine(1));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setFontSize(21).setBold(false).build());

            // Tipo de fuente SERIF
            line = "Fuente SERIF";
            //list.add(PrintableString.emptyLine(1));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SERIF).setFontSize(21).setBold(false).build());

            // Espacio de 10 puntos a la izquierda
            line = "Padeo a la izquierda";
            //list.add(PrintableString.emptyLine(1));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(21).setBold(false).setAlign(PrintableAlign.LEFT).setLeftOffset(20).build());

            // Espacio entre letras
            line = "Espaciado";
            //list.add(PrintableString.emptyLine(1));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(21).setBold(false).setAlign(PrintableAlign.LEFT).setLetterWidth(1.5F).build());

            // Código de Barras
            line = "1234567890";
            list.add(PrintableString.emptyLine(10));
            list.add(new PrintableBarCodeBase(line, PrintableBarCodeType.CODE_128, PrintableBarCodeWidth.LARGE, 50, 20));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(21).setBold(true).setAlign(PrintableAlign.CENTER).build());

            // QR
            list.add(PrintableString.emptyLine(10));
            list.add(new PrintableBarCodeBase(line, PrintableBarCodeType.QR_CODE, PrintableBarCodeWidth.LARGE, 400, 400));
            list.add(new PrintableStringExt.Builder().setContent(line).setFont(PrintableFont.SANS_SERIF).setBold(true).setFontSize(21).setBold(true).setAlign(PrintableAlign.CENTER).build());
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