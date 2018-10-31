package com.apap.tutorial7.rest;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DealerDetail {
	
	private String status;
	
	@JsonProperty("building-license")
	private Integer buildingLicense;
	
	@JsonProperty("valid-until")
	private Date validUntil;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getBuildingLicense() {
		return buildingLicense;
	}
	public void setBuildingLicense(Integer buildingLicense) {
		this.buildingLicense = buildingLicense;
	}
	public Date getValidUntil() {
		return validUntil;
	}
	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}
}
