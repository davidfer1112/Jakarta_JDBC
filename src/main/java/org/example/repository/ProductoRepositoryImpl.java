package org.example.repository;

import org.example.models.Categoria;
import org.example.models.Producto;
import org.example.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositoryImpl implements Repository<Producto>{

    private Connection getConnection() throws SQLException {
        return ConexionBaseDatos.getInstance();
    }

    // Listar
    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();

        try(Connection connection = getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT p.* , c.nombre as categoria FROM productos " +
                    "as p inner join categorias as c ON (p.categoria_id = c.id)")){
            while (rs.next()){
                Producto p = crearProducto(rs);
                productos.add(p);
            }
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return productos;
    }

    // Consultar por id
    @Override
    public Producto porId(Long id) {

        Producto producto = new Producto();

        try(Connection connection = getConnection();
            PreparedStatement stmt = connection.
                prepareStatement("SELECT p.*, c.nombre as categoria FROM productos as p" +
                        " inner join  categorias  as c ON (p.categoria_id = c.id) WHERE p.id = ?")){

                // El primer parametron indica que modificara el indice uno es decir la columna 1 (id)
                // y el segundo parametro es el valor que se le asignara al indice 1
                stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery();) {
                if(rs.next()){
                    producto = crearProducto(rs);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return producto;
    }

    //insertar datos y actualizar datos
    @Override
    public void guardar(Producto producto) {

        String sql;
        if (producto.getId() != null  && producto.getId() > 0){
            sql = "UPDATE productos SET nombre = ?, precio = ?, categoria_id = ? WHERE id = ?";
        } else {
            sql = "INSERT INTO productos (nombre, precio, categoria_id ,fecha_registro) VALUES (?, ?, ?, ?)";
        }

        try (Connection connection = getConnection();
             PreparedStatement stmt  = connection.prepareStatement(sql)){
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setLong(3, producto.getCategoria().getId());

            if (producto.getId() != null && producto.getId() > 0){
                stmt.setLong(4, producto.getId());
            } else {
                stmt.setDate(4, new Date(producto.getFechaRegistro().getTime()));
            }


            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    //eliminar datos
    @Override
    public void eliminar(Long id) {

        try(Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM productos WHERE id = ?")){
            stmt.setLong(1, id);
            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static Producto crearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getDouble("precio"));
        p.setFechaRegistro(rs.getDate("fecha_registro"));
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("categoria_id"));
        categoria.setNombre(rs.getString("categoria"));
        p.setCategoria(categoria);
        return p;
    }



}

