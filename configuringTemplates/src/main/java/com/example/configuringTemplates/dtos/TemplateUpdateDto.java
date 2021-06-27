package com.example.configuringTemplates.dtos; 

import java.util.ArrayList;

import org.openapitools.jackson.nullable.JsonNullable;

@SuppressWarnings("FieldMayBeFinal")
public class TemplateUpdateDto {
	private JsonNullable<Integer> id = JsonNullable.undefined();
	private JsonNullable<Integer> organisationId = JsonNullable.undefined();
    private JsonNullable<String> type = JsonNullable.undefined();
    private JsonNullable<String> content = JsonNullable.undefined();
    private JsonNullable<String> subject = JsonNullable.undefined();
    private JsonNullable<ArrayList<String>> placeholders = JsonNullable.undefined();
    private JsonNullable<String> language = JsonNullable.undefined();
    protected TemplateUpdateDto() {}
	public JsonNullable<Integer> getId() {
		return id;
	}
	public JsonNullable<Integer> getOrganisationId() {
		return organisationId;
	}
	public JsonNullable<String> getType() {
		return type;
	}
	public JsonNullable<String> getContent() {
		return content;
	}
	public JsonNullable<String> getSubject() {
		return subject;
	}
	public JsonNullable<ArrayList<String>> getPlaceholders() {
		return placeholders;
	}
	public JsonNullable<String> getLanguage() {
		return language;
	}
}
