package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "race")
public class Race implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idRace;

	@Column(name = "name", length = 60, nullable = false)
	private String name;

	public Race() {
		super();
	}

	public Race(int idRace, String name) {
		super();
		this.idRace = idRace;
		this.name = name;
	}

	public int getIdRace() {
		return idRace;
	}

	public void setIdRace(int idRace) {
		this.idRace = idRace;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
