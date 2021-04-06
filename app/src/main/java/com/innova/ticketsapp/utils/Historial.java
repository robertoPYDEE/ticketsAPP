package com.innova.ticketsapp.utils;


import android.database.SQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Historial implements Runnable {
    private String version = "-1";
    private String error = "";
    private String pc= "-1";
    private String ult_ven_lit= "-1";
    private String ult_ven_din= "-1";
    private String producto= "-1";
    private String hora_vta= "-1";
    private String fecha_vta= "-1";
    private String folio= "-1";
    private String mop= "-1";
    private String impreso= "-1";
    private String pu= "-1";
    private String turno= "-1";
    private String referencia= "-1";
    private String isla= "-1";

    private String parametro= "";

    private String url;
    private String usuario;
    private String password;

    public Historial(String pm) {
        url = "192.168.100.50";
        usuario = "root";
        password = "1973";
        parametro=pm;
    }

    public Historial(String urlBaseDatos, String pm) {
        url = urlBaseDatos;
        usuario = "tecnico";
        password = "vic1971";
        parametro=pm;
    }

    @Override
    public void run() {
        try {
            String sql = "SELECT pc, ult_ven_lit, ult_ven_din, producto, hora_vta, fecha_vta, folio, mop, impreso, pu,turno,referencia,isla from pc_historial where pc='"+parametro+"' order by horafecha desc limit 1";
            Class.forName("com.mysql.jdbc.Driver");

            String cadenaConexion = "jdbc:mysql://" + url + ":3306/FUELSOFT?characterEncoding=latin1&useConfigs=maxPerformance";

            Connection conexion = DriverManager.getConnection(cadenaConexion, usuario, password);
            Statement consulta = conexion.createStatement();
            ResultSet data = consulta.executeQuery(sql);
            data.next();

            //setVersion(data.getString(1));
            setPC(data.getString(1));
            setVolumen(data.getString(2));
            setImporte(data.getString(3));
            setProducto(data.getString(4));
            setHora(data.getString(5));
            setFecha(data.getString(6));
            setFolio(data.getString(7));
            setMOP(data.getString(8));
            setImpreso(data.getString(9));
            setPrecio(data.getString(10));
            setTurno(data.getString(11));
            setReferencia(data.getString(12));
            setIsla(data.getString(13));


            data.close();
            consulta.close();
            conexion.close();

        } catch (SQLException | java.sql.SQLException se) {
            System.err.println(se);
            this.setError("SQLEXCEPTION"+se.getMessage());
        } catch (ClassNotFoundException e) {
            this.setError("CLASSNOTFOUND");
        }
    }

    public String getVersion() {

        return version;
    }

    public void setVersion(String version) {

        this.version = version;
    }

    public String getError() {

        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setPC(String PC) {
        this.pc = PC;
    }

    public String getPC() {

        return pc;
    }

    public void setVolumen(String vol) {

        this.ult_ven_lit = vol;
    }
    public String getVolumen() {

        return ult_ven_lit;
    }

    public void setImporte(String imp) {

        this.ult_ven_din = imp;
    }
    public String getImporte() {

        return ult_ven_din;
    }

    public void setProducto(String prod) {

        this.producto = prod;
    }
    public String getProducto() {

        return producto;
    }

    public void setFecha(String f) {

        this.fecha_vta = f;
    }
    public String getFecha() {

        return fecha_vta;
    }

    public void setHora(String h) {

        this.hora_vta = h;
    }
    public String getHora() {

        return hora_vta;
    }

    public void setFolio(String fo) {

        this.folio = fo;
    }
    public String getFolio() {

        return folio;
    }

    public void setImpreso(String im) {

        this.impreso = im;
    }
    public String getImpreso() {

        return impreso;
    }

    public void setMOP(String mp) {

        this.mop = mp;
    }
    public String getMOP() {

        return mop;
    }

    public void setPrecio(String pr) {

        this.pu = pr;
    }
    public String getPrecio() {

        return pu;
    }

    public void setTurno(String tu) {

        this.turno = tu;
    }
    public String getTurno() {

        return turno;
    }
    public void setIsla(String is) {

        this.isla = is;
    }
    public String getIsla() {

        return isla;
    }
    public void setReferencia(String re) {

        this.referencia = re;
    }
    public String getReferencia() {

        return referencia;
    }
}
