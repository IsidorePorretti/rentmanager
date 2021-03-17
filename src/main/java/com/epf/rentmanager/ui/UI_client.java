package com.epf.rentmanager.ui;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import java.util.regex.Matcher; 
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository; 

@Repository
public class UI_client {

	//private static ClientService client_service = ClientService.getInstance();
	private static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
	private static ClientService client_service = context.getBean(ClientService.class);
	private static VehicleService vehicle_service = context.getBean(VehicleService.class);
	private static ReservationService reservation_service = context.getBean(ReservationService.class);
	
    public static void option_client() {
    	int choice_client = IOUtils.readInt("Veuillez choisir quel utilisation vous souhaitez ? \n"
    			+ "1. Voir la liste des clients. "
    			+ "2. Créer un client.  "
    			+ "3. Supprimer un client.  "
    			+ "4. Afficher le nombre de clients.  "
    			+ "5. Modifier un client.  "
    			+ "6. Trouver un client par son id.  ");

    		switch (choice_client) {
			case 1 :
				list_of_clients();
				break;
			
			case 2 :
				create_client();
				
				break;
			
			case 3 :
				delete_client();
				break;
			
			case 4 :
				count();
				break;
				
			case 5 :
				update_client();
				break;
				
			case 6 :
				list_one_client();
				break;

			default:
				break;
			}
    }
	
    public static void create_client()  {
    	
    	   
    	
    	try {
			String nom = IOUtils.readString("Veuillez saisir le nom de l'utilisateur", true);
			String prenom = IOUtils.readString("Veuillez saisir le prenom de l'utilisateur", true);
			
			String email = "";
			while (!isValid(email)) {
				email = IOUtils.readString("Veuillez saisir le mail de l'utilisateur", true);
			}
			
			Date naissance = Date.valueOf(IOUtils.readDate("Veuillez saisir la date de naissance de l'utilisateur", true));
   
	
			 			
			Client client = new Client();
			client.setNom(nom);
			client.setPrenom(prenom);
			client.setEmail(email);
			client.setNaissance(naissance);
   
			client_service.create(client);
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
			}
      
    }  
    

    	  

    public static boolean isValid(String email) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    } 
    	  
    	    

    
    public static void list_of_clients() {
    	
    		List<Client> list_all_client;
			try {
				list_all_client = client_service.findAll();
			
    		
				for(Client client:list_all_client) {
    			System.out.println(client);
				}
			} catch (ServiceException e) {
				IOUtils.print(e.getMessage());
			}
    	   	
    }   
    
    
    public static void list_one_client() {
    	
    	Client client = new Client();
    	List<Reservation> list_reservation;
    	List<Vehicle> list_vehicle;
    	
		try {
			int id = IOUtils.readInt("Veuillez saisir l'id cible pour affichage des informations : ");

			client = client_service.findById(id);	
			System.out.println(client);
			
			System.out.println("Ui partie client ok");
			
			
			list_vehicle = vehicle_service.findVehicleByClientId(id);
			//System.out.println(list_vehicle);
			for(Vehicle vehicle:list_vehicle) {
				System.out.println(vehicle);
			}
			System.out.println("Ui partie vehicle ok");

			
			
			list_reservation = reservation_service.findResaByClientId(id);
			//System.out.println(list_reservation);
			
			for(Reservation reservation:list_reservation) {
				System.out.println(reservation);
			}
			System.out.println("Ui partie reservation ok");
			
						
		} catch (ServiceException e) {
			IOUtils.print(e.getMessage());
		}
	   	
}  
    
 public static void update_client()  {

    	try {

			int id = IOUtils.readInt("Veuillez saisir l'id cible pour modification : ");
			
			String nom = IOUtils.readString("Veuillez saisir le nom de l'utilisateur", true);
			
			String prenom = IOUtils.readString("Veuillez saisir le prenom de l'utilisateur", true);
			
			String email = "";
			while (!isValid(email)) {
				email = IOUtils.readString("Veuillez saisir le mail de l'utilisateur", true);
			}
			
			Date naissance = Date.valueOf(IOUtils.readDate("Veuillez saisir la date de naissance de l'utilisateur", true));
   
			
			
			Client client = new Client();
			
			client.setNom(nom);
			client.setPrenom(prenom);
			client.setEmail(email);
			client.setNaissance(naissance);
			client.setId(id);
			
			//System.out.println(client);
   
			client_service.update(client);
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
			}
      
    }  
    
    public static void delete_client() {
    	
    	
    	try {
			int id = IOUtils.readInt("Veuillez saisir l'id cible pour supression : ");
			Client client = new Client();
			client.setId(id);
			client_service.delete(client);
		} catch (ServiceException e) {
			IOUtils.print(e.getMessage());
		}
    }
    
    
    public static void count() {
    	
    	try {
    		System.out.println(client_service.count());
		} catch (ServiceException e) {
			IOUtils.print(e.getMessage());
		}
    }
}
