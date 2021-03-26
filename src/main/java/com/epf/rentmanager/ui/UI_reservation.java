package com.epf.rentmanager.ui;


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
	public class UI_reservation {
		
		//private static ReservationService reservation_service = ReservationService.getInstance();
		private static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		private static ClientService client_service = context.getBean(ClientService.class);
		private static VehicleService vehicle_service = context.getBean(VehicleService.class);
		private static ReservationService reservation_service = context.getBean(ReservationService.class);
		   
		/**
		 * @return choix de l'action à réaliser dans la partie de travail sur les réservations
		 */
	    public static void option_reservation() {
	    	int choice_reservation = IOUtils.readInt("Veuillez choisir quel utilisation vous souhaitez ? \n"
	    			+ "1. Voir la liste des réservations  "
	    			+ "2. Créer une réservation  "
	    			+ "3. Supprimer une réservation  "
	    			+ "4. Afficher le nombre de réservations  "
	    			+ "5. Modifier une réservation"  
	    			+ "6. Trouver une réservation par son id  ");
	    	
			switch (choice_reservation) {
			case 1 :
				list_of_reservations();
				break;
			
			case 2 :
				create_reservation();
				break;
			
			case 3 :
				delete_reservation();
				break;
			case 4 :
				count();
				break;
			case 5 :
				update_reservation();
				break;
			case 6 :
				list_one_reservation();
				break;
				

			default:
				break;
			}
	    }
	    
	    
		/**
		 * @return création d'une réservation dans la BDD
		 */
	    public static void create_reservation()  {
	    	try {
				long client_id = IOUtils.readInt("Veuillez saisir l'id du client");
				long vehicle_id = IOUtils.readInt("Veuillez saisir l'id du vehicule");
				Date debut = Date.valueOf(IOUtils.readDate("Veuillez choisir la date de début", true));
				Date fin = Date.valueOf(IOUtils.readDate("Veuillez choisir la date de fin", true));
				
				Reservation reservation = new Reservation();
				
				Client client = new Client();
				client.setId(client_id);
				Vehicle vehicle = new Vehicle();
				vehicle.setId(vehicle_id);
				
	            reservation.setClient(client);
				reservation.setVehicle(vehicle);
				reservation.setDebut(debut);
				reservation.setFin(fin);
				
				System.out.println(reservation);
				reservation_service.create(reservation);
				
			

					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
				}
	      }  
	    
	    /**
	     * @return lsite de toutes les réservations de la BDD
	     */
	    public static void list_of_reservations() {
	    	
	    	try {
				List<Reservation> list_all_reservations;
				
				list_all_reservations = reservation_service.findAll();
				
				
				for(Reservation reservation:list_all_reservations) {
					System.out.println(reservation);
				}
			} catch (ServiceException e) {
				IOUtils.print(e.getMessage());
			}

	    }
	    
	    /**
	     * @return liste d'une réservation de la BDD correspondant à un id
	     */
	    public static void list_one_reservation() {
	    	
	    	List <Client> list_client;
	    	List <Vehicle> list_vehicle;
	    	
	    	try {
	    		int id = IOUtils.readInt("Veuillez saisir l'id cible pour affichage des informations : ");
	    		
	    		List <Reservation> reservation = reservation_service.findById(id);
				//System.out.println(reservation);				
				//System.out.println("Ui partie reservation ok");
	  
				list_client = client_service.findClientByReservationId(id);
				System.out.println(list_client);
				for(Client client:list_client) {
					System.out.println(client);
				}
				//System.out.println("Ui partie client ok");
				
				list_vehicle = vehicle_service.findVehicleByReservationId(id);
				System.out.println(list_vehicle);
				for(Vehicle vehicle:list_vehicle) {
					System.out.println(vehicle);
				}
				//System.out.println("Ui partie vehicle ok");
				
			} catch (ServiceException e) {
				IOUtils.print(e.getMessage());
			}

	    }
	    
	    /**
	     * @return modification d'une réservation de la BDD
	     */
	    public static void update_reservation()  {
	    	
	    	try {

				int id = IOUtils.readInt("Veuillez saisir l'id cible pour modification : ");
				long client_id = IOUtils.readInt("Veuillez saisir l'id du client");
				long vehicle_id = IOUtils.readInt("Veuillez saisir l'id du vehicule");
				Date debut = Date.valueOf(IOUtils.readDate("Veuillez choisir la date de début", true));
				Date fin = Date.valueOf(IOUtils.readDate("Veuillez choisir la date de fin", true));
				
				Reservation reservation = new Reservation();
				
				reservation.setId(id);
				
				Client client = new Client();
				client.setId(client_id);
				Vehicle vehicle = new Vehicle();
				vehicle.setId(vehicle_id);
				
	            reservation.setClient(client);
				reservation.setVehicle(vehicle);
				reservation.setDebut(debut);
				reservation.setFin(fin);
				
				System.out.println(reservation);
				reservation_service.update(reservation);
				
			

					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
				}
	      }  
	    
	    /**
	     * @return suppression d'une réservation de la BDD
	     */
	    public static void delete_reservation() {
	    	
	    	try {
				int id = IOUtils.readInt("Veuillez saisir l'id cible pour suppression : ");
				Reservation reservation = new Reservation();
				reservation.setId(id);
				reservation_service.delete(reservation);
				 

			} catch (ServiceException e) {
				IOUtils.print(e.getMessage());
			}
	    }

	    /**
	     * @return le nombre de réservations de la BDD
	     */
	    public static void count() {
	    	
	    	try {
	    		System.out.println(reservation_service.count());
			} catch (ServiceException e) {
				IOUtils.print(e.getMessage());
			}
	    }
	
	
}
