package Model;

import java.io.Serializable;

public class GlobalSalaryEmployee extends Employee implements Serializable {

	public GlobalSalaryEmployee(String name, String id, int startHour, boolean PrefHomeWorking)
			throws IdException, DayHourException, NameWithNonLetterException {
		super(name, id, startHour, PrefHomeWorking, 160);
	}

	@Override
	public String toString() {
		return super.toString() + "Employee Type: Getting Global Salary ";
	}

}
