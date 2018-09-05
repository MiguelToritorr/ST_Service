/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Date;

/**
 *
 * @author Sakai
 */
public class Facturavref {
    
    int idfacturavref = 0;
    Date fechavref;
    String rfc = "";

    public int getIdfacturavref() {
        return idfacturavref;
    }

    public void setIdfacturavref(int idfacturavref) {
        this.idfacturavref = idfacturavref;
    }

    public Date getFechavref() {
        return fechavref;
    }

    public void setFechavref(Date fechavref) {
        this.fechavref = fechavref;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
    
    
}
