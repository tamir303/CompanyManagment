package View;

import Listeners.CompanyUIEventsListeners;

public interface AbstractCompanyView {

	void registerListener(CompanyUIEventsListeners listener);
	
	void addEmployeeToUI(String EmployeeName, String id, int prefStartHour,Boolean PrefHomeWorking, int hourPerMonth);
	void addDepartemntToUI(String name, boolean syncable, int startHour, boolean homeWorking);
	void addInFlexibleRoleToUI(String name, int startHour, boolean homeWork);
	void addFlexibleRoleToUI(String name, int startHour, boolean homeWork);
	void getRolesToUI(String depName);


}
