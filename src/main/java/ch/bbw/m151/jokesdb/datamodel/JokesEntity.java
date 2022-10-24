package ch.bbw.m151.jokesdb.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jokes")
public class JokesEntity {

	@Id
	@GeneratedValue
	int id;

	@Column(nullable = false)
	String joke;

	public JokesEntity setJoke(String joke) {
		this.joke = joke;
		return this;
	}

	public int getId() {
		return id;
	}

	public String getJoke() {
		return joke;
	}
}
