package controllers.data.impl;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import model.Validatable;
import model.nojsonb.Client;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import model.nojsonb.Note;

public class NoJSONBController extends Controller {

    @Transactional(readOnly = true)
    public static Result signIn(String userEmail) throws Exception {

	List<Client> client = JPA.em().createQuery("from Client where email = :email", Client.class)
		.setParameter("email", userEmail).getResultList();

	return ok(Json.toJson(client.size() > 0 ? client.get(0) : ""));
    }

    public static Result signUp() throws Exception {

	Client client = Json.fromJson(request().body().asJson(), Client.class);

	Set<ConstraintViolation<Validatable>> validationResult = client.validate();

	if (validationResult.isEmpty()) {

	    JPA.withTransaction(() -> {
		JPA.em().persist(client);
	    });

	    return ok();

	} else {

	    StringBuilder sb = new StringBuilder();

	    String errorPattern = "Error: property: [%s], value: [%s], message: [%s] ";

	    validationResult.forEach(item -> {

		sb.append(
			String.format(errorPattern, item.getPropertyPath(), item.getInvalidValue(), item.getMessage()));

	    });
	}

	return badRequest();
    }

    @Transactional
    public static Result createNote(String userEmail) throws Exception {

	Client client = JPA.em().createQuery("from Client where email = :email", Client.class)
		.setParameter("email", userEmail).getSingleResult();

	Note mainNote = Json.fromJson(request().body().asJson(), Note.class);

	mainNote.setClient(client);

	setMainNote(mainNote, client);

	JPA.em().persist(mainNote);

	return ok();
    }

    private static void setMainNote(Note mainNote, Client client) {

	for (Note item : mainNote.getChildren()) {
	    setMainNote(item, client);
	    item.setParentNote(mainNote);
	    item.setClient(client);
	}
    }

    @Transactional(readOnly = true)
    public static Result getNotes(String userEmail, String level) throws Exception {

	String levelText = "note text " + level;

	List<?> result = JPA.em()
		.createQuery("select n from Note n inner join n.client c where c.email = :email and n.text = :text",
			Note.class)
		.setParameter("email", userEmail).setParameter("text", levelText).getResultList();

	return ok(Json.toJson(result.size()));
    }

    @Transactional
    public static Result delete(String userEmail) throws Exception {

	Client client = JPA.em().createQuery("from Client where email = :email", Client.class)
		.setParameter("email", userEmail).getSingleResult();

	JPA.em().remove(client);
	return ok();
    }
}
