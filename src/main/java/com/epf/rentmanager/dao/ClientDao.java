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
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	
	/*public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}*/
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom = ?, prenom = ?, email = ?, naissance = ? WHERE id= ?;";
	private static final String FIND_CLIENT_BY_ID_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	
	private static final String FIND_CLIENT_BY_VEHICLE_QUERY = 		
			"SELECT DISTINCT ON (Client.id) Client.id, Client.nom, Client.prenom, Client.email, Client.naissance "
			+ "FROM Reservation "
            + "INNER JOIN Client ON Reservation.client_id= Client.id "
            + "INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id "
            + "WHERE Reservation.vehicle_id = ?;";
	
	private static final String FIND_CLIENT_BY_RESERVATION_QUERY = 		
			"SELECT DISTINCT ON (Client.id) Client.id, Client.nom, Client.prenom, Client.email, Client.naissance "
			+ "FROM Reservation "
            + "INNER JOIN Client ON Reservation.client_id= Client.id "
            + "INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id "
            + "WHERE Reservation.id = ?;";
	
	
	
	private static final String SELECT_COUNT_CLIENT = "SELECT COUNT(id) AS count FROM Client;";
	
	
	public long create(Client client) throws DaoException {
		long id = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());	
			ps.setString(3, client.getEmail());
			ps.setDate(4, client.getNaissance());
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
			throw new DaoException();
		}
	}
	public long delete(Client client ) throws DaoException {

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_CLIENT_QUERY);
			
			ps.setLong(1, client.getId());
			ps.executeUpdate();
			
			ps.close();
			connection.close();
			return 1;
			
		} catch (SQLException e) {
			throw new DaoException();
		}
	}
	
	public long update(Client client) throws DaoException {
				
			try {
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE_CLIENT_QUERY);
				
				ps.setString(1, client.getNom());
				ps.setString(2, client.getPrenom());	
				ps.setString(3, client.getEmail());
				ps.setDate(4, client.getNaissance());
				ps.setLong(5, client.getId());
				
								
				ps.execute();
				//ps.executeUpdate();
				ps.close();
				connection.close();

				//System.out.println(client.getId());
				return 1;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new DaoException();
			}
		
	}
	
	
	public Optional<Client> findById(long id) throws DaoException {

	
		Optional<Client> opt_client;

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENT_BY_ID_QUERY);
			
			ps.setLong(1, id);
			
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()){
				Client client = new Client();			
				client.setId(resultSet.getLong("id"));
				client.setNom(resultSet.getString("nom"));
				client.setPrenom(resultSet.getString("prenom"));
				client.setEmail(resultSet.getString("email"));
				client.setNaissance(resultSet.getDate("naissance"));
				opt_client = Optional.of(client);

			}else {
				opt_client = Optional.empty();
			}
			resultSet.close();
			ps.close();
			connection.close();
			
			//System.out.println("Dao ok");
			return opt_client;
			
			
		} catch (SQLException e) {
			throw new DaoException();
		}
	}
	
	
	
	/**
	 * 
	 * @return 
	 * @throws DaoException 
	 */
	public List<Client> findAll() throws DaoException {
		List<Client> result = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENTS_QUERY);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				Client client = new Client();
				client.setId(resultSet.getInt("id"));
				client.setNom(resultSet.getString("nom"));
				client.setPrenom(resultSet.getString("prenom"));
				client.setEmail(resultSet.getString("email"));
				client.setNaissance(resultSet.getDate("naissance"));
				result.add(client);
			}
			
			resultSet.close();
			preparedStatement.close();
			connection.close();
			

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			//throw new DaoException();
		}
		
		return result;
	}
	
	
	
	public List<Client> findClientByVehicleId(long vehicleId) throws DaoException {
		
		List<Client> result1 = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENT_BY_VEHICLE_QUERY);
			
			ps.setLong(1, vehicleId);
			
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()){
				Client client = new Client();
				client.setId(resultSet.getInt("id"));
				client.setNom(resultSet.getString("nom"));
				client.setPrenom(resultSet.getString("prenom"));
				client.setEmail(resultSet.getString("email"));
				client.setNaissance(resultSet.getDate("naissance"));
				result1.add(client);

			}
			resultSet.close();
			ps.close();
			connection.close();
			//System.out.println("Client DAO ok");
			return result1;
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DaoException();
		}	 	
	}
	
	
	public List<Client> findClientByReservationId(long reservationId) throws DaoException {
		
		List<Client> result2 = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENT_BY_RESERVATION_QUERY);
			
			ps.setLong(1, reservationId);
			
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()){
				Client client = new Client();
				client.setId(resultSet.getInt("id"));
				client.setNom(resultSet.getString("nom"));
				client.setPrenom(resultSet.getString("prenom"));
				client.setEmail(resultSet.getString("email"));
				client.setNaissance(resultSet.getDate("naissance"));
				result2.add(client);

			}
			resultSet.close();
			ps.close();
			connection.close();
			//System.out.println("Client DAO ok");
			return result2;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DaoException();
		}	 	
	}
	
	
	public int count() throws DaoException {
		
		int nombre = 0;
		try {
		Connection connection = ConnectionManager.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_CLIENT);
		
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
