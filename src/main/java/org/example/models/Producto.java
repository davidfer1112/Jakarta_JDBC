package org.example.models;

import java.util.Date;

public class Producto {
    private Long id;
    private String nombre;
    private Double precio;
    private Date fechaRegistro;
    private Categoria categoria;
    private String sku;

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return  id +
                " | " +
                nombre +
                " | " +
                precio +
                " | " +
                fechaRegistro+
                " | " +
                categoria.getNombre()+
                " | " +
                sku;
    }

    public Producto() {
    }

    public Producto(Long id, String nombre, Double precio, Date fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.fechaRegistro = fechaRegistro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
