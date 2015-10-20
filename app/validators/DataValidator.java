package validators;

public interface DataValidator<T> {

	default Result validate(Data<T> data, String model) throws Exception {

		return new Result() {

			@Override
			public String getMessage() {

				return null;
			}

			@Override
			public boolean isSuccess() {
				return false;
			}
		};
	}

	Data<T> createData(T data);

	interface Result {

		boolean isSuccess();

		String getMessage();
	}

	interface Data<T> {

		T getData();
	}
}
