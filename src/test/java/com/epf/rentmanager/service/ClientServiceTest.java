package com.epf.rentmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.utils.IOUtils;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {
	
	@Test //test unitaire du mail
	public void mail_fonctionne() {
	assertTrue(ClientService.isValid("test@live.fr"));
	}
	
	@Test
	public void mail_nefonctionnepas() {
	assertFalse(ClientService.isValid("testlivefr"));
	}
	
	@InjectMocks
	private ClientService clientService;
	@Mock
	private ClientDao clientDao;

	@Test(expected=ServiceException.class) 
	public void findAll_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
	
		// When
	when(this.clientDao.findAll()).thenThrow(new DaoException());
	
	// Then
	clientService.findAll();
	}
	
	
	@Test 
	public void findAll_fonctionne() throws DaoException, ServiceException {
		List<Client> listeclient = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse("2000-06-19", formatter);
		Date naissance = Date.valueOf(localDate);
		Client client1 = new Client();
		client1.setId(1);
		client1.setNom("PORRETTI");
		client1.setPrenom("Isidore");
		client1.setEmail("isidore.porretti@epfedu.fr");
		client1.setNaissance(naissance);
		Client client2 = new Client();
		listeclient.add(client1);
		listeclient.add(client2);
		
		// When
		Mockito.when(this.clientDao.findAll()).thenReturn(listeclient);
		// Then
		assertEquals(2, clientService.findAll().size()); 
		}
}