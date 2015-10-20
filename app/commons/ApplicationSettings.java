package commons;

public enum ApplicationSettings {

	PATH_TO_SCHEMAS("path.to.schemas");

	ApplicationSettings(String value) {
		this.value = value;
	}

	public final String value;

}
