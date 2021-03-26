package com.epf.rentmanager.ui;

import java.awt.datatransfer.SystemFlavorMap;
import java.sql.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

@Repository
public class UI_vehicle {
	
	//private static VehicleService vehicle_service = VehicleService.getInstance();
	private static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
	private static ClientService client_service = context.getBean(ClientService.class);
	private static VehicleService vehicle_service = context.getBean(VehicleService.class);
	private static ReservationService reservation_service = context.getBean(ReservationService.class);
	  
	
	/**
	 * @return choix de l'action à réaliser dans la partie de travail sur les véhicules
	 */
    public static void option_vehicle() {
    	int choice_vehicle = IOUtils.readInt("Veuillez choisir quel utilisation vous souhaitez ? \n"
    			+ "1. Voir la liste des véhicules  "
    			+ "2. Créer un véhicule  "
    			+ "3. Supprimer un véhicule  "
    			+ "4. Afficher le nombre de véhicules  "
    			+ "5. Modifier un véhicule  "
    			+ "6. Trouver un véhicule par son id.  ");
    	
		switch (choice_vehicle) {
		case 1 :
			list_of_vehicles();
			break;
		
		case 2 :
			create_vehicle();
			break;
		
		case 3 :
			delete_vehicle();
			break;
		case 4 :
			count();
			break;
		case 5 :
			update_vehicle();
			break;
		case 6 :
			list_one_vehicle();
			break;

		default:
			break;
		}
    }
    
    
	/**
	 * @return création d'un véhicule dans la BDD
	 */
    public static void create_vehicle()  {
    	try {
			String constructeur = IOUtils.readString("Veuillez saisir le nom du constructeur", true);
			String modele = IOUtils.readString("Veuillez saisir le nom du modèle", true);
			short nb_places = Short.parseShort(IOUtils.readString("Veuillez saisir le nombre de places", true));
			
			
	
			Vehicle vehicle = new Vehicle();
			
			vehicle.setConstructeur(constructeur);
			vehicle.setModele(modele);
			vehicle.setNb_places(nb_places);
			
			vehicle_service.create(vehicle);

				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
			}
      }  
    
    /**
     * @return liste de tous les véhicules de la BDD
     */
    public static void list_of_vehicles() {
    	
    	try {
			List<Vehicle> list_all_vehicles;
			
			list_all_vehicles = vehicle_service.findAll();
			
			for(Vehicle vehicle:list_all_vehicles) {
				System.out.println(vehicle);
			}
		} catch (ServiceException e) {
			IOUtils.print(e.getMessage());
		}

    }
    
    /** 
     * @return liste d'un véhicule de la BDD correspondant à la recherche par un id
     */
    public static void list_one_vehicle() {
    	
    	
    	Vehicle vehicle = new Vehicle();
    	List<Client> list_client;
    	List<Reservation> list_reservation;
    	
    	try {
    		int id = IOUtils.readInt("Veuillez saisir l'id cible pour affichage des informations : ");

    		vehicle = vehicle_service.findById(id);	
			System.out.println(vehicle);
			
			//System.out.println("Ui partie vehicule ok");
			
			
			list_client = client_service.findClientByVehicleId(id);
			System.out.println(list_client);
			for(Client client:list_client) {
				System.out.println(client);
			}
			//System.out.println("Ui partie client ok");

			
			
			list_reservation = reservation_service.findResaByVehicleId(id);
			System.out.println(list_reservation);
			
			for(Reservation reservation:list_reservation) {
				System.out.println(reservation);
			}
			//System.out.println("Ui partie reservation ok");
		} catch (ServiceException e) {
			IOUtils.print(e.getMessage());
		}

    }
    
    
    
    
	/**
	 * @return modification d'un véhicule de la BDD
	 */
    public static void update_vehicle()  {
    	
    	try {
    		int id = IOUtils.readInt("Veuillez saisir l'id cible pour modification : ");
			String constructeur = IOUtils.readString("Veuillez saisir le nom du constructeur", true);
			String modele = IOUtils.readString("Veuillez saisir le nom du modèle", true);
			short nb_places = Short.parseShort(IOUtils.readString("Veuillez saisir le nombre de places", true));
			
			
	
			Vehicle vehicle = new Vehicle();
			
			vehicle.setConstructeur(constructeur);
			vehicle.setModele(modele);
			vehicle.setNb_places(nb_places);
			vehicle.setId(id);
			
			
			//System.out.println(vehicle);
			vehicle_service.update(vehicle);

				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
			}
      }  
    
    /**
     * @return suppression d'un véhicule de la BDD
     */
    public static void delete_vehicle() {
    	
    	try {
			int id = IOUtils.readInt("Veuillez saisir l'id cible pour suppression : ");
			Vehicle vehicle = new Vehicle();
			vehicle.setId(id);
			vehicle_service.delete(vehicle);
		} catch (ServiceException e) {
			IOUtils.print(e.getMessage());
		}
    }
    
    /**
     * @return le nombre de véhicules de la BDD
     */
    public static void count() {
    	
    	try {
    	//	vehicle_service.count();
    		System.out.println(vehicle_service.count());
			
		} catch (ServiceException e) {
			IOUtils.print(e.getMessage());
		}
    }


}
