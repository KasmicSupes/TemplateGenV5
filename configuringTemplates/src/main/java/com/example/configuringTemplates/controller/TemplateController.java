package com.example.configuringTemplates.controller;

import java.io.IOException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.configuringTemplates.dtos.TemplateUpdateDto;
import com.example.configuringTemplates.entity.Template;
import com.example.configuringTemplates.models.AuthenticationRequest;
import com.example.configuringTemplates.models.AuthenticationResponse;
import com.example.configuringTemplates.repository.TemplateRepository;
import com.example.configuringTemplates.service.EmailService;
import com.example.configuringTemplates.service.MyUserDetailsService;
import com.example.configuringTemplates.service.TemplateService;
import com.example.configuringTemplates.utils.JwtUtil;
import com.example.configuringTemplates.dtos.MailRequest;
import com.example.configuringTemplates.dtos.MailResponse;




@RestController
public class TemplateController {

	@Autowired
	private EmailService service;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private TemplateRepository tr;
	
	@GetMapping("/templates")
	public Iterable<Template> getTemplates()
	{
		return templateService.findTemplates();
	}
	
	@PostMapping("/templates")
	public ResponseEntity<Template> addNewTemplate(@RequestBody Template template)
	{
		templateService.addTemplate(template);
		return new ResponseEntity<Template>(template,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/templates/{id}")
	public ResponseEntity delTemplate(@PathVariable Integer id)
	{
		templateService.delTemplate(id);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@GetMapping("/templatesi/{id}")
	public Template getTEmplate(@PathVariable Integer id)
	{
		return templateService.findTemplate(id);
	}
	@GetMapping("/templates/{oid}/{type}/{lan}")
	public Template getTEmplates(@PathVariable Integer oid,@PathVariable String type,@PathVariable String lan)
	{
		return templateService.findtid(oid,type,lan);
	}
	
	@PatchMapping("/templates/{id}")
    public ResponseEntity<Template>updateTemplate(@PathVariable("id") Integer id,@Valid @RequestBody TemplateUpdateDto templateUpdateDto) {
		templateService.updateTemplate(id, templateUpdateDto);
		return new ResponseEntity<Template>(templateService.findTemplate(id),HttpStatus.OK);
	}
	
	@RequestMapping(value="/authenticate",method=RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		try 
		{
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		}
		catch (BadCredentialsException e) 
		{
			throw new Exception("Incorrect username or password", e);
		}
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	@PostMapping("/sendingEmail")
	public MailResponse sendEmail(@RequestBody MailRequest request) throws IOException {
		return service.sendEmail(request);
	}
	@GetMapping("/t")
	public Template gets()
	{
		return tr.findByOrganisationIdAndTypeAndLanguage(2, "RESET", "es");
		//return tr.existsByTypeAndLanguage("RESET", "en");
		//return tr.existsByOrganisationIdAndType("O1","RESET");
		//return tr.findByType("RESET ");
	}

}
