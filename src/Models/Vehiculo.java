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
public class Vehiculo {
    String numserie, color = "";
    int anio = 0;
    String tipo, rfc = "";

    public String getNumserie() {
        return numserie;
    }

    public void setNumserie(String numserie) {
        this.numserie = numserie;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
    
        public static String celdaNumSerie="";
    public void mostrarDatoNSerie(JTable tabla, String celdaNumSerie){
       try{
        int i = tabla.getSelectedRow();
        celdaNumSerie = (String) tabla.getValueAt(i, 0);
       }catch(Exception e){}
    }
    
}
