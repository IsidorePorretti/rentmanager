package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.util.List;

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
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

@WebServlet("/clients/details")
public class ClientDetailsServlet extends HttpServlet{

	@Autowired
	ClientService client_service;
	
	@Autowired
	ReservationService reservation_service;
	
	@Autowired
	VehicleService vehicle_service;
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		}
	
	/**
	 * @return le doGet de ClientDetailsServlet
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/clients/details.jsp");
		
		try {
			
			Client client = new Client();
			long monLongId=Long.parseLong(req.getParameter("id"));
			client = client_service.findById(monLongId);
			
			List<Vehicle> ListeVehicleById;
			ListeVehicleById = vehicle_service.findVehicleByClientId(monLongId);
			
			List<Reservation> ListeReservationsById;
			ListeReservationsById = reservation_service.findResaByClientId(monLongId);
						
			
			req.setAttribute("client", client );
			req.setAttribute("vehicles", ListeVehicleById);
			req.setAttribute("reservations", ListeReservationsById );
			
			req.setAttribute("nombre_vehicules", ListeVehicleById.size());
			req.setAttribute("nombre_reservations", ListeReservationsById.size());
			
			requestDispatcher.forward(req, resp);
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @return le doPost de ClientDetailsServlet
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
	

