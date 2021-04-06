package com.innova.ticketsapp.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ClienteFormaPago {
     private  boolean conectado;
     String error;
    private JSONObject formaspago;
    private String out;


    public ClienteFormaPago(){}

    public void doPost() {

        try {
            URL url;

            //url = new URL("http://192.168.0.121:8080/apiterminal/tiketventa");
            url = new URL("http://192.168.0.117:8080/apiterminal/FormasDePago");


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //String authString = "usuario:pruebas2011";
            //System.out.println(":::::::::::::::::::::::::::::::::::"+autorizacion);
			/*String authString ="pruebas:pruebas2011";
			String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
			conn.setRequestProperty("Authorization", "Basic " + authStringEnc);*/
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Content-Type", "text/plain");

            String input = "{\"tipo\":\"ticket\"}";

            //String input="{\"PaymentAccountNumber\": \"5143\",\"Currency\": \"MXN\",\"Folio\": \"1000\",\"CfdiType\": \"i\",\"PaymentMethod\": \"01\",\"Issuer\": {\"TaxAddress\": {\"Street\": \"Canada de Gomez\",\"ExteriorNumber\": \"115\",\"Neighborhood\": \"Lomas 4ta\",\"ZipCode\": \"12345\",\"Municipality\": \"SAN LUIS POTOSI\",\"State\": \"SAN LUIS POTOSI\",\"Country\": \"MEXICO\"},\"FiscalRegime\": \"Regimen de Incorporacion Fiscal\",\"Rfc\": \"AAA010101AAA\",\"Name\": \"Expresion en Software SAPI de CV\"},\"Receiver\": {\"Rfc\": \"XAXX010101000\",\"Name\": \"Fulano\"},\"Items\": [{\"Description\": \"110 Folios\",\"Unit\": \"SERVICIO\",\"UnitPrice\": 1.0,\"Quantity\": 2.0,\"Subtotal\": 100.0,\"Discount\": 10.0,\"Taxes\": [{\"Total\": 16.0,\"Name\": \"IVA\",\"Rate\": 16.0}],\"Total\": 116.0}]}";
            /*
            org.json.JSONObject obj = new org.json.JSONObject();
            try {
                obj.put("id", "3");
                obj.put("name", "NAME OF STUDENT");
                obj.put("year", "3rd");
                obj.put("curriculum", "Arts");
                obj.put("birthday", "5/5/1993");
            } catch (org.json.JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            */



            OutputStream os = conn.getOutputStream();
            java.nio.charset.Charset.forName("UTF-8").encode(input);
            //os.write(input.getBytes("UTF-8"));
            os.write(input.getBytes("UTF-8"));
            os.flush();

            if (conn.getResponseCode() != 200) {
                Log.e("ERROR", String.valueOf(conn.getResponseCode()));
                conectado=false;
                error=conn.getResponseCode()+" --- "+getStringFromInputStream(conn.getErrorStream());
                Log.e("ERROR",conn.getResponseCode()+" --- "+error);
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            Log.i("info","Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                out=output;

            }
            formaspago = new JSONObject(out);
            conectado=true;
            Log.i("info",formaspago.toString());

/*
            if (conn.getResponseCode() != 200) {
                conectado = false;
                result = "Fallo : HTTP error code : "
                        + conn.getResponseCode();

            } else {
                conectado = true;
            }
            InputStream stream = conn.getInputStream();
            java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
            result = s.hasNext() ? s.next() : "";
            ticket = new JSONObject(result);
            Log.i("info", "Output from Server .... \n" + result);

*/

            conn.disconnect();

        } catch (MalformedURLException e) {
            conectado = false;
            e.printStackTrace();

        } catch (IOException e) {
            conectado = false;
            e.printStackTrace();

        }catch (JSONException e) {
            conectado = false;
            e.printStackTrace();

        }
    }

    /*
    private JSONObject crearJSON(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("posicion", (pos.getSelectedItemPosition() + 1));
            obj.put("mop", mop.getSelectedItem().toString());
            obj.put("nip", 0);
            JSONArray items=new JSONArray();
            if(AceitesIm.size()>0){
                for(int i=0;i<AceitesIm.size();i++){
                    JSONObject objA = new JSONObject();
                    Vector temp=(Vector)AceitesIm.get(i);
                    objA.put("id",temp.get(0).toString());
                    objA.put("cantidad",Double.valueOf(temp.get(1).toString()));
                    items.put(objA);
                }
            }
            obj.put("items",items);
            //obj.put("birthday", "5/5/1993");
        } catch (org.json.JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.e("info",obj.toString());
        return obj;
    }

*/

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
    public String getRespuesta(){
        return out;
    }

    public JSONObject getJSON(){
        return formaspago;
    }

}
