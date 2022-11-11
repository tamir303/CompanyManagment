package Model;

import java.io.Serializable;

public class DayHourException extends CompanyException implements Serializable {
	public DayHourException() {
		super("Hour isn't valid please input 0-23");
	}
}
