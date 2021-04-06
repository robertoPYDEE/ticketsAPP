package com.innova.ticketsapp.utils;

import android.database.SQLException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AceitesBD implements Runnable {

    private Long id;

    private String transaccion;

    private String cuenta;

    private String subcuenta;

    private String codigoFabrica;

    private String producto;

    private BigDecimal precioUnitario;

    private Integer cantidad;

    private BigDecimal total;

    private String version = "-1";
    private String error = "";
    private String pc= "-1";
    private String ult_ven_lit= "-1";
    private String ult_ven_din= "-1";

    private String hora_vta= "-1";
    private String fecha_vta= "-1";
    private String folio= "-1";
    private String mop= "-1";
    private String impreso= "-1";
    private String pu= "-1";
    private String turno= "-1";

    private String parametro= "";

    private String url;
    private String usuario;
    private String password;
    Connection conexion;

    public AceitesBD(String pm) {
        url = "192.168.100.50";
        usuario = "root";
        password = "1973";
        parametro=pm;
    }

    public AceitesBD(String urlBaseDatos, String usuarioBAseDatos, String passwordBaseDatos) {
        url = urlBaseDatos;
        usuario = usuarioBAseDatos;
        password = passwordBaseDatos;
    }

    @Override
    public void run() {
        try {
            String sql = "SELECT producto,precio_venta, codigo_fabrica from cata_aceites where codigo_fabrica='"+parametro+"' ";
            Class.forName("com.mysql.jdbc.Driver");

            String cadenaConexion = "jdbc:mysql://" + url + ":3306/baseserver";

            conexion = DriverManager.getConnection(cadenaConexion, usuario, password);
            Statement consulta = conexion.createStatement();
            ResultSet data = consulta.executeQuery(sql);
            data.next();

            //setVersion(data.getString(1));

            if(data.getRow()>0){
            setProducto(data.getString(1));
            setPrecioUnitario(data.getString(2));
            setCodigoFabrica(data.getString(3));
            setCuenta("");
            setSubcuenta("");
            setCantidad("1");

            setTransaccion("0");
            setTotal();
            }else{
                this.setError("NO SE ENCONTRO CODIGO DEN BD PARAMETRO: "+parametro+" ***"+sql);
            }


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

    public String getError() {

        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the transaccion
     */
    public String getTransaccion() {
        return transaccion;
    }

    /**
     * @param transaccion the transaccion to set
     */
    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    /**
     * @return the cuenta
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta the cuenta to set
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * @return the subcuenta
     */
    public String getSubcuenta() {
        return subcuenta;
    }

    /**
     * @param subcuenta the subcuenta to set
     */
    public void setSubcuenta(String subcuenta) {
        this.subcuenta = subcuenta;
    }

    /**
     * @return the codigoFabrica
     */
    public String getCodigoFabrica() {
        return codigoFabrica;
    }

    /**
     * @param codigoFabrica the codigoFabrica to set
     */
    public void setCodigoFabrica(String codigoFabrica) {
        this.codigoFabrica = codigoFabrica;
    }

    /**
     * @return the producto
     */
    public String getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(String producto) {
        this.producto = producto;
    }

    /**
     * @return the precioUnitario
     */
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * @param precioUnitario the precioUnitario to set
     */
    public void setPrecioUnitario(String precioUnitario) {
        this.precioUnitario = BigDecimal.valueOf(Double.valueOf(precioUnitario));
    }

    /**
     * @return the cantidad
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(String cantidad) {
        this.cantidad = Integer.valueOf(cantidad);
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }


    public void setTotal() {
        BigDecimal c = new BigDecimal(cantidad);
        this.total = precioUnitario.multiply(c);
    }




    public void setCodigo(String c) {
       this.parametro=c;
    }


}
