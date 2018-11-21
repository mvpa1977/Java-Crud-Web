package com.ecodeup.controler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecodeup.dao.ProductoDAO;
import com.ecodeup.model.Producto;

/**
 * Servlet implementation class ProductoControler
 */
@WebServlet(description = "administra preticiones para la tabla productos", urlPatterns = { "/productos" })
public class ProductoControler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductoControler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String opcion=request.getParameter("opcion");
		
		
		if (opcion.equals("crear")) {
			System.out.println("Presionó Opcion Crear");
			RequestDispatcher requestDispatcher=request.getRequestDispatcher("/views/crear.jsp");//pasa a la vista crear
			requestDispatcher.forward(request, response);
		}else if (opcion.equals("listar")) {
			
			ProductoDAO productoDAO=new ProductoDAO();
			List<Producto> lista = new ArrayList<>();
			try {
				lista=productoDAO.obtenerProductos();
				for (Producto producto : lista) {
					System.out.println(producto);
				}
				request.setAttribute("lista", lista);//parametro hacia la vista
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("/views/listar.jsp");
				requestDispatcher.forward(request, response);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			System.out.println("Presionó Opcion Listar");
		}else if (opcion.equals("meditar")) {
			int id =Integer.parseInt(request.getParameter("id"));//recibe el parametro id de la pagina list.jsp
			System.out.println("Editar id:"+id);
			ProductoDAO productoDAO=new ProductoDAO();
			Producto p=new Producto();
			try {
				p=productoDAO.obtenerProducto(id);
				System.out.println(p);
				request.setAttribute("producto", p);
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("/views/editar.jsp");
				requestDispatcher.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}else if (opcion.equals("eliminar")) {
			
			ProductoDAO productoDAO=new ProductoDAO();
			int id=Integer.parseInt(request.getParameter("id"));
			try {
				productoDAO.eliminar(id);
				System.out.println("Registro Eliminado");
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("/index.jsp");
				requestDispatcher.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String opcion=request.getParameter("opcion");
		
		Date fechaActual=new Date();
		
		if (opcion.equals("guardar")) {
			
			ProductoDAO productoDAO=new ProductoDAO();
			Producto producto=new Producto();
			
			producto.setNombre(request.getParameter("nombre")); //toma los datos de las vista crear
			producto.setCantidad(Double.parseDouble(request.getParameter("cantidad")));
			producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
			producto.setFecha_crear(new java.sql.Date(fechaActual.getTime()));
					
			try {
				productoDAO.guardar(producto);
				System.out.println("Registro creado");
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("/index.jsp");
				requestDispatcher.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		}else if (opcion.equals("editar")) {
			
			Producto producto=new Producto();
			ProductoDAO productoDAO=new ProductoDAO();
			
			producto.setId(Integer.parseInt(request.getParameter("id")));
			producto.setNombre(request.getParameter("nombre")); 
			producto.setCantidad(Double.parseDouble(request.getParameter("cantidad")));
			producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
			producto.setFecha_actualizar(new java.sql.Date(fechaActual.getTime()));
			try {
				productoDAO.editar(producto);
				System.out.println("Actualizado Correctamente");
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("/index.jsp");
				requestDispatcher.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		
		//doGet(request, response);
	}

}
