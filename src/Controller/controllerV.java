/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Vehiculo;
import javax.swing.JTable;

/**
 *
 * @author Sakai
 */
public class controllerV {
        public void recibeTabla(JTable tabla, String celdaNumSerie){
        Vehiculo pV = new Vehiculo();
        pV.mostrarDatoNSerie(tabla, celdaNumSerie);
    }
}
