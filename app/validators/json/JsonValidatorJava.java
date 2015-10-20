package validators.json;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

import play.Play;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import commons.ApplicationSettings;

public class JsonValidatorJava extends JsonValidator {

	private static ConcurrentHashMap<String, JsonSchema> schemaContainer = new ConcurrentHashMap<>();

	static {

		try {

			File schemasFolder = new File(Play.application().configuration()
					.getString(ApplicationSettings.PATH_TO_SCHEMAS.value));

			for (File file : schemasFolder.listFiles()) {

				JsonNode schemaJson = JsonLoader.fromFile(file);

				JsonSchemaFactory schemaFactory = JsonSchemaFactory.byDefault();

				JsonSchema schemaObj = schemaFactory.getJsonSchema(schemaJson);
				schemaContainer.put(
						file.getName().substring(0,
								file.getName().lastIndexOf('.')), schemaObj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Result validate(Data<JsonNode> data, String model) throws Exception {

		ProcessingReport result = schemaContainer.get(model).validate(
				data.getData());

		return new JsonValidationResult(result.toString(), result.isSuccess());
	}
}
