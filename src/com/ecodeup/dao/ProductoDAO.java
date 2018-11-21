package com.ecodeup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ecodeup.conexion.Conexion;
import com.ecodeup.model.Producto;

public class ProductoDAO {
	
	private Connection connection;
	private PreparedStatement statement;
	private boolean estadoOperacion;
	
	//guardar video 26
	public boolean guardar(Producto producto) throws SQLException {
		String sql=null;
		estadoOperacion=false;
		connection=obtenerConexion();
		
		try {
			connection.setAutoCommit(false);
			sql="INSERT INTO productos(id,nombre,cantidad,precio,fecha_crear,fecha_actualizar) VALUES(?,?,?,?,?,?)";
			statement=connection.prepareStatement(sql);
			
			statement.setString(1,null);
			statement.setString(2,producto.getNombre());
			statement.setDouble(3,producto.getCantidad());
			statement.setDouble(4,producto.getPrecio());
			statement.setDate(5,producto.getFecha_crear());
			statement.setDate(6,producto.getFecha_actualizar());
			
			estadoOperacion=statement.executeUpdate()>0;
			
			connection.commit();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			connection.rollback();
			e.printStackTrace();
		}
		
		return estadoOperacion;
		
		
	}
	//editar video 27
	public boolean editar(Producto producto) throws SQLException {
		String sql=null;
		estadoOperacion=false;
		connection=obtenerConexion();
		
		try {
			connection.setAutoCommit(false);
			sql="UPDATE productos SET nombre=?,cantidad=?,precio=?,fecha_actualizar=? WHERE id=?";
			statement=connection.prepareStatement(sql);
			
			statement.setString(1,producto.getNombre());
			statement.setDouble(2,producto.getCantidad());
			statement.setDouble(3,producto.getPrecio());
			statement.setDate(4,producto.getFecha_actualizar());
			statement.setInt(5, producto.getId());
			
			estadoOperacion=statement.executeUpdate()>0;
			
			connection.commit();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}
		
			return estadoOperacion;
		
	}
	
	
	
	
	//eliminar video 27
	public boolean eliminar(int idProducto) throws SQLException {
		String sql=null;
		estadoOperacion=false;
		connection=obtenerConexion();
		
		try {
			connection.setAutoCommit(false);
			sql="DELETE FROM productos WHERE id=?";
			statement=connection.prepareStatement(sql);
			
			statement.setInt(1, idProducto);
			
			estadoOperacion=statement.executeUpdate()>0;
			
			connection.commit();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}
		
			return estadoOperacion;
		
		
	}
	
	
	
	//obtener lista de productos video 27
	public List<Producto> obtenerProductos() throws SQLException {
	
		ResultSet resulSet=null;
		List<Producto> listaProductos=new ArrayList<>();
		
		String sql=null;
		estadoOperacion=false;
		connection=obtenerConexion();
		
		try {
			sql="SELECT * FROM productos";
			statement=connection.prepareStatement(sql);
			resulSet=statement.executeQuery(sql);
			while (resulSet.next()) {
				Producto p=new Producto();
				p.setId(resulSet.getInt(1));
				p.setNombre(resulSet.getString(2));
				p.setCantidad(resulSet.getDouble(3));
				p.setPrecio(resulSet.getDouble(4));
				p.setFecha_crear(resulSet.getDate(5));
				p.setFecha_actualizar(resulSet.getDate(6));
				listaProductos.add(p);
			
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return listaProductos;
		
	}
	
	
	
	//obtener producto video 27
	public Producto obtenerProducto(int idProducto) throws SQLException {
		ResultSet resulSet=null;
		Producto p=new Producto();
		
		String sql=null;
		estadoOperacion=false;
		connection=obtenerConexion();
		
		try {
			sql="SELECT * FROM productos WHERE id=?";
			statement=connection.prepareStatement(sql);
			statement.setInt(1, idProducto );
			resulSet=statement.executeQuery();
			if (resulSet.next()) {
				
				p.setId(resulSet.getInt(1));
				p.setNombre(resulSet.getString(2));
				p.setCantidad(resulSet.getDouble(3));
				p.setPrecio(resulSet.getDouble(4));
				p.setFecha_crear(resulSet.getDate(5));
				p.setFecha_actualizar(resulSet.getDate(6));
				
			
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return p;
	}
	
	//obtener conexion pool
	private Connection obtenerConexion() throws SQLException {
		return Conexion.getConnection();
		
	}

}	


