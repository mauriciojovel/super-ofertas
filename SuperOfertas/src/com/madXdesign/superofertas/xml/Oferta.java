package com.madXdesign.superofertas.xml;

import java.io.Serializable;
import java.util.Date;

public class Oferta implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String titulo;
    private String presentacion;
    private String imagen;
    private double precionNormal;
    private double ahorro;
    private double precioOferta;
    private double descuento;
    private Date fechaPublicacion;
    private Date fechaVencimiento;
    
    public Oferta() {}
    
    
    
    public Oferta(String titulo, String presentacion, String imagen,
            double precionNormal, double ahorro, double precioOferta,
            double descuento, Date fechaPublicacion, Date fechaVencimiento)
    {
        super();
        this.titulo = titulo;
        this.presentacion = presentacion;
        this.imagen = imagen;
        this.precionNormal = precionNormal;
        this.ahorro = ahorro;
        this.precioOferta = precioOferta;
        this.descuento = descuento;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getPresentacion() {
        return presentacion;
    }
    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }
    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    public double getPrecionNormal() {
        return precionNormal;
    }
    public void setPrecionNormal(double precionNormal) {
        this.precionNormal = precionNormal;
    }
    public double getAhorro() {
        return ahorro;
    }
    public void setAhorro(double ahorro) {
        this.ahorro = ahorro;
    }
    public double getPrecioOferta() {
        return precioOferta;
    }
    public void setPrecioOferta(double precioOferta) {
        this.precioOferta = precioOferta;
    }
    public double getDescuento() {
        return descuento;
    }
    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }
    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    
    public int getPorcentajeDescuento() {
        if(precionNormal != 0) {
            return (int)((1-(precioOferta/precionNormal))*100);
        } else {
            return 0;
        }
    }
    
    @Override
    public String toString() {
        return titulo+" "+precioOferta+" "+" "+precionNormal
                +" "+ahorro+" "+getPorcentajeDescuento()+"%";
    }
}
