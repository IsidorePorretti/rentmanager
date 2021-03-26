package com.epf.rentmanager.ui.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/cars/update")
public class VehicleUpdateServlet extends HttpServlet {
	
	
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
	 * @return le doGet de VehicleUpdateServlet
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
			
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp");
			requestDispatcher.forward(req, resp);
			
}
	/**
	 * @return le doPost de VehicleUpdateServlet
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// traitement du formulaire (appel à la méthode de sauvegarde)}
		
		try {
			Vehicle vehicle = new Vehicle();
			
			long monLongId=Long.parseLong(req.getParameter("id"));
			vehicle.setId(monLongId);
			
			vehicle.setConstructeur(req.getParameter("manufacturer"));
			vehicle.setModele(req.getParameter("modele"));
			vehicle.setNb_places(Short.parseShort(req.getParameter("seats")));
			
			
			
			vehicle_service.update(vehicle);
			resp.sendRedirect("http://localhost:8080/rentmanager/cars");
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		
	}
	
	
}
