package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "character")
public class Character implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCharacter;

	@Column(name = "name", length = 60, nullable = false)
	private String name;

	public Character() {
		super();
	}

	public Character(int idCharacter, String name) {
		super();
		this.idCharacter = idCharacter;
		this.name = name;
	}

	public int getIdCharacter() {
		return idCharacter;
	}

	public void setIdCharacter(int idCharacter) {
		this.idCharacter = idCharacter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
