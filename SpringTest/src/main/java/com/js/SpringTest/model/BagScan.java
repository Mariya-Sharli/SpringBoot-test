package com.js.SpringTest.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
@Entity
public class BagScan {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String destinationGate;
	private String locationScanned;
	private LocalDateTime scanTime;
	@PrePersist
	protected void onScan()
	{
		this.scanTime = LocalDateTime.now();
	}
	public Long getId() {
		return id;
	}
	void setId(Long id) {
		this.id = id;
	}
	String getDestinationGate() {
		return destinationGate;
	}
	void setDestinationGate(String destinationGate) {
		this.destinationGate = destinationGate;
	}
	public String getLocationScanned() {
		return locationScanned;
	}
	void setLocationScanned(String locationScanned) {
		this.locationScanned = locationScanned;
	}
	public LocalDateTime getScanTime() {
		return scanTime;
	}
	void setScanTime(LocalDateTime scanTime) {
		this.scanTime = scanTime;
	}
	public String getBagTagId() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
