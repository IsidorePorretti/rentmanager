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
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;


@WebServlet("/home")
/**
 * 
 * @author porretti
 *
 */
public class HomeServlet extends HttpServlet {
	
	private static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
	//private static ClientService client_service = ClientService.getInstance();
	//private static VehicleService vehicle_service = VehicleService.getInstance();
  	//private static ReservationService reservation_service = ReservationService.getInstance();

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
		
		try {

		
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/home.jsp");
		
		req.setAttribute("count_clients", client_service.count() );
		req.setAttribute("count_vehicles", vehicle_service.count() );
		req.setAttribute("count_reservations", reservation_service.count() );
		
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
	
}
