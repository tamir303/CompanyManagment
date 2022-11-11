package Model;

import java.io.Serializable;

public class IdException extends CompanyException implements Serializable {
	public IdException(String id) {
		super("The id " + id + " is not 9 digits");
	}

	public IdException() {
		super("Id must contain only digits");
	}

}
