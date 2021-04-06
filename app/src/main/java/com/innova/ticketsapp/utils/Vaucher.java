package com.innova.ticketsapp.utils;

import android.graphics.BitmapFactory;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mit.com.mpos.model.intelipos.business.print.MitVaucherLine;


public class Vaucher  {


    List<MitVaucherLine> vaucherLines;
    MitVaucherLine vaucherLine;
    JSONObject ticket;
    private Double ImporteTotal=0.0;


    public List<MitVaucherLine> getTicket(JSONObject ticket,String tipo){

        this.ticket=ticket;
        vaucherLine=new MitVaucherLine();
        vaucherLines= new ArrayList<MitVaucherLine>();


        //vaucherLine.setText("");

        try{
            for(int i=0;i<ticket.getJSONArray("encabezado").length();i++){
                String line = ticket.getJSONArray("encabezado").get(i).toString();

                vaucherLine.setText(line);
                vaucherLine.setBold(true);
                vaucherLine.setSize(2);
                vaucherLines.add(vaucherLine);
            }

            vaucherLines.add(new MitVaucherLine());

            String line = "Cuenta:" +ticket.getString("cuenta");
            vaucherLine.setText(line);
            vaucherLine.setBold(true);
            vaucherLine.setSize(1);
            vaucherLines.add(vaucherLine);

            line = "Subcuenta:" +ticket.getString("subcuenta");
            vaucherLine.setText(line);
            vaucherLine.setBold(true);
            vaucherLine.setSize(1);
            vaucherLines.add(vaucherLine);

            line = "Hora:" + ticket.getString("hora") + "  Fecha: " + ticket.getString("fecha");
            vaucherLine.setText(line);
            vaucherLine.setBold(true);
            vaucherLine.setSize(1);
            vaucherLines.add(vaucherLine);

            line = "Posicion: " + ticket.getString("posicion") + "  Turno: " + ticket.getString("turno");
            vaucherLine.setText(line);
            vaucherLine.setBold(true);
            vaucherLine.setSize(1);
            vaucherLines.add(vaucherLine);

            line = "Transaccion:" +ticket.getString("folio");
            vaucherLine.setText(line);
            vaucherLine.setBold(true);
            vaucherLine.setSize(1);
            vaucherLines.add(vaucherLine);

            line = "Forma de pago:" +ticket.getString("mop");
            vaucherLine.setText(line);
            vaucherLine.setBold(true);
            vaucherLine.setSize(1);
            vaucherLines.add(vaucherLine);

            line ="Recibo: " + ((ticket.getString("copias").equalsIgnoreCase("0")) ? ticket.getString("recibo") : ticket.getString("recibo")+"("+ticket.getString("copias")+")");
            vaucherLine.setText(line);
            vaucherLine.setBold(true);
            vaucherLine.setSize(1);
            vaucherLines.add(vaucherLine);

            line = "Folio Web: " + ticket.getString("folioWeb");
            vaucherLine.setText(line);
            vaucherLine.setBold(true);
            vaucherLine.setSize(1);
            vaucherLines.add(vaucherLine);

            line =  "Cant  Descripcion    P.U.   Importe";
            vaucherLine.setText(line);
            vaucherLine.setBold(true);
            vaucherLine.setSize(1);
            vaucherLines.add(vaucherLine);

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
                vaucherLine.setText(line);
                vaucherLine.setBold(true);
                vaucherLine.setSize(1);
                vaucherLines.add(vaucherLine);
            }



        }catch(Exception e){
            e.printStackTrace();
        }


        //vaucherLines.add(vaucherLine);
        return vaucherLines;
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
