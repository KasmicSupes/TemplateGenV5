package com.example.configuringTemplates.entity;


import java.util.ArrayList;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;




@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"organisationid","type"})})
public class Template {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
    private String organisationid;
    private String type;
	private String content;
	private ArrayList<String> placeholders;
	public Integer getId() {
		return id;
	}
	public Template setId(Integer id) {
		this.id = id;
		return this;
	}
	public String getOrganisationid() {
		return organisationid;
	}
	public Template setOrganisationid(String organisationid) {
		this.organisationid = organisationid;
		return this;
	}
	public String getType() {
		return type;
	}
	public Template setType(String type) {
		this.type = type;
		return this;
	}
	public String getContent() {
		return content;
	}
	public Template setContent(String content) {
		this.content = content;
		return this;
	}
	public ArrayList<String> getPlaceholders() {
		return placeholders;
	}
	public Template setPlaceholders(ArrayList<String> placeholders) {
		this.placeholders = placeholders;
		return this;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return id == template.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
	
}
