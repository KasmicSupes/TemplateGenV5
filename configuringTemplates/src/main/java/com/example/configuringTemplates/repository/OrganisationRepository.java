package com.example.configuringTemplates.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.configuringTemplates.entity.Organisation;
public interface OrganisationRepository extends CrudRepository<Organisation,Integer>{
	Organisation findByOrganisationId(Integer oid);
}