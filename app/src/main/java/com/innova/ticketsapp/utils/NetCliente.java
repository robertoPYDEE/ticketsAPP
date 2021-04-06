package com.innova.ticketsapp.utils;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class NetCliente {

    String out,modalidad;
    String autorizacion;
    String error="";
    boolean doGet=false;



    public NetCliente(){
    }

    public void envia(String a)throws Exception {

        try {
            URL url;
            //URL url = new URL(" https://www.api.facturama.com.mx/api-lite/cfdis");

                url = new URL("http://192.168.0.121:8080/ServiceFuelsoft-1.0/ultimaVenta?posicion="+a);



            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //String authString = "usuario:pruebas2011";
            //System.out.println(":::::::::::::::::::::::::::::::::::"+autorizacion);
            //String authString =autorizacion;
            //String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            //conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
            //conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "text/plain");
            //conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Type", "text/plain");


/*
            String input = a;


            OutputStream os = conn.getOutputStream();
            java.nio.charset.Charset.forName("UTF-8").encode(input);
            os.write(input.getBytes("UTF-8"));
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                error=conn.getResponseCode()+" --- "+getStringFromInputStream(conn.getErrorStream());
                log.error(conn.getResponseCode()+" --- "+error);
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode()+" --- "+error);


            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                out=output;
                System.out.println(output);


            }*/
            if (conn.getResponseCode() != 200) {
                doGet=false;
                throw new RuntimeException("Fallo : HTTP error code : "
                        + conn.getResponseCode());

            }else{
                doGet=true;
            }

            InputStream stream = conn.getInputStream();
            java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
            out = s.hasNext() ? s.next() : "";
            Log.i("info","Output from Server .... \n"+out);

            conn.disconnect();

        } catch (java.net.SocketTimeoutException e) {
            e.printStackTrace();
            error=e.toString();
            //throw new Exception("Error al Enviar peticion: " + e.toString());

        } catch (MalformedURLException e) {

            e.printStackTrace();
            error=e.toString();
            //throw new Exception("Error al Enviar peticion: " + e.toString());

        } catch (IOException e) {

            e.printStackTrace();
            error=e.toString();
           // throw new Exception("Error al Enviar peticion: " + e.toString());
        }

    }

    public String getRespuesta(){
        return out;
    }

    public void setUsuario(String u, String p, String m){
        autorizacion=u+":"+p;
        modalidad=m;
    }

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

    public String getError(){
        return error;
    }
}

