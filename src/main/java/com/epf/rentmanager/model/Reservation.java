package com.epf.rentmanager.model;

import java.sql.Date;
public class Reservation {
	private long 	id;
	private Client 	client;
	private Vehicle vehicle;
	private Date 	debut;
	private Date 	fin;


public Reservation() {
	
}
/**
 * @return définition des attributs de l'objet réservation
 * @param id
 * @param client
 * @param vehicle
 * @param debut
 * @param fin
 */
public Reservation(long id, Client client, Vehicle vehicle, Date debut, Date fin) {
	this.id 			= id;
	this.client			= client;
	this.vehicle	 	= vehicle;
	this.debut 			= debut;
	this.fin 			= fin;
}


@Override
public String toString() {
	return "Reservation [id = " + id + ",client = " + client + ", vehicle = " + vehicle + ", debut = " + debut + ", fin = "
 + fin + " ]";
 }

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public Date getDebut() {
	return debut;
}

public void setDebut(Date debut) {
	this.debut = debut;
}

public Date getFin() {
	return fin;
}

public void setFin(Date fin) {
	this.fin = fin;
}

public Client getClient() {
	return client;
}

public void setClient(Client client) {
	this.client = client;
}

public Vehicle getVehicle() {
	return vehicle;
}

public void setVehicle(Vehicle vehicle) {
	this.vehicle = vehicle;
}


}
