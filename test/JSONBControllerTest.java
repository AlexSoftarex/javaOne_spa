
import static play.test.Helpers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

public class JSONBControllerTest extends BaseTest {

    
    @Test
    public void jsonbSelectTest() {

	FakeRequest fakeRequest = fakeRequest(POST, "/jsonb/signUp");
	JsonNode requestBody = Json.toJson(jsonbClient);
	fakeRequest.withJsonBody(requestBody);
	
	Result result = callAction(controllers.data.impl.routes.ref.JSONBController.signUp(), fakeRequest);
	assertEquals(OK, status(result));

	fakeRequest = fakeRequest(POST, "/jsonb/createNote/" + jsonbClient.getEmail());
	requestBody = jsonbJson;
	fakeRequest.withJsonBody(requestBody);
	
	result = callAction(controllers.data.impl.routes.ref.JSONBController.createNote(jsonbClient.getEmail()), fakeRequest);
	assertEquals(OK, status(result));
	
	fakeRequest.withJsonBody(requestBody);
	fakeRequest = fakeRequest(GET, "/jsonb/notes/" + jsonbClient.getEmail());
	
	result = callAction(
		controllers.data.impl.routes.ref.JSONBController.getNotes(jsonbClient.getEmail(), String.valueOf(3)),
		fakeRequest);

	assertFalse(contentAsString(result).isEmpty());

	fakeRequest = fakeRequest(DELETE, "/jsonb/delete/" + jsonbClient.getEmail());
	result = callAction(controllers.data.impl.routes.ref.JSONBController.delete(jsonbClient.getEmail()),
		fakeRequest);

	assertEquals(OK, status(result));
    }
}
