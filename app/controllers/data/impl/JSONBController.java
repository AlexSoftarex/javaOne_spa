package controllers.data.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import model.jsonb.ClientWithJsonb;
import play.db.DB;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import validators.DataValidator;
import validators.json.JsonValidator;
import validators.json.JsonValidatorJava;

public class JSONBController extends Controller {

    private static JsonValidator jsonValidator = new JsonValidatorJava();

    @Transactional(readOnly = true)
    public static Result signIn(String userEmail) throws Exception {

	String sql = "select * from clientwithjsonb where model->>'email' = ?";

	List<ClientWithJsonb> jsonNodes = JPA.em().createNativeQuery(sql, ClientWithJsonb.class)
		.setParameter(1, userEmail).getResultList();

	return ok(Json.toJson(jsonNodes.size() > 0 ? jsonNodes.get(0) : ""));
    }

    @Transactional
    public static Result signUp() throws Exception {

	JsonNode user = request().body().asJson();

	DataValidator.Result validationResult = jsonValidator.validate(jsonValidator.createData(user), "client");

	if (validationResult.isSuccess()) {

	    ClientWithJsonb client = new ClientWithJsonb();
	    client.setModel(request().body().asJson());
	    JPA.em().persist(client);
	    return ok(Json.toJson(client));

	} else {
	    return badRequest(validationResult.getMessage());
	}
    }

    @Transactional
    public static Result createNote(String userEmail) throws Exception {

	JsonNode note = request().body().asJson();

	String sql = "select * from clientwithjsonb where model->>'email' = ?";

	ClientWithJsonb clientWithJsonb = (ClientWithJsonb) JPA.em().createNativeQuery(sql, ClientWithJsonb.class)
		.setParameter(1, userEmail).getSingleResult();

	JsonNode newNode = clientWithJsonb.getModel().deepCopy();
	((ObjectNode) newNode).put("notes", note);
	clientWithJsonb.setModel(newNode);

	JPA.em().persist(clientWithJsonb);

	return ok();
    }

    @Transactional(readOnly = true)
    public static Result getNotes(String userEmail, String level) throws Exception {

	
	String qLevel = "note text " + level;
	
	String sql = "select * FROM (select jsonb_array_elements(model -> 'notes') "
		+ "from clientwithjsonb WHERE model ->> 'email' = ?) as item WHERE item.jsonb_array_elements @> '{\"text\": \"" + qLevel + "\"}'";

	PreparedStatement ps = DB.getConnection().prepareStatement(sql);
	ps.setString(1, userEmail);
	ResultSet rs = ps.executeQuery();

	List<JsonNode> jsonList = new ArrayList<>();

	ResultSetMetaData rsmd = rs.getMetaData();

	int i = 1;
	while (rs.next()) {

	    jsonList.add(Json.parse(rs.getString(rsmd.getColumnName(i))));
	}

	return ok(Json.toJson(jsonList.size()));
    }

    @Transactional
    public static Result delete(String userEmail) throws Exception {

	String sql = "delete from clientwithjsonb where model->>'email' = ?";

	PreparedStatement ps = DB.getConnection().prepareStatement(sql);
	ps.setString(1, userEmail);
	ps.executeUpdate();

	return ok();
    }
}
