package com.qaautomated.controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Storage2")
public class AllToysData {
    @Column(name="toys_name")
	private String toys_name;
    
    @Column(name="id")
	private String id;
 
    @Column(name="price")
	private int price;
    @Column(name="category")
	private String category;
    @Id
	public String getToys_name() {
		return toys_name;
	}
	public void setToys_name(String course_name) {
		this.toys_name = course_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
}
