/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Sakai
 */
public class Detallevref {
    
    int iddetallevref = 0;
    int idfacturavref = 0;
    String codRefaccion = "";
    double precio = 0.0;
    int cantidadref = 0;
    

    public int getIddetallevref() {
        return iddetallevref;
    }

    public void setIddetallevref(int iddetallevref) {
        this.iddetallevref = iddetallevref;
    }

    public int getIdfacturavref() {
        return idfacturavref;
    }

    public void setIdfacturavref(int idfacturavref) {
        this.idfacturavref = idfacturavref;
    }

    public String getCodRefaccion() {
        return codRefaccion;
    }

    public void setCodRefaccion(String codRefaccion) {
        this.codRefaccion = codRefaccion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidadref() {
        return cantidadref;
    }

    public void setCantidadref(int cantidadref) {
        this.cantidadref = cantidadref;
    }
String fechavref = "", nombre = "", apellido = "", descripcion = "";
Double subtotal = 0.0, total = 0.0;

    public String getFechavref() {
        return fechavref;
    }

    public void setFechavref(String fechavref) {
        this.fechavref = fechavref;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    
}
