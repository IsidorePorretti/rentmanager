package com.epf.rentmanager.ui.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

@WebServlet("/cars/delete")
public class VehicleDeleteServlet extends HttpServlet{

	
	//private static VehicleService vehicle_service = VehicleService.getInstance();
	private static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);	
	
	
	@Autowired
	VehicleService vehicle_service;
	
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		}
	
	
	/**
	 * @return le doGet de VehicleDeleteServlet
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
				
	try {
		Vehicle vehicle = new Vehicle();
		
		
		long monLongId=Long.parseLong(req.getParameter("id"));
		vehicle.setId(monLongId);
		
		vehicle_service.delete(vehicle);

		resp.sendRedirect("http://localhost:8080/rentmanager/cars");

		
	} catch (ServiceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
		/**
		 * @return le doPost de VehicleDeleteServlet
		 */
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		
	}
	

	
}
