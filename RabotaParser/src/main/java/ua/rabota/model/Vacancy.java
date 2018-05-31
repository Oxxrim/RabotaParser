package ua.rabota.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "Vacancies")
public class Vacancy {

	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "company")
	private String company;
	
	@Column(name = "info", length = 10000)
	private String info;
	
	@Column(name = "description", length = 10000)
	private String description;
	
	@Column(name = "language")
	private String language;
}
