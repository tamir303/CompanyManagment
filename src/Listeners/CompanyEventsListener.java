package Listeners;

import Model.Department;
import Model.Employee;
import Model.FlexibleRole;
import Model.InFlexibleRole;

public interface CompanyEventsListener {
	void addedEmployeeToModelEvent(Employee e);

	void addedDepartmentToModelEvent(Department d);

	void addedInFlexibleRoleToModelEvent(InFlexibleRole r);

	void addedFlexibleRoleToModelEvent(FlexibleRole r);

}
