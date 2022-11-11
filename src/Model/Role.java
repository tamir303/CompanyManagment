package Model;

import java.io.Serializable;

public abstract class Role implements Serializable {
	protected Employee employee;
	protected String name;

	public Role(String name) throws NameWithNonLetterException {
		this.name = Utils.setName(name);
	}

	public Employee getEmployee() {
		return employee;
	}

	public String getName() {
		return name;
	}

	private String stringEmployy() {
		Class c = Utils.EmployeeType(this.employee);
		if (c.equals(GlobalSalaryEmployee.class)) {
			GlobalSalaryEmployee ge = (GlobalSalaryEmployee) this.employee;
			return ge.toString();
		} else if (c.equals(GlobalAndSaleEmployee.class)) {
			GlobalAndSaleEmployee gse = (GlobalAndSaleEmployee) this.employee;
			return gse.toString();
		} else {
			PerHourEmployee pe = (PerHourEmployee) this.employee;
			return pe.toString();
		}
	}
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Role) || other == null) {
			return false;
		}
		Role r = (Role) other;
		return r.employee.equals(this.employee)&& r.name.equals(this.name);
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Role Name: " + this.name);
		if (this.employee != null)
			sb.append(stringEmployy());
		else
			sb.append(", role is unmanned");
		return sb.toString();
	}

}
