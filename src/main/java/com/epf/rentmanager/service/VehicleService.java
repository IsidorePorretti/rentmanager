package com.epf.rentmanager.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	
	@Autowired
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}
	
	/**
	 * 
	 * @param vehicle
	 * @return création d'un véhicule
	 * @throws ServiceException
	 */
	public long create(Vehicle vehicle) throws ServiceException {
		// TODO: créer un véhicule
		if (vehicle.getConstructeur().isEmpty()) {
			throw new ServiceException();
		}

		if (vehicle.getNb_places() < 2 || vehicle.getNb_places() > 9) {
			throw new ServiceException();
		}
		
		try {
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ServiceException();
		}
	}
	
	/**
	 * 
	 * @param vehicle
	 * @return modification d'un véhicule
	 * @throws ServiceException
	 */
	public long update(Vehicle vehicle) throws ServiceException {
		// TODO: modifier un véhicule
		if (vehicle.getConstructeur().isEmpty()) {
			throw new ServiceException();
		}

		if (vehicle.getNb_places() < 1) {
			throw new ServiceException();
		}
		
		try {
			return vehicleDao.update(vehicle);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ServiceException();
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return liste d'un véhicule correspondant à une recherche par son ID
	 * @throws ServiceException
	 */
	public Vehicle findById(long id) throws ServiceException {
		
		Vehicle vehicle;
		try {
			Optional<Vehicle> optvehicle = vehicleDao.findById(id);
			if(optvehicle.isPresent()) {
				vehicle = optvehicle.get();
			} else {
				throw new ServiceException();
			}
			
			return vehicle;
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	/**
	 * 
	 * @return la liste de tous les véhicules de la BDD
	 * @throws ServiceException
	 */
	public List<Vehicle> findAll() throws ServiceException {
		// TODO: récupérer tous les vehicules
		try {
			return vehicleDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	/**
	 * 
	 * @param clientId
	 * @return liste d'un véhicule de la BDD correspondant à une recherche d'un ID client
	 * @throws ServiceException
	 */
	public List<Vehicle> findVehicleByClientId(long clientId) throws ServiceException {
		// TODO: récupérer un vehicle par son id client

		try {
			List<Vehicle> vehicle = vehicleDao.findVehicleByClientId(clientId);
			return vehicle;
			
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	/**
	 * 
	 * @param reservationId
	 * @return liste d'un véhicule de la BDD correspondant à une recherche d'un ID reservation
	 * @throws ServiceException
	 */
	public List<Vehicle> findVehicleByReservationId(long reservationId) throws ServiceException {
		// TODO: récupérer un vehicle par son id reservation

		try {
			List<Vehicle> vehicle = vehicleDao.findVehicleByClientId(reservationId);
			return vehicle;
			
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	/**
	 * 
	 * @param vehicle
	 * @return suppression du véhicule
	 * @throws ServiceException
	 */
	public long delete (Vehicle vehicle) throws ServiceException {
		// TODO : supprimer un vehicule

			try {
				return vehicleDao.delete(vehicle);
			} catch (DaoException e) {
				throw new ServiceException();
			}
	}
	
	/**
	 * 
	 * @return le nombre de véhicules de la BDD
	 * @throws ServiceException
	 */
	public int count() throws ServiceException {
		//Compte les vehicules
		try {
			return vehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		
	}	
	
	
}
