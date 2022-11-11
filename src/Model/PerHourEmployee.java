package Model;

import java.io.Serializable;

public class PerHourEmployee extends Employee implements Serializable {

	public PerHourEmployee(String name, String id, int startHour, boolean prefHomeWorking, int hourPerMonth)
			throws IdException, DayHourException, NameWithNonLetterException {
		super(name, id, startHour, prefHomeWorking, hourPerMonth);
	}

	@Override
	public String toString() {
		return super.toString() + "Working Per Hour, total Hour Per Month : "+this.hourPerMonth;
	}
	
	
}
