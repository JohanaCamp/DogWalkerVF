package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "district")
public class District implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idDistrict;

	@Column(name = "name", length = 60, nullable = false)
	private String name;

	public District() {
		super();
	}

	public District(int idDistrict, String name) {
		super();
		this.idDistrict = idDistrict;
		this.name = name;
	}

	public int getIdDistrict() {
		return idDistrict;
	}

	public void setIdDistrict(int idDistrict) {
		this.idDistrict = idDistrict;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
