package model.jsonb;

import hibernate.JSONBUserType;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.databind.JsonNode;

import model.UUIDEntity;
import model.Validatable;


@TypeDef(name = "jsonb", typeClass = JSONBUserType.class, parameters = {
	  @Parameter(name = JSONBUserType.CLASS,
	      value = "model.jsonb.ClientWithJsonb")})
@Entity
@Table
public class ClientWithJsonb extends UUIDEntity implements Validatable {
	
	@Type(type = "jsonb")
	private JsonNode model;

	public JsonNode getModel() {
		return model;
	}

	public void setModel(JsonNode model) {
		this.model = model;
	}	
}
