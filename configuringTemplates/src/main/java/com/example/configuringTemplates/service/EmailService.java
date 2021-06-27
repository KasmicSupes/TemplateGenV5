package com.example.configuringTemplates.service;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.configuringTemplates.dtos.MailRequest;
import com.example.configuringTemplates.dtos.MailResponse;
import com.example.configuringTemplates.entity.Organisation;
import com.example.configuringTemplates.exception.TemplateNotFoundException;
import com.example.configuringTemplates.exception.UserNotFoundException;
import com.example.configuringTemplates.repository.OrganisationRepository;
import com.example.configuringTemplates.repository.ProductRepository;
import com.example.configuringTemplates.repository.UserRepository;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
@Service
public class EmailService implements EmailServiceI{

	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private Configuration config;
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private OrganisationRepository orgRepository;
	
	
	@Autowired
    private ProductRepository prodRepository;
	
	@Override
	public MailResponse generateEmail(MailRequest request, Map<String, Object> model) {
		MailResponse response = new MailResponse();
	    ArrayList<String> tempL;
	    Map<String, Object> tmodel = new HashMap<>();
		MimeMessage message = sender.createMimeMessage();
		try 
		{
			config.setDirectoryForTemplateLoading(new File("/Users/prelango/git/TemplateGenV5/configuringTemplates/src/main/resources/templates"));
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name());
			//FileWriter myWriter = new FileWriter("/Users/prelango/git/TemplateGenV5/configuringTemplates/src/main/resources/templates/tempfile.ftl");
			FileWriter myWriter2 = new FileWriter("/Users/prelango/git/TemplateGenV5/configuringTemplates/src/main/resources/templates/tempfile2.ftl");
			Integer tid=templateService.findtid( request.getOrganisationId(), request.getTemplateType(),request.getLanguage()).getId();
			String cont=templateService.findTemplate(tid).getContent();
			boolean contains=cont.contains("${productLogo}");
			boolean containstime=cont.contains("${expireAt}");
			if(containstime==true)
			{
				cont=cont.replace("${expireAt}","${expireAt?time}");
			}
			/*if(contains==true)
			{
				cont=cont.replace("${productLogo}","");
				
			}*/
			tmodel.put("content",cont);
			tmodel.put("OrganisationLogo","${OrganisationLogo}");
			tmodel.put("OrganisationAddress","${OrganisationAddress}");
			tmodel.put("OrganisationName","${OrganisationName}");
			
			Template nt = config.getTemplate("tempfile4.ftl");
			//Template nt = config.getTemplate("tempfile5.ftl");
			String nhtml = FreeMarkerTemplateUtils.processTemplateIntoString(nt, tmodel);
			System.out.print(nhtml);
			System.out.print(tmodel);
			myWriter2.write(nhtml);
		    myWriter2.close();
			/*myWriter.write("<!DOCTYPE html>\n"
		      		+ "<html lang=\"en\">\n"
		      		+ "<head>\n"
		      		+ "</head>\n"
		      		+ "<body>\n"
		      		+" 	   <img src=${productLogo} width=\"100\" height=\"100\">\n"
		      		+ "    <h2>"+cont+"!</h2>\n"
		      		+ "</body>\n"
		      		+ "</html>");
		      myWriter.close();*/
		 
			Template t = config.getTemplate("tempfile2.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
			helper.setTo(userRepository.findByUserId(request.getUserId()).getUserEmail());
			helper.setText(html, true);
			helper.setSubject(templateService.findTemplate(tid).getSubject());
			//helper.setFrom(request.getFrom());
			sender.send(message);

			response.setMessage("mail send to : " + userRepository.findByUserId(request.getUserId()).getUserEmail());
			response.setStatus(Boolean.TRUE);

		} 
		catch (MessagingException | IOException | TemplateException e) 
		{
			response.setMessage("Mail Sending failure : "+e.getMessage());
			response.setStatus(Boolean.FALSE);
		}

		return response;
	}
	@Override
	public MailResponse sendEmail(@RequestBody MailRequest request) throws IOException {
		Map<String, Object> model = new HashMap<>();
		Integer tid=templateService.findtid(request.getOrganisationId(),request.getTemplateType(), request.getLanguage()).getId();
		ArrayList<String> tempL=templateService.findTemplate(tid).getPlaceholders();
		Organisation org=orgRepository.findByOrganisationId(request.getOrganisationId());
		if(!request.getOrganisationId().equals(userRepository.findByUserId(request.getUserId()).getOrganisationId()))
		{
			throw new UserNotFoundException("User not found with id :"+request.getUserId());
		}
		model.put("OrganisationLogo","https://www.nextbigbrand.in/wp-content/uploads/2019/01/freshworks-feature.jpg");
		if(org.getOrganisationAddress()==null)
		{
			model.put("OrganisationAddress","2950 S. Delaware Street, Suite 201, San Mateo CA 94403");
			model.put("OrganisationName","Freshworks");
		}
		if(org.getOrganisationLogo()!=null)
		{
			model.put("OrganisationLogo",org.getOrganisationLogo());
		}
		if(org.getOrganisationAddress()!=null)
		{
			model.put("OrganisationAddress",org.getOrganisationAddress());
			model.put("OrganisationName",org.getOrganisationName());
		}
		
		model.put("productName",request.getProductName());
		for(int i=0;i<tempL.size();i++)
		{
			switch(tempL.get(i))
			{
				case "userName":
					model.put(tempL.get(i),userRepository.findByUserId(request.getUserId()).getUserName());
					break;
				case "organisationDomain":
					model.put(tempL.get(i),orgRepository.findByOrganisationId(userRepository.findByUserId(request.getUserId()).getOrganisationId()).getOrganisationDomain());
					break;
				case "productLogo":
					//model.put(tempL.get(i),prodRepository.findByProductName(request.getProductName()).getProductLogo());
					model.put("productLogo","<img src="+prodRepository.findByProductName(request.getProductName()).getProductLogo()+" "+"width=\"100\" height=\"100\">\n");
					//model.put(tempL.get(i),"logo");
					break;
				case "expireAt":
					Calendar now = Calendar.getInstance();
					now.add(Calendar.MINUTE, 30);
					model.put(tempL.get(i), now.getTime());
					break;
			}	
		}
		return generateEmail(request, model);
	}
}
