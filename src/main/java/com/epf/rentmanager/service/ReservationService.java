package com.epf.rentmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;

@Service
public class ReservationService {

	private ReservationDao reservationDao;

	@Autowired
	private ReservationService(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}
	
	
	public long create(Reservation reservation) throws ServiceException {
		// TODO: créer une reservation
		
		/*
		if (reservation.getClient().getId()	== 0) {
			throw new ServiceException();
		}
		if (reservation.getVehicle().getId() == 0) {
			throw new ServiceException();
		}
*/
		try {
			return reservationDao.create(reservation);
			
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ServiceException();
		}
		
	}
	
	public long update(Reservation reservation) throws ServiceException {
		// TODO: modifier une reservation
		
		/*
		if (reservation.getClient().getId()	== 0) {
			throw new ServiceException();
		}
		if (reservation.getVehicle().getId() == 0) {
			throw new ServiceException();
		}
*/
		try {
			return reservationDao.update(reservation);
			
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ServiceException();
		}
		
	}

	public List<Reservation> findResaByClientId(long client_id) throws ServiceException {
		// TODO: récupérer une reservation par son id client
		
		try {
			List<Reservation> reservation = reservationDao.findResaByClientId(client_id);
			return reservation;
			
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	public List<Reservation> findResaByVehicleId(long vehicle_id) throws ServiceException {
		// TODO: récupérer une reservation par son id client

		try {
			List<Reservation> reservation = reservationDao.findResaByVehicleId(vehicle_id);
			return reservation;
			
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	

	public List<Reservation> findById(long id) throws ServiceException {
		// TODO: récupérer une reservation par son id client

		try {
			List<Reservation> reservation = reservationDao.findById(id);
			//System.out.println("on est là en Service");
			return reservation;
			
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	

	public List<Reservation> findAll() throws ServiceException {
		// TODO: récupérer toutes les reservations
		try {
			return reservationDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	public long delete (Reservation	reservation) throws ServiceException {
		// TODO : supprimer une reservation

			try {
				return reservationDao.delete(reservation);
			} catch (DaoException e) {
				throw new ServiceException();
			}
	}
	
	public int count() throws ServiceException {
		//Compte les reservations
		try {
			return reservationDao.count();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		
	}	
	
}
