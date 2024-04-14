package org.example.service;

import org.example.models.Categoria;
import org.example.models.Producto;
import org.example.repository.Repository;
import org.example.repository.ProductoRepositoryImpl;
import org.example.repository.CategoriaRepositoryImp;
import org.example.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CatalogoServicio implements Service{

    private Repository<Producto> productoRepository;
    private Repository<Categoria> categoriaRepository;

    public CatalogoServicio(){
        this.productoRepository = new ProductoRepositoryImpl();
        this.categoriaRepository = new CategoriaRepositoryImp();
    }

    @Override
    public List<Producto> listar() throws SQLException {
        try (Connection connection = ConexionBaseDatos.getConnection()){
            productoRepository.setConnection(connection);
            return productoRepository.listar();
        }
    }

    @Override
    public Producto porId(Long id) throws SQLException {
        try (Connection connection = ConexionBaseDatos.getConnection()){
            productoRepository.setConnection(connection);
            return productoRepository.porId(id);
        }
    }

    @Override
    public Producto guardar(Producto producto) throws SQLException {
        try (Connection connection = ConexionBaseDatos.getConnection()){
            productoRepository.setConnection(connection);
            if(connection.getAutoCommit()){
                connection.setAutoCommit(false);
            }
            Producto nuevoProducto = null;
            try {
                nuevoProducto = productoRepository.guardar(producto);
                connection.commit();
            }catch (SQLException e){
                connection.rollback();
                e.printStackTrace();
            }
            return nuevoProducto;
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try (Connection connection = ConexionBaseDatos.getConnection()){
            productoRepository.setConnection(connection);
            if(connection.getAutoCommit()){
                connection.setAutoCommit(false);
            }
            try {
                productoRepository.eliminar(id);
                connection.commit();
            }catch (SQLException e){
                connection.rollback();
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<Categoria> listarCategorias() throws SQLException {
        try (Connection connection = ConexionBaseDatos.getConnection()){
            categoriaRepository.setConnection(connection);
            return categoriaRepository.listar();
        }
    }

    @Override
    public Categoria porIdCategoria(Long id) throws SQLException {
        try (Connection connection = ConexionBaseDatos.getConnection()){
            categoriaRepository.setConnection(connection);
            return categoriaRepository.porId(id);
        }
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) throws SQLException {
        try (Connection connection = ConexionBaseDatos.getConnection()){
            categoriaRepository.setConnection(connection);
            if(connection.getAutoCommit()){
                connection.setAutoCommit(false);
            }
            Categoria nuevaCategoria = null;
            try {
                nuevaCategoria = categoriaRepository.guardar(categoria);
                connection.commit();
            }catch (SQLException e){
                connection.rollback();
                e.printStackTrace();
            }
            return nuevaCategoria;
        }
    }

    @Override
    public void eliminarCategoria(Long id) throws SQLException {
        try (Connection connection = ConexionBaseDatos.getConnection()){
            categoriaRepository.setConnection(connection);
            if(connection.getAutoCommit()){
                connection.setAutoCommit(false);
            }
            try {
                categoriaRepository.eliminar(id);
                connection.commit();
            }catch (SQLException e){
                connection.rollback();
                e.printStackTrace();
            }
        }

    }

    @Override
    public void guardarProductoConCategoria(Producto producto, Categoria categoria) throws SQLException {
        try (Connection connection = ConexionBaseDatos.getConnection()){
            productoRepository.setConnection(connection);
            categoriaRepository.setConnection(connection);
            if(connection.getAutoCommit()){
                connection.setAutoCommit(false);
            }
            try {
                Categoria nuevaCategoria = categoriaRepository.guardar(categoria);
                producto.setCategoria(nuevaCategoria);
                productoRepository.guardar(producto);
                connection.commit();
            }catch (SQLException e){
                connection.rollback();
                e.printStackTrace();
            }
        }
    }
}
