package Model;

import java.io.Serializable;

public class FlexibleRole extends Role implements ChangeWorkMethod, Serializable {
	private int startHour;
	private boolean isHomeWork;

	public FlexibleRole(String name) throws NameWithNonLetterException {
		super(name);
	}

	public Employee addEmployee(Employee e) {
		// in flexibleRole we set the employee's work method to match his preference
		e.setWorkMethod(e.getPrefStartHour(), e.getPreference().equals(e.preference.WorkFromHome));
		this.startHour = e.getPrefStartHour();
		this.isHomeWork = e.isHomeWorking;
		Employee oldEmp = this.employee;
		this.employee = e;
		return oldEmp;
	}

	public int getStartHour() {
		return startHour;
	}

	public boolean isHomeWork() {
		return isHomeWork;
	}
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof FlexibleRole) || other == null) {
			return false;
		}
		FlexibleRole r = (FlexibleRole) other;
		return r.isHomeWork==this.isHomeWork && r.startHour==this.startHour&&super.equals(r);
	}

	@Override
	public boolean setWorkMethod(int startHour, boolean isHomeWorking) {
		// when we set new work method in a flexibleRole, we refer to it as the
		// employee's new preference
		this.startHour = startHour;
		this.isHomeWork = isHomeWorking;
		if (this.employee != null) {
			this.employee.setPreference(startHour, isHomeWorking);
		return true;
		}else
		return false;
	}
}