package com.ecodeup.conexion;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class Conexion {
	
	
		private static BasicDataSource dataSource=null;
		
		private static DataSource getDataSource() {
			if (dataSource==null) {
				dataSource=new BasicDataSource();
				dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
				dataSource.setUsername("root");
				dataSource.setPassword("l0jan0");
				dataSource.setUrl("jdbc:mysql://localhost:3307/crud");
				dataSource.setInitialSize(20);
				dataSource.setMaxIdle(15);
				dataSource.setMaxWaitMillis(5000);
				
			}
			return dataSource;
			
		}
		//metodo retorno de la coneccion 
		public static Connection getConnection() throws SQLException {
			return getDataSource().getConnection();
			
		}
}
