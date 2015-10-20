package validators.json;

import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import play.Play;

import com.fasterxml.jackson.databind.JsonNode;


public class JsonValidatorRhino extends JsonValidator {

	@Override
	public Result validate(Data<JsonNode> data, String model) throws Exception {

		ScriptEngineManager factory = new ScriptEngineManager(null);
		ScriptEngine engine = factory.getEngineByName("javascript");
	
		long time = System.currentTimeMillis();
		
		engine.eval(new FileReader(Play.application().path().getAbsolutePath()
				+ "\\utils\\is-my-json-valid-master\\example.js"));

	
		System.out.println(System.currentTimeMillis() - time);

		return null;
	}
}
