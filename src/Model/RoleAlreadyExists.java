package Model;

import java.io.Serializable;

public class RoleAlreadyExists extends CompanyException implements Serializable {
	public RoleAlreadyExists(String name) {
		super("the role " + name + " already exists");
	}
}
