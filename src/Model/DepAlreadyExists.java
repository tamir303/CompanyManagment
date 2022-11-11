package Model;

import java.io.Serializable;

public class DepAlreadyExists extends CompanyException implements Serializable {

	public DepAlreadyExists(String name) {
		super("the Department" +name +" already exists");
	}

}
