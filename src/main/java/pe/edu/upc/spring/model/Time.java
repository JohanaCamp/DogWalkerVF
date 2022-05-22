package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "time")
public class Time implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTime;

	@Column(name = "time", length = 60, nullable = false)
	private String time;
		
	@Column(name = "value", nullable = false)
	private double value;

	public Time() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public Time(int idTime, String time, double value) {
		super();
		this.idTime = idTime;
		this.time = time;
		this.value = value;
	}



	public int getIdTime() {
		return idTime;
	}

	public void setIdTime(int idTime) {
		this.idTime = idTime;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	

}
