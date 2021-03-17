package com.epf.rentmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;

@Service
public class ClientService {

	private ClientDao clientDao;

	
	
	@Autowired
	private ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	


	
	public long create(Client client) throws ServiceException {
		// TODO: créer un client
		if (client.getNom().isEmpty()) {
			throw new ServiceException();
		}
		if (client.getPrenom().isEmpty()) {
			throw new ServiceException();
		}

		try {
			client.setNom(client.getNom().toUpperCase());
			return clientDao.create(client);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ServiceException();
		}
		
	}
	
	public long update(Client client) throws ServiceException {
		// TODO: modifier un client
		
		if (client.getNom().isEmpty()) {
			throw new ServiceException();
		}
		if (client.getPrenom().isEmpty()) {
			throw new ServiceException();
		}
		
		try {
			client.setNom(client.getNom().toUpperCase());
			return clientDao.update(client);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ServiceException();
		}
		
	}

	public Client findById(long id) throws ServiceException {
		// TODO: récupérer un client par son id
		Client client;
		try {
			Optional<Client> optclient = clientDao.findById(id);
			if(optclient.isPresent()) {
				client = optclient.get();
			} else {
				throw new ServiceException();
			}
			//System.out.println("Service ok");
			//System.out.println(client);
			return client;
			
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public List<Client> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			return clientDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	public List<Client> findClientByVehicleId(long vehicleId) throws ServiceException {
		// TODO: récupérer un client par son id véhicule

		try {
			List<Client> client = clientDao.findClientByVehicleId(vehicleId);
			return client;
			
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	public List<Client> findClientByReservationId(long reservationId) throws ServiceException {
		// TODO: récupérer un client par son id reservation

		try {
			List<Client> client = clientDao.findClientByReservationId(reservationId);
			return client;
			
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	public long delete (Client client) throws ServiceException {
		// TODO : supprimer un client

			try {
				return clientDao.delete(client);
			} catch (DaoException e) {
				throw new ServiceException();
			}
	}
	
	public int count() throws ServiceException {
		//Compte les clients
		try {
			return clientDao.count();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		
	}	
}
