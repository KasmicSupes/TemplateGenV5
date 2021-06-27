package com.example.configuringTemplates.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Organisation {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer organisationId;
	private String organisationName;
	private String organisationDomain;
	private String organisationAddress;
	private String organisationLogo;
	private String productId;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getOrganisationId() {
		return organisationId;
	}
	public void setOrganisationId(Integer organisationId) {
		this.organisationId = organisationId;
	}
	public String getOrganisationName() {
		return organisationName;
	}
	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}
	public String getOrganisationDomain() {
		return organisationDomain;
	}
	public void setOrganisationDomain(String organisationDomain) {
		this.organisationDomain = organisationDomain;
	}
	public String getOrganisationAddress() {
		return organisationAddress;
	}
	public void setOrganisationAddress(String organisationAddress) {
		this.organisationAddress = organisationAddress;
	}
	public String getOrganisationLogo() {
		return organisationLogo;
	}
	public void setOrganisationLogo(String organisationLogo) {
		this.organisationLogo = organisationLogo;
	}
	

}