package project1.models;

import java.sql.Timestamp;	

public class Ticket {
	private int id;
	private double amount;
	private Timestamp datesubmitted;
	private Timestamp dateresolved;
	private String description;
	private String imgaddr;
	private int author;
	private int resolver;
	private int status;
	private int type;
	
	public Ticket(int id, double amount, Timestamp datesubmitted, Timestamp dateresolved, String description,
			String imgaddr, int author, int resolver, int status, int type) {
		super();
		this.id = id;
		this.amount = amount;
		this.datesubmitted = datesubmitted;
		this.dateresolved = dateresolved;
		this.description = description;
		this.imgaddr = imgaddr;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}

	public Ticket () {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Timestamp getDatesubmitted() {
		return datesubmitted;
	}
	public void setDatesubmitted(Timestamp datesubmitted) {
		this.datesubmitted = datesubmitted;
	}
	public Timestamp getDateresolved() {
		return dateresolved;
	}
	public void setDateresolved(Timestamp dateresolved) {
		this.dateresolved = dateresolved;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImgaddr() {
		return imgaddr;
	}
	public void setImgaddr(String imgaddr) {
		this.imgaddr = imgaddr;
	}
	public int getAuthor() {
		return author;
	}
	public void setAuthor(int author) {
		this.author = author;
	}
	public int getResolver() {
		return resolver;
	}
	public void setResolver(int resolver) {
		this.resolver = resolver;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
