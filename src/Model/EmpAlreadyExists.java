package Model;

import java.io.Serializable;

public class EmpAlreadyExists extends CompanyException implements Serializable {
	public EmpAlreadyExists(String id) {
		super("the Id: " +id +" already exists please try again");
	}

}
