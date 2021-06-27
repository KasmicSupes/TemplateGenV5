package com.example.configuringTemplates.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.example.configuringTemplates.dtos.MailRequest;
import com.example.configuringTemplates.dtos.MailResponse;

public interface EmailServiceI {
	//public MailResponse generateEmail(MailRequest request, Map<String, Object> model,String html);
	public MailResponse sendEmail(@RequestBody MailRequest request) throws IOException;

	MailResponse generateEmail(MailRequest request, Map<String, Object> model);
}
