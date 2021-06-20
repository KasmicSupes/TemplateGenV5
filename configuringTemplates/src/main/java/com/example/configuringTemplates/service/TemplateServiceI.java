package com.example.configuringTemplates.service;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

import com.example.configuringTemplates.dtos.TemplateUpdateDto;
import com.example.configuringTemplates.entity.Template;

public interface TemplateServiceI
{
	
	public Iterable<Template> findTemplates();
	public ArrayList<String> findPlaceholders(String content);
	public String addTemplate(Template template);
	public String delTemplate(Integer id);
	public Template findTemplate(Integer id);
	public ResponseEntity<Void> updateTemplate(Integer id,TemplateUpdateDto templateUpdateDto);	
}
