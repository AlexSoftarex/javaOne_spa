import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;

import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.fasterxml.jackson.databind.JsonNode;

import model.nojsonb.Client;
import model.nojsonb.Note;
import play.libs.Json;
import play.test.FakeApplication;
import spa.testdata.ClientFactory;

public class BaseTest {

	protected static FakeApplication app;
	protected static Client noJsonbClient;
	protected static JsonNode jsonbJson;
	protected static JsonNode noJsonbJson;
	protected static Client jsonbClient;

	@BeforeClass
	public static void beforeTestClass() {
		app = fakeApplication();
		start(app);
	}

	@AfterClass
	public static void afterTestClass() {
		stop(app);
	}

	@Before
	public void beforeTest() {
		int treeWidth = 4;
		int treeLength = 4;

		initJsonbClient(treeWidth, treeLength);
		initNoJsonbClient(treeWidth, treeLength);
	}

	private void initNoJsonbClient(int treeWidth, int treeLength) {
		noJsonbClient = new ClientFactory().getClient();
		Note mainNote = new Note();

		for (int width = 0; width < treeWidth; width++) {
			Note newNote = new Note();
			newNote.setText("note text " + treeLength);
			newNote.setChildren(getChildren(treeLength, 1, treeWidth));
			mainNote.getChildren().add(newNote);
		}

		noJsonbJson = Json.toJson(mainNote);
	}

	private void initJsonbClient(int treeWidth, int treeLength) {

		Long id = 1L;

		jsonbClient = new ClientFactory().getClient();

		Note mainNote = new Note();
		mainNote.setId(id++);
		mainNote.setText("some text");

		Set<Note> data = new HashSet<>();
		data.add(mainNote);

		for (int width = 0; width < treeWidth; width++)

		{
			Note newNote = new Note();
			newNote.setText("note text " + treeLength);
			newNote.setId(id++);
			newNote.setParentId(mainNote.getId());

			data.add(newNote);

			data.addAll(getChildren(treeLength, 1, treeWidth, id, newNote.getId()));
		}

		jsonbJson = Json.toJson(data);
	}

	private static Set<Note> getChildren(int treeLength, int currentLength, int treeWidth, Long id, Long parentId) {

		if (treeLength == currentLength) {
			return new HashSet<Note>();
		} else {

			Set<Note> children = new HashSet<>();

			for (int width = 0; width < treeWidth; width++) {

				Note newNote = new Note();
				newNote.setText("note text " + currentLength);
				newNote.setId(id++);
				newNote.setParentId(parentId);

				children.addAll(getChildren(treeLength, currentLength + 1, treeWidth, id, newNote.getId()));
				children.add(newNote);
			}
			return children;
		}
	}

	private static Set<Note> getChildren(int treeLength, int currentLength, int treeWidth) {

		if (treeLength == currentLength) {
			return new HashSet<Note>();
		} else {

			Set<Note> children = new HashSet<>();

			for (int width = 0; width < treeWidth; width++) {

				Note newNote = new Note();
				newNote.setText("note text " + currentLength);

				newNote.getChildren().addAll(getChildren(treeLength, currentLength + 1, treeWidth));
				children.add(newNote);
			}
			return children;
		}
	}
}
