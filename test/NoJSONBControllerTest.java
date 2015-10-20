
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.DELETE;
import static play.test.Helpers.GET;
import static play.test.Helpers.POST;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.status;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

public class NoJSONBControllerTest extends BaseTest {

    @Test
    public void noJsonbSelectTest() {

	FakeRequest fakeRequest = fakeRequest(POST, "/nojsonb/signUp");
	JsonNode requestBody = Json.toJson(noJsonbClient);
	fakeRequest.withJsonBody(requestBody);

	Result result = callAction(controllers.data.impl.routes.ref.NoJSONBController.signUp(), fakeRequest);
	    
	assertEquals(OK, status(result));
	
	requestBody = noJsonbJson;
	fakeRequest = fakeRequest(POST, "/nojsonb/createNote/" + noJsonbClient.getEmail());
	fakeRequest.withJsonBody(requestBody);
	
	result = callAction(controllers.data.impl.routes.ref.NoJSONBController.createNote(noJsonbClient.getEmail()), fakeRequest);
	    
	assertEquals(OK, status(result));

	fakeRequest = fakeRequest(GET, "/nojsonb/notes/" + noJsonbClient.getEmail());

	result = callAction(controllers.data.impl.routes.ref.NoJSONBController.getNotes(noJsonbClient.getEmail(), String.valueOf(3)), fakeRequest);

	assertFalse(contentAsString(result).isEmpty());

	fakeRequest = fakeRequest(DELETE, "/nojsonb/delete/" + noJsonbClient.getEmail());

	result = callAction(controllers.data.impl.routes.ref.NoJSONBController.delete(jsonbClient.getEmail()), fakeRequest);

	assertEquals(OK, status(result));
    }
}