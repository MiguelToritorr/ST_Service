/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javax.swing.JTable;

/**
 *
 * @author Sakai
 */
public class Refacciones {
    String codRefaccion, categoria, descripcion;
    double preciopublico, preciomayoreo;
    String vcompatibles;
    int stock;

    public String getCodRefaccion() {
        return codRefaccion;
    }

    public void setCodRefaccion(String codRefaccion) {
        this.codRefaccion = codRefaccion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPreciopublico() {
        return preciopublico;
    }

    public void setPreciopublico(double preciopublico) {
        this.preciopublico = preciopublico;
    }

    public double getPreciomayoreo() {
        return preciomayoreo;
    }

    public void setPreciomayoreo(double preciomayoreo) {
        this.preciomayoreo = preciomayoreo;
    }

    public String getVcompatibles() {
        return vcompatibles;
    }

    public void setVcompatibles(String vcompatibles) {
        this.vcompatibles = vcompatibles;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
        public static String celdaCodRef="";
    public void mostrarDatorfc(JTable tabla, String celdaCodRef){
       try{
        int i = tabla.getSelectedRow();
        celdaCodRef = (String) tabla.getValueAt(i, 0);
       }catch(Exception e){}
    }
}
