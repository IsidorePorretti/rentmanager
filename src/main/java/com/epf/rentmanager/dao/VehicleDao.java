 package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	
	/*public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	*/
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String UPDATE_VEHICLE_QUERY= "UPDATE Vehicle SET constructeur = ?, modele = ?, nb_places = ? WHERE id= ?;";
	
	private static final String FIND_VEHICLE_BY_ID_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	
	private static final String FIND_VEHICLE_BY_CLIENT_QUERY = 		
			"SELECT DISTINCT ON (Vehicle.id) Vehicle.id, Vehicle.constructeur, Vehicle.modele, Vehicle.nb_places "
			+ "FROM Reservation "
            + "INNER JOIN Client ON Reservation.client_id= Client.id "
            + "INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id "
            + "WHERE Reservation.client_id = ?;";
	
	private static final String FIND_VEHICLE_BY_RESERVATION_QUERY = 		
			"SELECT DISTINCT ON (Vehicle.id) Vehicle.id, Vehicle.constructeur, Vehicle.modele, Vehicle.nb_places "
			+ "FROM Reservation "
            + "INNER JOIN Client ON Reservation.client_id= Client.id "
            + "INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id "
            + "WHERE Reservation.id = ?;";
	
	private static final String SELECT_COUNT_VEHICLE = "SELECT COUNT(id) AS count FROM Vehicle;";
	
	
	public long create(Vehicle vehicle) throws DaoException {
		long id = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, vehicle.getConstructeur());
			ps.setString(2, vehicle.getModele());
			ps.setShort(3, vehicle.getNb_places());	
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

	public long delete(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_VEHICLE_QUERY);
			
			ps.setLong(1, vehicle.getId());
			ps.executeUpdate();
			
			ps.close();
			connection.close();
			return 1;
			
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	
	public long update(Vehicle vehicle) throws DaoException {
		
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_VEHICLE_QUERY);
			
			ps.setString(1, vehicle.getConstructeur());
			ps.setString(2, vehicle.getModele());
			ps.setShort(3, vehicle.getNb_places());	
			ps.setLong(4, vehicle.getId());
			
			ps.execute();
			//ps.executeUpdate();
			
			ps.close();
			connection.close();
			
			//System.out.println(vehicle.getId());
			return 1;
			
		} catch (SQLException e) {
			throw new DaoException();
		}
	}
	
	
	public Optional<Vehicle> findById(long id) throws DaoException {

		Optional<Vehicle> opt_vehicle;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_BY_ID_QUERY);
			
			ps.setLong(1, id);
			
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()){
				Vehicle vehicle = new Vehicle();	
				vehicle.setId(resultSet.getLong("id"));
				vehicle.setConstructeur(resultSet.getString("constructeur"));
				vehicle.setModele(resultSet.getString("modele"));
				vehicle.setNb_places(resultSet.getShort("nb_places"));
				opt_vehicle = Optional.of(vehicle);

			}else {
				opt_vehicle = Optional.empty();
						}
			resultSet.close();
			ps.close();
			connection.close();
			return opt_vehicle;
			
		} catch (SQLException e) {
			throw new DaoException();
			//System.out.println();
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> result = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLES_QUERY);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				Vehicle vehicle = new Vehicle();
				vehicle.setId(resultSet.getInt("id"));
				vehicle.setConstructeur(resultSet.getString("constructeur"));
				vehicle.setModele(resultSet.getString("modele"));
				vehicle.setNb_places(resultSet.getShort("nb_places"));
				result.add(vehicle);
			}
			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException();
		}
		return result;
	}
	
	public List<Vehicle> findVehicleByClientId(long clientId) throws DaoException {
		
		List<Vehicle> result1 = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_BY_CLIENT_QUERY);
			
			ps.setLong(1, clientId);
			
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()){
				Vehicle vehicle = new Vehicle();
				vehicle.setId(resultSet.getInt("id"));
				vehicle.setConstructeur(resultSet.getString("constructeur"));
				vehicle.setModele(resultSet.getString("modele"));
				vehicle.setNb_places(resultSet.getShort("nb_places"));
				result1.add(vehicle);

			}
			resultSet.close();
			ps.close();
			connection.close();
			//System.out.println("Vehcile DAO ok");
			return result1;
			
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DaoException();
		}	 	
	}
	
	
	public List<Vehicle> findVehicleByReservationId(long reservationId) throws DaoException {
		
		List<Vehicle> result2 = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_BY_RESERVATION_QUERY);
			
			ps.setLong(1, reservationId);
			
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()){
				Vehicle vehicle = new Vehicle();
				vehicle.setId(resultSet.getInt("id"));
				vehicle.setConstructeur(resultSet.getString("constructeur"));
				vehicle.setModele(resultSet.getString("modele"));
				vehicle.setNb_places(resultSet.getShort("nb_places"));
				result2.add(vehicle);

			}
			resultSet.close();
			ps.close();
			connection.close();
			//System.out.println("Vehcile DAO ok");
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
		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_VEHICLE);
		
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
