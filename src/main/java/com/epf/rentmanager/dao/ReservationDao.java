package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;


@Repository
public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	
	/*public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	*/
	

	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String UPDATE_RESERVATION_QUERY= "UPDATE Reservation SET client_id = ?, vehicle_id = ?, debut = ?, fin =? WHERE id= ?;";
	
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = 
			"SELECT Reservation.id, Reservation.vehicle_id, Reservation.debut, Reservation.fin,Reservation.client_id, "
		            + "Client.nom, Client.prenom,Client.email, Client.naissance, "
		            + "Vehicle.constructeur, Vehicle.modele, Vehicle.nb_places "
		            + "FROM Reservation "
		            + "INNER JOIN Client ON Reservation.client_id= Client.id "
		            + "INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id "
		            + "WHERE Reservation.client_id = ?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = 
			"SELECT Reservation.id, Reservation.vehicle_id, Reservation.debut, Reservation.fin,Reservation.client_id, "
		            + "Client.nom, Client.prenom,Client.email, Client.naissance, "
		            + "Vehicle.constructeur, Vehicle.modele, Vehicle.nb_places "
		            + "FROM Reservation "
		            + "INNER JOIN Client ON Reservation.client_id= Client.id "
		            + "INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id "
		            + "WHERE Reservation.vehicle_id = ?;";
	
	private static final String FIND_RESERVATIONS_QUERY = 
			"SELECT Reservation.id, Reservation.vehicle_id, Reservation.debut, Reservation.fin,Reservation.client_id, "
            + "Client.nom, Client.prenom,Client.email, Client.naissance, "
            + "Vehicle.constructeur, Vehicle.modele, Vehicle.nb_places "
            + "FROM Reservation "
            + "INNER JOIN Client ON Reservation.client_id= Client.id "
            + "INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id;";
	
	private static final String FIND_RESERVATION_BY_ID_QUERY = 
			"SELECT Reservation.id, Reservation.vehicle_id, Reservation.debut, Reservation.fin,Reservation.client_id, "
		            + "Client.nom, Client.prenom,Client.email, Client.naissance, "
		            + "Vehicle.constructeur, Vehicle.modele, Vehicle.nb_places "
		            + "FROM Reservation "
		            + "INNER JOIN Client ON Reservation.client_id= Client.id "
		            + "INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id "
		            + "WHERE Reservation.id = ?;";
	
	private static final String SELECT_COUNT_RESERVATION = "SELECT COUNT(id) AS count FROM Reservation;";
	
	private static final String FIND_RESERVATION_OF_VEHICLE_PERIOD = "SELECT Reservation.id, Reservation.vehicle_id, Reservation.debut, Reservation.fin,Reservation.client_id, "
			+ "Client.nom, Client.prenom,Client.email, Client.naissance, "
			+ "Vehicle.constructeur, Vehicle.modele, Vehicle.nb_places " + "FROM Reservation "
			+ "INNER JOIN Client ON Reservation.client_id= Client.id "
			+ "INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id " + "WHERE Reservation.vehicle_id = ? "
			+ "AND ( ? BETWEEN Reservation.debut AND Reservation.fin OR ? BETWEEN Reservation.debut AND Reservation.fin ) "
			+ "ORDER BY Reservation.debut ASC;";
	
 
	/**
	 * 
	 * @param reservation
	 * @return création d'une réservation dans la BDD
	 * @throws DaoException
	 */
	public long create(Reservation reservation) throws DaoException {
		long id = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
			
			//System.out.println(reservation);
			

			
			ps.setLong(1, reservation.getClient().getId());
			ps.setLong(2, reservation.getVehicle().getId());	
			ps.setDate(3, reservation.getDebut());
			ps.setDate(4, reservation.getFin());
			ps.executeUpdate();
			
			ResultSet resultSet = ps.getGeneratedKeys();
			
			
			
			while (resultSet.next()){
				id = resultSet.getLong(1);
			}
			resultSet.close();
			ps.close();
			connection.close();
			return id;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DaoException();
		}
	}
	
	/**
	 * 
	 * @param reservation
	 * @return suppréssion d'une réservation de la BDD
	 * @throws DaoException
	 */
	public long delete(Reservation reservation) throws DaoException {

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_RESERVATION_QUERY);
			
			ps.setLong(1, reservation.getId());
			ps.executeUpdate();
			
			ps.close();
			connection.close();
			return 1;
			
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	/**
	 * 
	 * @param reservation
	 * @return modification d'une réservation de la BDD
	 * @throws DaoException
	 */
	public long update(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_RESERVATION_QUERY);
			
			
			ps.setLong(1, reservation.getClient().getId());
			ps.setLong(2, reservation.getVehicle().getId());	
			ps.setDate(3, reservation.getDebut());
			ps.setDate(4, reservation.getFin());
			ps.setLong(5, reservation.getId());
			
			ps.executeUpdate();
			
			
			ps.close();
			connection.close();
			System.out.println(reservation.getId());
			return 1;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DaoException();
		}
	}

	
	
	 
	


	/**
	 * 
	 * @param clientId
	 * @return liste d'une réservation de la BDD à partir d'une recherche par l'id du client
	 * @throws DaoException
	 */
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		
		List<Reservation> result1 = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			
			ps.setLong(1, clientId);
			
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()){
				Reservation reservation = new Reservation();
	            reservation.setId(resultSet.getLong("id"));

	            Client client = new Client();
	            client.setId(resultSet.getInt("client_id"));
	            client.setNom(resultSet.getString("nom"));
	            client.setPrenom(resultSet.getString("prenom"));
	            client.setEmail(resultSet.getString("email"));
	            client.setNaissance(resultSet.getDate("naissance"));

	            reservation.setClient(client);

	            Vehicle vehicle = new Vehicle();
	            vehicle.setId(resultSet.getLong("vehicle_id"));
	            vehicle.setModele(resultSet.getString("modele"));
	            vehicle.setConstructeur(resultSet.getString("constructeur"));
	            vehicle.setNb_places(resultSet.getShort("nb_places"));
	            reservation.setVehicle(vehicle);

	            reservation.setDebut(resultSet.getDate("debut"));
	            reservation.setFin(resultSet.getDate("fin"));
	            
				result1.add(reservation);

			}
			resultSet.close();
			ps.close();
			connection.close();
			//System.out.println("Reservation Dao ok");
			return result1;
			
		} catch (SQLException e) {
			throw new DaoException();
		}	 	
	}
	
	/**
	 * 
	 * @param vehicleId
	 * @return liste d'une réservation de la BDD à partir d'une recherche par l'id du véhicule
	 * @throws DaoException
	 */
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		
		List<Reservation> result2 = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			
			ps.setLong(1, vehicleId);
			
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()){
				Reservation reservation = new Reservation();
	            reservation.setId(resultSet.getLong("id"));

	            Client client = new Client();
	            client.setId(resultSet.getInt("client_id"));
	            client.setNom(resultSet.getString("nom"));
	            client.setPrenom(resultSet.getString("prenom"));
	            client.setEmail(resultSet.getString("email"));
	            client.setNaissance(resultSet.getDate("naissance"));

	            reservation.setClient(client);

	            Vehicle vehicle = new Vehicle();
	            vehicle.setId(resultSet.getLong("vehicle_id"));
	            vehicle.setModele(resultSet.getString("modele"));
	            vehicle.setConstructeur(resultSet.getString("constructeur"));
	            vehicle.setNb_places(resultSet.getShort("nb_places"));
	            reservation.setVehicle(vehicle);

	            reservation.setDebut(resultSet.getDate("debut"));
	            reservation.setFin(resultSet.getDate("fin"));
	            
				result2.add(reservation);

			}
			resultSet.close();
			ps.close();
			connection.close();
			return result2;
			
		} catch (SQLException e) {
			throw new DaoException();
		}	 
	}

	/**
	 * 
	 * @param reservationId
	 * @return liste d'une réservation de la BDD à partir d'une recherche par son id
	 * @throws DaoException
	 */
	public List<Reservation> findById(long reservationId) throws DaoException {
		List<Reservation> result3 = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATION_BY_ID_QUERY);
			
			ps.setLong(1, reservationId);
			
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()){
				Reservation reservation = new Reservation();
	            reservation.setId(resultSet.getLong("id"));

	            Client client = new Client();
	            client.setId(resultSet.getInt("client_id"));
	            client.setNom(resultSet.getString("nom"));
	            client.setPrenom(resultSet.getString("prenom"));
	            client.setEmail(resultSet.getString("email"));
	            client.setNaissance(resultSet.getDate("naissance"));

	            reservation.setClient(client);

	            Vehicle vehicle = new Vehicle();
	            vehicle.setId(resultSet.getLong("vehicle_id"));
	            vehicle.setModele(resultSet.getString("modele"));
	            vehicle.setConstructeur(resultSet.getString("constructeur"));
	            vehicle.setNb_places(resultSet.getShort("nb_places"));
	            reservation.setVehicle(vehicle);

	            reservation.setDebut(resultSet.getDate("debut"));
	            reservation.setFin(resultSet.getDate("fin"));
	            
				result3.add(reservation);

			}
			resultSet.close();
			ps.close();
			connection.close();
			return result3;
			
		} catch (SQLException e) {
			throw new DaoException();
		}	 
	}
	
	/**
	 * 
	 * @return la liste de toutes les réservations de la BDD
	 * @throws DaoException
	 */
	public List<Reservation> findAll() throws DaoException {
		List<Reservation> result = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_QUERY);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()){
				
			Reservation reservation = new Reservation();
            reservation.setId(resultSet.getLong("id"));

            Client client = new Client();
            client.setId(resultSet.getInt("client_id"));
            client.setNom(resultSet.getString("nom"));
            client.setPrenom(resultSet.getString("prenom"));
            client.setEmail(resultSet.getString("email"));
            client.setNaissance(resultSet.getDate("naissance"));

            reservation.setClient(client);

            Vehicle vehicle = new Vehicle();
            vehicle.setId(resultSet.getLong("vehicle_id"));
            vehicle.setModele(resultSet.getString("modele"));
            vehicle.setConstructeur(resultSet.getString("constructeur"));
            vehicle.setNb_places(resultSet.getShort("nb_places"));
            reservation.setVehicle(vehicle);

            reservation.setDebut(resultSet.getDate("debut"));
            reservation.setFin(resultSet.getDate("fin"));
            
			result.add(reservation);
			}
			resultSet.close();
			preparedStatement.close();
			connection.close();
			
			//System.out.println("Après le dao");
			return result;
			
		} catch (SQLException e) {
			throw new DaoException();
		}
		
	}
	
	
	public List<Reservation> findReservationVehicleIsOccupated(Reservation reservation_verif)
			throws DaoException {

		List<Reservation> list_reservation = new ArrayList<>();
		try {

			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection
					.prepareStatement(FIND_RESERVATION_OF_VEHICLE_PERIOD);
			ps.setLong(1, reservation_verif.getVehicle().getId());
			ps.setDate(2, reservation_verif.getDebut());
			ps.setDate(3, reservation_verif.getFin());
			ps.setLong(4, reservation_verif.getId());
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Reservation reservation = new Reservation();
				reservation.setId(resultSet.getLong("id"));

				Client client = new Client();
				client.setId(resultSet.getInt("client_id"));
				client.setNom(resultSet.getString("nom"));
				client.setPrenom(resultSet.getString("prenom"));
				client.setEmail(resultSet.getString("email"));
				client.setNaissance(resultSet.getDate("naissance"));

				reservation.setClient(client);

				Vehicle vehicle = new Vehicle();
				vehicle.setId(resultSet.getLong("vehicle_id"));
				vehicle.setModele(resultSet.getString("modele"));
				vehicle.setConstructeur(resultSet.getString("constructeur"));
				vehicle.setNb_places(resultSet.getShort("nb_places"));
				reservation.setVehicle(vehicle);

				reservation.setDebut(resultSet.getDate("debut"));
				reservation.setFin(resultSet.getDate("fin"));

				list_reservation.add(reservation);

			}

			resultSet.close();
			ps.close();
			connection.close();
			return list_reservation;
		} catch (SQLException e) {
			throw new DaoException();
		}

	}
	
	/**
	 * 
	 * @return le nombre de réservations de la BDD
	 * @throws DaoException
	 */
	public int count() throws DaoException {
		
		int nombre = 0;
		try {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_RESERVATION);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (resultSet.next()){
		nombre = resultSet.getInt("count");
		}
		
		
		resultSet.close();
		preparedStatement.close();
		connection.close();
		
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			//throw new DaoException();
		}
		return nombre;
		
	}	

}
