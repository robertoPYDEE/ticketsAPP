package com.innova.ticketsapp.utils;

import java.math.BigDecimal;

public class Aceites {

    private Long id;

    private String transaccion;

    private String cuenta;

    private String subcuenta;

    private String codigoFabrica;

    private String producto;

    private BigDecimal precioUnitario;

    private Integer cantidad=0;

    private BigDecimal total;


    private String error = "";


    public Aceites() {


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
}




