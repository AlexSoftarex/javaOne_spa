package validators.json;

import com.fasterxml.jackson.databind.JsonNode;

import validators.DataValidator;

public abstract class JsonValidator implements DataValidator<JsonNode>{
	
	@Override
	public  Data<JsonNode> createData(JsonNode json) {
				
		return new JsonData(json);
	}
	
	protected class JsonData implements Data<JsonNode> {
		
		JsonNode jsonNode;
		
		JsonData(JsonNode json) {
			jsonNode = json;
		}

		@Override
		public JsonNode getData() {
			return jsonNode;
		}
	}
	
	protected class JsonValidationResult implements Result {
		
		private String msg;
		private boolean success;
		
		public JsonValidationResult(String msg, boolean isSuccess) {
			this.msg = msg;
			this.success = isSuccess;
		}
		
		public boolean isSuccess() {
			return success;
		}

		@Override
		public String getMessage() {
			return msg;
		}
	}

}
