/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.*;
import static Models.Personas.celdaRfc;
import Views.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Sakai
 */
public class controllerP {

    public void recibeTabla(JTable tabla, String celdaRfc){
        Personas pV = new Personas();
        pV.mostrarDatorfc(tabla, celdaRfc);
    }
    
    }
    

    

