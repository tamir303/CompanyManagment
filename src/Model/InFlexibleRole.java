package Model;

import java.io.Serializable;

public class InFlexibleRole extends Role implements ChangeWorkMethod, Serializable {
	private int startHour;
	private boolean isHomeWork;

	public InFlexibleRole(String name, int startHour, boolean isHomeWorking)
			throws DayHourException, NameWithNonLetterException {
		super(name);
		if (isHomeWorking)
			this.startHour = -1;
		else
			this.startHour = Utils.setHourDay(startHour);
		this.isHomeWork = isHomeWorking;
	}

	public Employee addEmployee(Employee e) {
		e.setWorkMethod(this.startHour, this.isHomeWork);
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
		if (!(other instanceof InFlexibleRole) || other == null) {
			return false;
		}
		InFlexibleRole r = (InFlexibleRole) other;
		return r.isHomeWork==this.isHomeWork && r.startHour==this.startHour&&super.equals(r);
	}
	@Override
	public boolean setWorkMethod(int DepStartHour, boolean isWorkingFromHome) {
		this.isHomeWork = isWorkingFromHome;
		this.startHour = DepStartHour;
		if (this.employee != null) {
			this.employee.setWorkMethod(DepStartHour, isWorkingFromHome);
		return true;
		}else
		return false;
	}

}