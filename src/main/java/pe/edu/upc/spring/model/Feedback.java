package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "feedback")
public class Feedback implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idFeedback;

	@Column(name = "rating", nullable = false)
	private int rating;

	
	//@NotEmpty(message = "Ingrese su comentario")
	@Size(max=200, message = "Solo se permite como máximo 200 caracteres en el comentario")
	@Column(name = "comment", length = 200, nullable = false)
	private String comment;

	@ManyToOne
	@JoinColumn(name = "idOwner", nullable = false)
	private Owner owner;

	@ManyToOne
	@JoinColumn(name = "idWalker", nullable = false)
	private Walker walker;

	public Feedback() {
		super();
	}

	public Feedback(int idFeedback, int rating, String comment, Owner owner, Walker walker) {
		super();
		this.idFeedback = idFeedback;
		this.rating = rating;
		this.comment = comment;
		this.owner = owner;
		this.walker = walker;
	}

	public int getIdFeedback() {
		return idFeedback;
	}

	public void setIdFeedback(int idFeedback) {
		this.idFeedback = idFeedback;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Walker getWalker() {
		return walker;
	}

	public void setWalker(Walker walker) {
		this.walker = walker;
	}

}
