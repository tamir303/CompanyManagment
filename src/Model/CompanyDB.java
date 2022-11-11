package Model;

import java.io.Serializable;
import java.util.Vector;

public class CompanyDB implements Serializable {
	protected Vector<Department> allDepartments;
	protected Vector<Employee> allEmployees;
	protected double profit;
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof CompanyDB) || other == null) {
			return false;
		}
		CompanyDB e = (CompanyDB) other;
		return allDepartments.equals(e.allDepartments)&&allEmployees.equals(e.allEmployees)&&profit==e.profit;
	}
}
