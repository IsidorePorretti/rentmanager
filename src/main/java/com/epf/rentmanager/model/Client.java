package com.epf.rentmanager.model;
//mettre attribut, constructeur et guetteur setter

import java.sql.Date;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;

/**
 * 
 * @author porretti
 *
 */
public class Client {
	private long	id;
	private String 	nom;
	private String 	prenom;
	private String	email;
	private Date 	naissance;
	
	
	public Client () {
		
	}
	
	/**
	 * @return définition de l'objet client
	 * @param id
	 * @param nom
	 * @param prenom
	 * @param email
	 * @param naissance
	 */
	public Client (long id, String nom, String prenom, String email, Date naissance) {
		this.id 		= id;
		this.naissance 	= naissance;
	}

	@Override
	public String toString() {
		return "Client [id = " + id + ",nom = " + nom + ",prenom = " + prenom + ",email = " + email + ",naissance = " + naissance + " ]";
			}



	public void setId(long id) {
		this.id = id;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getNaissance() {
		return naissance;
	}

	public void setNaissance(Date date) {
		this.naissance = date;
	}

	public long getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getEmail() {
		return email;
	}


}