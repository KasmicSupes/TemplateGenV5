package com.example.configuringTemplates.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.configuringTemplates.dtos.TemplateUpdateDto;
import com.example.configuringTemplates.entity.Template;
import com.example.configuringTemplates.exception.DuplicateKeysFoundException;
import com.example.configuringTemplates.exception.NotValidPlaceholderException;
import com.example.configuringTemplates.exception.TemplateNotFoundException;
import com.example.configuringTemplates.repository.TemplateRepository;
import com.example.configuringTemplates.utils.JsonNullableUtils;

import static java.util.Map.entry;



@Service
@Transactional
public class TemplateService implements TemplateServiceI{
	
	@Autowired
	private TemplateRepository templateRepository;
	private List<String> finalPlaceholders=Arrays.asList("${userName}","${organisationDomain}","${expireAt}","${productLogo}","${productName}");

	@Override
	public Iterable<Template> findTemplates()
	{
		return templateRepository.findAll();
	}
	
	@Override
	public ArrayList<String> findPlaceholders(String content)
	{
		ArrayList<String> allMatches = new ArrayList<String>();
		Matcher m = Pattern.compile("\\$\\{([^}]*)\\}").matcher(content);
		while (m.find()) 
		{
			int flag=0;
			int fixpHL=finalPlaceholders.size();
			for(int i=0;i<fixpHL;i++)
			{
				
				if(finalPlaceholders.get(i).equals(m.group()))
				{
					allMatches.add(m.group());
					flag=1;
				}
			}
			if(flag==0)
			{
				throw new NotValidPlaceholderException("Placeholder not valid. Please check again and try");
			}
			flag=0;
		}
		for (int j=0;j<allMatches.size();j++)
		{
			String temp=allMatches.get(j).substring(2);
			temp=temp.substring(0, temp.length() - 1);
			allMatches.set(j, temp);
		}
		return allMatches;
	}

	@Override
	public String addTemplate(Template template) throws DuplicateKeysFoundException,NotValidPlaceholderException
	{
		Integer oid=template.getOrganisationId();
		String ty=template.getType();
		String lan=template.getLanguage();
		if(templateRepository.existsByOrganisationIdAndTypeAndLanguage(oid, ty,lan)==true)
		{
			throw new DuplicateKeysFoundException("Combination of fields OrganisationId,Type and Language with current values already exists");
		}
		else {
			template.setPlaceholders(findPlaceholders(template.getContent()));
			templateRepository.save(template);
			return "saved";
		}
	}
	
	@Override
	public Template findTemplate(Integer id)
	{
		return templateRepository.findById(id).orElseThrow(() -> new TemplateNotFoundException("Template not found with id :"+id));
		
	}
	
	@Override
	public String delTemplate(Integer id)
	{
		templateRepository.findById(id).orElseThrow(() -> new TemplateNotFoundException("Template not found with id :"+id));
		templateRepository.deleteById(id);
		return "deleted";
				
	}
	
	@Override
    public ResponseEntity<Void> updateTemplate(Integer id,TemplateUpdateDto templateUpdateDto) {
    	Template template = templateRepository.findById(id).orElseThrow(() -> new TemplateNotFoundException("Template not found with id :"+id));
        JsonNullableUtils.changeIfPresent(templateUpdateDto.getOrganisationId(), template::setOrganisationId);
        JsonNullableUtils.changeIfPresent(templateUpdateDto.getType(), template::setType);
        JsonNullableUtils.changeIfPresent(templateUpdateDto.getContent(), template::setContent);
        JsonNullableUtils.changeIfPresent(templateUpdateDto.getSubject(), template::setSubject);
        JsonNullableUtils.changeIfPresent(templateUpdateDto.getLanguage(), template::setLanguage);
        template.setPlaceholders(findPlaceholders(template.getContent()));
        templateRepository.save(template);
        return ResponseEntity.noContent().build();

    }
	public Template findtid(Integer organisationId,String type,String language) throws TemplateNotFoundException
	{
		if(templateRepository.findByOrganisationIdAndTypeAndLanguage(organisationId, type, language)==null) {
			throw new TemplateNotFoundException("Template not found with given data");
		}
		else {
			return templateRepository.findByOrganisationIdAndTypeAndLanguage(organisationId, type, language);
		}
	}
}
