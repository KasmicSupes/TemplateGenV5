package com.example.configuringTemplates.dtos;

public class MailRequest {

	private Integer userId;
	private String templateType;
	private Integer organisationId;
	private String language;
	private String productName;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public Integer getOrganisationId() {
		return organisationId;
	}
	public void setOrganisationId(Integer organisationId) {
		this.organisationId = organisationId;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public MailRequest(Integer userId, String templateType, Integer organisationId, String language, String productName,
			String from) {
		super();
		this.userId = userId;
		this.templateType = templateType;
		this.organisationId = organisationId;
		this.language = language;
		this.productName = productName;
	}
	
	

}
