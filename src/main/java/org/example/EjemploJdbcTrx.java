package org.example;

import org.example.models.Categoria;
import org.example.models.Producto;
import org.example.repository.ProductoRepositoryImpl;
import org.example.repository.Repository;
import org.example.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;


public class EjemploJdbcTrx {
    public static void main(String[] args) throws SQLException{

        try (Connection connection = ConexionBaseDatos.getInstance()){
            if (connection.getAutoCommit()){
                connection.setAutoCommit(false);
            }
            try {
                Repository<Producto> repository = new ProductoRepositoryImpl();
                System.out.println("\nListado de productos:");
                repository.listar().forEach(System.out::println);

                // Se imprime el producto con id 2 (La L es para indicar que es un Long)
                System.out.println("\nConsultando el producto con id 2:");
                System.out.println(repository.porId(2L));

                // Se inserta un nuevo producto
                System.out.println("\nInsertando un nuevo producto");
                Producto producto = new Producto();
                producto.setNombre("Spring Framework");
                producto.setPrecio(310.0);
                producto.setFechaRegistro(new Date());
                Categoria categoria = new Categoria();
                categoria.setId(3L);
                producto.setCategoria(categoria);
                producto.setSku("abcde12345");
                repository.guardar(producto);
                System.out.println("Producto guardado con exito");

                // Se edita un nuevo producto
                System.out.println("\nEditando un producto producto");
                 producto = new Producto();
                producto.setId(5L);
                producto.setNombre("Jakarta (Java EE)");
                producto.setPrecio(400.0);
                producto.setSku("abcd123456");
                categoria = new Categoria();
                categoria.setId(2L);
                producto.setCategoria(categoria);
                repository.guardar(producto);
                System.out.println("Producto actualizo con exito");

                // Se imprime la lista de productos
                System.out.println("\nListado de productos:");
                repository.listar().forEach(System.out::println);

                connection.commit();
            }catch (SQLException e){
                e.printStackTrace();
                connection.rollback();
            }
        }
    }
}
