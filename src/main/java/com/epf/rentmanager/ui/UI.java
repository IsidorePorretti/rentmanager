package com.epf.rentmanager.ui;


import com.epf.rentmanager.utils.*;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

/**
 * 
 * @author porretti
 *
 */
@Repository
public class UI {
	//private static ClientService client_service = ClientService.getInstance();
	private static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
	//private static ClientService client_service = context.getBean(ClientService.class);
   
	
	/** 
	 * @return choix de l'espace de travail
	 * @param args
	 * @throws ServiceException
	 */
	public static void main(String[] args) throws ServiceException {
    	
    	int option_domain = IOUtils.readInt("Veuillez choisir quel utilisation vous souhaitez ? \n"
    			+ "1. Travail sur les clients  "
    			+ "2. Travail sur les véhicules  "
    			+ "3. Travail sur les réservations  ");
    	
   	
    	switch (option_domain){
		case 1 :     
			UI_client.option_client();			
			break;
			
		case 2 :
			UI_vehicle.option_vehicle();
			break;
			
		case 3 :
			UI_reservation.option_reservation();
			break;

		default:
			break;
		}
    	
   
    }
   

}