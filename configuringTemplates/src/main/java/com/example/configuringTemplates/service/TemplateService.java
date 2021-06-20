package com.example.configuringTemplates.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.configuringTemplates.controller.DuplicateKeysFoundException;
import com.example.configuringTemplates.dtos.TemplateUpdateDto;
import com.example.configuringTemplates.entity.Template;
import com.example.configuringTemplates.exception.TemplateNotFoundException;
import com.example.configuringTemplates.repository.TemplateRepository;
import com.example.configuringTemplates.utils.JsonNullableUtils;





@Service
@Transactional
public class TemplateService implements TemplateServiceI{
	
	@Autowired
	private TemplateRepository templateRepository;
	private List<String> finalPlaceholders=Arrays.asList("${name}");
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
			int fixpHL=finalPlaceholders.size();
			for(int i=0;i<fixpHL;i++)
			{
				if(finalPlaceholders.get(i).equals(m.group()))
				{
					allMatches.add(m.group());
					System.out.println(m.group());
				}
			}
		}
		return allMatches;
	}

	@Override
	public String addTemplate(Template template) throws TemplateNotFoundException
	{
		String oid=template.getOrganisationid();
		String ty=template.getType();
		//List<Template> dum;
		//dum=templateRepository.findByOrganisationidAndType(oid, ty);
		if(templateRepository.existsByOrganisationidAndType(oid, ty)==true)
		{
			throw new DuplicateKeysFoundException("Fields type and organisationid with value"+" "+template.getType()+" "+"and"+" "+
		template.getOrganisationid()+" "+"exists");
		}
		/*if(dum.size()==1)
		{
			throw new TemplateNotFoundException("sdsd :");
		}*/
		else {
			template.setPlaceholders(findPlaceholders(template.getContent()));
			templateRepository.save(template);
			return "saved";
		}
	}
	@Override
	public Template findTemplate(Integer id)
	{
		return templateRepository.findById(id).orElseThrow(() -> new TemplateNotFoundException("User not found with id :"+id));
		
	}
	@Override
	public String delTemplate(Integer id)
	{
		templateRepository.findById(id).orElseThrow(() -> new TemplateNotFoundException("User not found with id :"+id));
		templateRepository.deleteById(id);
		return "deleted";
				
	}
	@Override
    public ResponseEntity<Void> updateTemplate(Integer id,TemplateUpdateDto templateUpdateDto) {
    	Template template = templateRepository.findById(id).orElseThrow(() -> new TemplateNotFoundException("User not found with id :"+id));
        JsonNullableUtils.changeIfPresent(templateUpdateDto.getOrganisationid(), template::setOrganisationid);
        JsonNullableUtils.changeIfPresent(templateUpdateDto.getType(), template::setType);
        JsonNullableUtils.changeIfPresent(templateUpdateDto.getContent(), template::setContent);
        template.setPlaceholders(findPlaceholders(template.getContent()));
        templateRepository.save(template);
        return ResponseEntity.noContent().build();

    }
}
