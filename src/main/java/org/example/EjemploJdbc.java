package org.example;
import org.example.models.Categoria;
import org.example.models.Producto;
import org.example.repository.ProductoRepositoryImpl;
import org.example.repository.Repository;
import org.example.util.ConexionBaseDatos;
import  java.util.Date;

import java.sql.*;


public class EjemploJdbc {
    public static void main(String[] args) {

        try (Connection connection = ConexionBaseDatos.getInstance()){

            Repository<Producto> repository = new ProductoRepositoryImpl();
            System.out.println("\nListado de productos:");
            repository.listar().forEach(System.out::println);

            // Se imprime el producto con id 2 (La L es para indicar que es un Long)
            System.out.println("\nConsultando el producto con id 2:");
            System.out.println(repository.porId(2L));

            // Se inserta un nuevo producto
            System.out.println("\nInsertando un nuevo producto");
            Producto producto = new Producto();
            producto.setNombre("Jakarta EE");
            producto.setPrecio(310.0);
            producto.setFechaRegistro(new Date());
            Categoria categoria = new Categoria();
            categoria.setId(3L);
            producto.setCategoria(categoria);
            repository.guardar(producto);
            System.out.println("Producto guardado con exito");

            // Se imprime la lista de productos
            System.out.println("\nListado de productos:");
            repository.listar().forEach(System.out::println);


        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
