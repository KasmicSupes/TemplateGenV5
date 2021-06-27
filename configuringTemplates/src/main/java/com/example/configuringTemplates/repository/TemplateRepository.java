package com.example.configuringTemplates.repository;


import org.springframework.data.repository.CrudRepository;

import com.example.configuringTemplates.entity.Template;

public interface TemplateRepository extends CrudRepository<Template,Integer>{
	

	Boolean existsByOrganisationIdAndTypeAndLanguage(Integer organisationid,String type,String language);
	Template findByOrganisationIdAndTypeAndLanguage(Integer organisationid,String type,String language);
	Boolean existsByTypeAndLanguage(String type,String language);
	Boolean existsByOrganisationIdAndType(Integer organisationid,String type);
	Template findByOrganisationIdAndType(Integer id,String type);
	Template findByType(String typ);
	Template findByOrganisationIdAndLanguage(Integer id,String lan);
}
