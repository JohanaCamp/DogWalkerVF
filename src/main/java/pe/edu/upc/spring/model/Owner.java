package pe.edu.upc.spring.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "owner")
public class Owner implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idOwner;

	@NotEmpty(message = "Ingrese su nombre")
	@Size(max=50, message = "Ingrese un nombre válido")
	@Column(name = "firstNames", length = 50, nullable = false)
	private String firstNames;

	@NotEmpty(message = "Ingrese su apellido")
	@Size(max=50, message = "Ingrese un apellido válido")
	@Column(name = "lastNames", length = 50, nullable = false)
	private String lastNames;
	
	@NotEmpty(message = "Ingrese su correo")
	@Size(max=40, message = "Ingrese el correo electrónico correcto")
	@Email (message = "Ingrese el correo electrónico correcto")
	@Column(name = "email", length = 40, nullable = false)
	private String email;

	@NotEmpty(message = "Ingrese su contraseña")
	@Column(name = "password", length = 255, nullable = false)
	private String password;

	@NotNull(message = "Ingrese su fecha de nacimiento")
	@Past(message = "Fecha de nacimiento incorrecta")
	@Temporal(TemporalType.DATE)
	@Column(name = "dateOfBirth")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;

	@NotNull(message = "Seleccione su distrito")
	@ManyToOne
	@JoinColumn(name = "idDistrict", nullable = false)
	private District district;

	@NotEmpty(message = "Ingrese su dirección")
	@Size(max=150, message = "Solo se permite 150 caracteres")
	@Column(name = "address", length = 150, nullable = false)
	private String address;

	public Owner() {
		super();
	}

	public Owner(int idOwner, String firstNames, String lastNames, String email, String password, Date dateOfBirth,
			District district, String address) {
		super();
		this.idOwner = idOwner;
		this.firstNames = firstNames;
		this.lastNames = lastNames;
		this.email = email;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.district = district;
		this.address = address;
	}

	public int getIdOwner() {
		return idOwner;
	}

	public void setIdOwner(int idOwner) {
		this.idOwner = idOwner;
	}

	public String getFirstNames() {
		return firstNames;
	}

	public void setFirstNames(String firstNames) {
		this.firstNames = firstNames;
	}

	public String getLastNames() {
		return lastNames;
	}

	public void setLastNames(String lastNames) {
		this.lastNames = lastNames;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
