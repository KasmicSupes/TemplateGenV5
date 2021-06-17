package com.example.configuringTemplates.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.configuringTemplates.entity.Template;

public interface TemplateRepository extends CrudRepository<Template,Integer>{
	

	//List<Template> findByOrganisationidAndType(String organisationid,String type);
	Boolean existsByOrganisationidAndType(String organisationid,String type);
}
