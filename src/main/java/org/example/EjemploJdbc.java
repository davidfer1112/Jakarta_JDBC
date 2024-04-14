package org.example;

import org.example.models.Categoria;
import org.example.models.Producto;
import org.example.repository.ProductoRepositoryImpl;
import org.example.repository.CategoriaRepositoryImp;
import org.example.repository.Repository;
import org.example.service.CatalogoServicio;
import org.example.service.Service;
import org.example.util.ConexionBaseDatos;

import java.util.Date;

import java.sql.*;


public class EjemploJdbc {
    public static void main(String[] args) throws SQLException {

        Service service = new CatalogoServicio();

        System.out.println("\nListado de productos:");
        service.listar().forEach(System.out::println);
        System.out.println("Insertando una nueva categoria");
        Categoria categoria = new Categoria();
        categoria.setNombre("Gamer");


        // Se inserta un nuevo producto
        System.out.println("\nInsertando un nuevo producto");
        Producto producto = new Producto();
        producto.setNombre("Laptop Gamer");
        producto.setPrecio(450.0);
        producto.setFechaRegistro(new Date());
        producto.setSku("LPG-001");
        service.guardarProductoConCategoria(producto, categoria);
        System.out.println("Producto guardado con exito: " + producto.getId());

        // Se imprime la lista de productos
        System.out.println("\nListado de productos:");
        service.listar().forEach(System.out::println);

    }
}
