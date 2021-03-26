package com.epf.rentmanager.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

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
	
	/**
	 * 
	 * @param email
	 * @return l'email est-il valide ?
	 */
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
    
    public boolean check_if_email_exists(Client client) throws ServiceException
    { 
    		
    		try {
    			Optional<Client> opt_email = clientDao.findEmail(client.getEmail());
    			if(opt_email.isPresent()) {
    				return true;
    			} else {
    				return false;
    			}

    		} catch (DaoException e) {
    			System.out.println(e.getMessage());
    			throw new ServiceException();
    		}
    } 
    
    /**
     * 
     * @param naissance
     * @return l'age du client
     */
    public int getAge(Date naissance) {
        int age = 0;
        try {
        		Calendar maintenant = Calendar.getInstance();
        	    Calendar ddn = Calendar.getInstance();

        	    ddn.setTime(naissance);

        	    if (ddn.after(maintenant)) 
        	    {
        	        throw new IllegalArgumentException("Date de naissance ulterieure a aujourd'hui");
        	    }
        	    age = maintenant.get(Calendar.YEAR) - ddn.get(Calendar.YEAR);

        	    if (maintenant.get(Calendar.DAY_OF_YEAR) < ddn.get(Calendar.DAY_OF_YEAR)) 
        	    {
        	        age--;
        	    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //System.out.println(age);
        return age;
}
    
    
	/**
	 * 
	 * @param client
	 * @return création du client
	 * @throws ServiceException
	 */
	public long create(Client client) throws ServiceException {
		// TODO: créer un client
		if (client.getNom().isEmpty()) {
			throw new ServiceException();
		}
		if ((client.getNom()).length() < 3){
			throw new ServiceException();
		}
			
		if (client.getPrenom().isEmpty()) {
			throw new ServiceException();
		}
		if ((client.getPrenom()).length() < 3){
			throw new ServiceException();
		}
		if (!isValid(client.getEmail())){
			throw new ServiceException();
		}
		if (getAge(client.getNaissance()) < 18){
			throw new ServiceException();
		}
		if (check_if_email_exists(client)) {
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
	
	/**
	 * 
	 * @param client
	 * @return modification du client
	 * @throws ServiceException
	 */
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

	/**
	 * 
	 * @param id
	 * @return une liste avec un client de la BDD correspondant à la recherche d'un id client
	 * @throws ServiceException
	 */
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

	/**
	 * 
	 * @return une liste de tous les clients de la BDD
	 * @throws ServiceException
	 */
	public List<Client> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			return clientDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	/**
	 * 
	 * @param vehicleId
	 * @return une liste avec un client de la BDD correspondant à la recherche par rapport à l'ID de la voiture
	 * @throws ServiceException
	 */
	public List<Client> findClientByVehicleId(long vehicleId) throws ServiceException {
		// TODO: récupérer un client par son id véhicule

		try {
			List<Client> client = clientDao.findClientByVehicleId(vehicleId);
			return client;
			
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	/**
	 * 
	 * @param reservationId
	 * @return une liste avec un client de la BDD correspondant à la recherche par rapport à l'ID de la reservation
	 * @throws ServiceException
	 */
	public List<Client> findClientByReservationId(long reservationId) throws ServiceException {
		// TODO: récupérer un client par son id reservation

		try {
			List<Client> client = clientDao.findClientByReservationId(reservationId);
			return client;
			
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	/**
	 * 
	 * @param client
	 * @return suppression du client
	 * @throws ServiceException
	 */
	public long delete (Client client) throws ServiceException {
		// TODO : supprimer un client

			try {
				return clientDao.delete(client);
			} catch (DaoException e) {
				throw new ServiceException();
			}
	}
	
	/**
	 * 
	 * @return le nombre de client de la BDD
	 * @throws ServiceException
	 */
	public int count() throws ServiceException {
		//Compte les clients
		try {
			return clientDao.count();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		
	}	

    	
}
