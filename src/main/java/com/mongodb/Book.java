package com.mongodb;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
	@Id
	@Column(name = "_id")
	private String id;
	private String title;

	@PrePersist
	void ensureId() {
		if (id == null) {
			this.id = new org.bson.types.ObjectId().toHexString();
		}
	}

	public Book(String title) {
 		this.title = title;
	}

	public Book() {

	}


	public String getTitle() {
		return title;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
