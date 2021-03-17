package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet{

private static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
//private static ReservationService reservation_service = ReservationService.getInstance();
//private static ClientService client_service = ClientService.getInstance();	
//private static VehicleService vehicle_service = VehicleService.getInstance();

@Autowired
ClientService client_service;

@Autowired
VehicleService vehicle_service;

@Autowired
ReservationService reservation_service;

@Override
public void init() throws ServletException {
	super.init();
	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
		req.setAttribute("clients", client_service.findAll());
		req.setAttribute("vehicles", vehicle_service.findAll());
		
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/rents/create.jsp");
		requestDispatcher.forward(req, resp);
		}
		
		catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
				
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Client client = new Client();
			long client_long = Long.parseLong(req.getParameter("client"));
			client.setId(client_long);
			
			Vehicle vehicle = new Vehicle();
			long vehicle_long = Long.parseLong(req.getParameter("car"));
			vehicle.setId(vehicle_long);
			
			Reservation reservation  = new Reservation();
			
			reservation.setClient(client); 
			reservation.setVehicle(vehicle);
			
	        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	        Date parsed;
			
				parsed = format.parse(req.getParameter("begin"));
		        java.sql.Date debut = new java.sql.Date(parsed.getTime() );
		        
		        parsed = format.parse(req.getParameter("end"));
		        java.sql.Date fin = new java.sql.Date(parsed.getTime());
		        
		        reservation.setDebut(debut);
		        reservation.setFin(fin);
			
		        //System.out.println(reservation);
		        
		        reservation_service.create(reservation);

			resp.sendRedirect("http://localhost:8080/rentmanager/rents");

			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		  catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}// TODO Auto-generated method stub
		
	
	}
	
	
	
	
}
