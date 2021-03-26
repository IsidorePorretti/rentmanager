package com.epf.rentmanager.model;

public class Vehicle {
	private long 	id;
	private String 	constructeur;
	private String 	modele;
	private short	nb_places;

	
	public Vehicle() {
		
	}
	
	/**
	 * @return définition des attributs de l'objet véhicule
	 * @param id
	 * @param nb_places
	 */
	public Vehicle(long id, short nb_places) {
		this.id 		= id;
		this.nb_places 	= nb_places;	
	}
	
	@Override
	public String toString() {
		return "Vehicle [id = " + id + ",constructeur = " + constructeur + ", modele = " + modele + ", nb_places = " + nb_places + "]";
	 }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getConstructeur() {
		return constructeur;
	}

	public void setConstructeur(String constructeur) {
		this.constructeur = constructeur;
	}

	public String getModele() {
		return modele;
	}

	public void setModele(String modele) {
		this.modele = modele;
	}

	public short getNb_places() {
		return nb_places;
	}

	public void setNb_places(short nb_places) {
		this.nb_places = nb_places;
	}

	
	
	
	
}
