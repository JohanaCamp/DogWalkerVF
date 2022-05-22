package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "personality")
public class Personality implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPersonality;

	@Column(name = "name", length = 60, nullable = false)
	private String name;

	public Personality() {
		super();
	}

	public Personality(int idPersonality, String name) {
		super();
		this.idPersonality = idPersonality;
		this.name = name;
	}

	public int getIdPersonality() {
		return idPersonality;
	}

	public void setIdPersonality(int idPersonality) {
		this.idPersonality = idPersonality;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
