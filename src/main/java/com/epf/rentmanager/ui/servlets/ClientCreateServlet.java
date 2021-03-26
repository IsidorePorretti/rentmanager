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
import com.epf.rentmanager.service.ClientService;

@WebServlet("/clients/create")
public class ClientCreateServlet extends HttpServlet{

	//private static ClientService client_service = ClientService.getInstance();
	private static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);

	@Autowired
	ClientService client_service;
	
	@Override
	
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		}
	
	/** 
	 * @return doGet de ClientCreateServlet
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/clients/create.jsp");

		requestDispatcher.forward(req, resp);
				
	}
	
	/**
	 * @return doPost de ClientCreateServlet
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Client client = new Client();
			client.setNom(req.getParameter("last_name")); //dans le jsp, regarder le name du input pour récupérer la valeur
			client.setPrenom(req.getParameter("first_name"));
			client.setEmail(req.getParameter("email"));
			
	        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	        Date parsed;
			
				parsed = format.parse(req.getParameter("birthday"));

		        java.sql.Date date_de_naissance_en_format_sql = new java.sql.Date(parsed.getTime());
		        
		        client.setNaissance(date_de_naissance_en_format_sql);
			
			client_service.create(client);

			resp.sendRedirect("http://localhost:8080/rentmanager/clients");

			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// TODO Auto-generated method stub
		
	
	}
	
	
	
}
