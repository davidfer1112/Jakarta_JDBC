package org.example;
import org.example.models.Categoria;
import org.example.models.Producto;
import org.example.repository.ProductoRepositoryImpl;
import org.example.repository.CategoriaRepositoryImp;
import org.example.repository.Repository;
import org.example.util.ConexionBaseDatos;
import  java.util.Date;

import java.sql.*;


public class EjemploJdbc {
    public static void main(String[] args) throws SQLException {

            try(Connection connection = ConexionBaseDatos.getConnection()) {
                    if (connection.getAutoCommit()) {
                        connection.setAutoCommit(false);
                    }
                    try {
                            Repository<Categoria> repositoryCategoria = new CategoriaRepositoryImp(connection);
                            System.out.println("Insertando una nueva categoria");
                            Categoria categoria = new Categoria();
                            categoria.setNombre("Hogar");
                            Categoria nuevaCategoria = repositoryCategoria.guardar(categoria);
                            System.out.println("Categoria guardada con exito: " + nuevaCategoria.getId());

                            Repository<Producto> repository = new ProductoRepositoryImpl(connection);
                            System.out.println("\nListado de productos:");
                            repository.listar().forEach(System.out::println);

                            // Se imprime el producto con id 2 (La L es para indicar que es un Long)
                            System.out.println("\nConsultando el producto con id 2:");
                            System.out.println(repository.porId(2L));

                            // Se inserta un nuevo producto
                            System.out.println("\nInsertando un nuevo producto");
                            Producto producto = new Producto();
                            producto.setNombre("Microondas");
                            producto.setPrecio(350.0);
                            producto.setFechaRegistro(new Date());
                            producto.setSku("abcdef123");
                            producto.setCategoria(nuevaCategoria);
                            repository.guardar(producto);
                            System.out.println("Producto guardado con exito: " + producto.getId());

                            // Se imprime la lista de productos
                            System.out.println("\nListado de productos:");
                            repository.listar().forEach(System.out::println);

                            connection.commit();
                    }catch (SQLException e){
                        connection.rollback();
                        e.printStackTrace();
                    }
            }

    }
}
