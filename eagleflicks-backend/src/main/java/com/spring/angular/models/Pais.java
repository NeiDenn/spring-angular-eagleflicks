package com.spring.angular.models;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_pais")
public class Pais {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pais")
	private Integer idPais;
	
	@Column(name = "nom_pais")
	private String nomPais;
	
	@JsonIgnore
	@OneToMany(mappedBy = "empleadoPais")
	private List<Empleado> listaPaises;
	
	public Pais(Integer idPais, String nomPais, List<Empleado> listaPaises) {
		super();
		this.idPais = idPais;
		this.nomPais = nomPais;
		this.listaPaises = listaPaises;
	}

	public Pais() {
		super();
	}

	public Integer getIdPais() {
		return idPais;
	}

	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}

	public String getNomPais() {
		return nomPais;
	}

	public void setNomPais(String nomPais) {
		this.nomPais = nomPais;
	}

	public List<Empleado> getListaPaises() {
		return listaPaises;
	}

	public void setListaPaises(List<Empleado> listaPaises) {
		this.listaPaises = listaPaises;
	}
}