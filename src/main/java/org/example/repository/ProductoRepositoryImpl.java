package org.example.repository;

import org.example.models.Categoria;
import org.example.models.Producto;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositoryImpl implements Repository<Producto>{

    private Connection connection;

    public ProductoRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    // Listar
    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();

        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT p.* , c.nombre as categoria FROM productos " +
                    "as p inner join categorias as c ON (p.categoria_id = c.id)")){
            while (rs.next()){
                Producto p = crearProducto(rs);
                productos.add(p);
            }
            rs.close();
        }

        return productos;
    }

    // Consultar por id
    @Override
    public Producto porId(Long id) throws SQLException {

        Producto producto = new Producto();

        try(PreparedStatement stmt = connection.
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

        }
        return producto;
    }

    //insertar datos y actualizar datos
    @Override
    public Producto guardar(Producto producto) throws SQLException {

        String sql;
        if (producto.getId() != null  && producto.getId() > 0){
            sql = "UPDATE productos SET nombre = ?, precio = ?, categoria_id = ?, sku = ? WHERE id = ?";
        } else {
            sql = "INSERT INTO productos (nombre, precio, categoria_id, sku, fecha_registro) VALUES (?, ?, ?, ?, ?)";
        }

        try (PreparedStatement stmt  = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setLong(3, producto.getCategoria().getId());
            stmt.setString(4, producto.getSku());

            if (producto.getId() != null && producto.getId() > 0){
                stmt.setLong(5, producto.getId());
            } else {
                stmt.setDate(5, new Date(producto.getFechaRegistro().getTime()));
            }

            stmt.executeUpdate();

            if(producto.getId() == null){
                try(ResultSet rs = stmt.getGeneratedKeys()){
                    if(rs.next()){
                        producto.setId(rs.getLong(1));
                    }
                }
            }

            return producto;
        }

    }

    //eliminar datos
    @Override
    public void eliminar(Long id) throws SQLException {

        try(PreparedStatement stmt = connection.prepareStatement("DELETE FROM productos WHERE id = ?")){
            stmt.setLong(1, id);
            stmt.executeUpdate();

        }
    }

    private static Producto crearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getDouble("precio"));
        p.setFechaRegistro(rs.getDate("fecha_registro"));
        p.setSku(rs.getString("sku"));
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("categoria_id"));
        categoria.setNombre(rs.getString("categoria"));
        p.setCategoria(categoria);
        return p;
    }



}

