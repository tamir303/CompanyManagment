package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import javax.swing.JOptionPane;

import Listeners.CompanyEventsListener;
import Listeners.CompanyUIEventsListeners;
import Model.CantChangeRoleInSyncDepWorkMethod;
import Model.CantChangeUnSyncDepartmentWorkMethod;
import Model.Company;
import Model.DayHourException;
import Model.DepAlreadyExists;
import Model.Department;
import Model.EmpAlreadyExists;
import Model.Employee;
import Model.FlexibleRole;
import Model.GlobalAndSaleEmployee;
import Model.GlobalSalaryEmployee;
import Model.IdException;
import Model.InFlexibleRole;
import Model.NameWithNonLetterException;
import Model.PerHourEmployee;
import Model.RoleAlreadyExists;

import Model.Utils;
import View.AbstractCompanyView;

public class Controller implements CompanyEventsListener, CompanyUIEventsListeners {
	private Company companyModel;
	private AbstractCompanyView companyView;

	public Controller(Company model, AbstractCompanyView view) {
		companyModel = model;
		companyView = view;

		companyModel.registerListener(this);
		companyView.registerListener(this);

		companyModel.loadHardCode();
		this.loadCompany();
	}

	@Override
	public String getRolesToUI(String depName) {
		return companyModel.getAllRolesString(depName);
	}

	@Override
	public void addEmployeeToUI(String name, String Id, int prefStartHours, Boolean PrefHomeWorking, int hourPerMonth,
			double saleBonus, boolean isSalesMan, String depName, String role) {
		Class c = Utils.EmployeeType(isSalesMan, hourPerMonth);
		try {
			if (c.equals(PerHourEmployee.class)) {
				companyModel.addEmployees(new PerHourEmployee(name, Id, prefStartHours, PrefHomeWorking, hourPerMonth),
						depName, role);
				JOptionPane.showMessageDialog(null, "Employee has been added");

			} else if (c.equals(GlobalSalaryEmployee.class)) {
				companyModel.addEmployees(new GlobalSalaryEmployee(name, Id, prefStartHours, PrefHomeWorking), depName,
						role);
				JOptionPane.showMessageDialog(null, "Employee has been added");

			} else {
				companyModel.addEmployees(
						new GlobalAndSaleEmployee(name, Id, prefStartHours, PrefHomeWorking, saleBonus), depName, role);
				JOptionPane.showMessageDialog(null, "Employee has been added");
			}
		} catch (EmpAlreadyExists | IdException | DayHourException | NameWithNonLetterException e) {
			e.getMessage();
		}

	}

	@Override
	public String showProfit() {
		return companyModel.getTotalEfficiency();
	}

	@Override
	public void addedEmployeeToModelEvent(Employee e) {
		companyView.addEmployeeToUI(e.getName(), e.getId(), e.getPrefStartHour(),
				e.getPreference().equals(Employee.prefType.WorkFromHome), e.getHourPerMonth());
	}

	@Override
	public void addDepartemntToUI(String depName, boolean isSync, int depStartHour, boolean isHomeWorking) {
		try {
				companyModel.addDepartment(new Department(depName,isSync, isHomeWorking, depStartHour));
				JOptionPane.showMessageDialog(null, "Deparment has been added");

		} catch (DepAlreadyExists | NameWithNonLetterException e) {
			e.getMessage();
		}
	}

	@Override
	public void addedDepartmentToModelEvent(Department d) {
		companyView.addDepartemntToUI(d.getName(),d.isSyncable(),d.getStartHour(),d.isHomeWorking());
	}

	@Override
	public void addRoleToUI(String depName, String roleName, int startHour, boolean isHomeWorking, boolean isFlexable) {
		Class c = Utils.RoleType(isFlexable);
		try {
			if (c.equals(FlexibleRole.class)) {
				companyModel.addRole(depName, new FlexibleRole(roleName));
				JOptionPane.showMessageDialog(null, "Role has been added");

			} else {
				companyModel.addRole(depName, new InFlexibleRole(roleName, startHour, isHomeWorking));
				JOptionPane.showMessageDialog(null, "Role has been added");

			}
		} catch (RoleAlreadyExists | DayHourException | NameWithNonLetterException e) {
			e.getMessage();
		}
	}

	@Override
	public void addedInFlexibleRoleToModelEvent(InFlexibleRole r) {
		companyView.addInFlexibleRoleToUI(r.getName(), r.getStartHour(), r.isHomeWork());
	}

	@Override
	public void addedFlexibleRoleToModelEvent(FlexibleRole r) {
		companyView.addFlexibleRoleToUI(r.getName(), r.getStartHour(), r.isHomeWork());
	}

	@Override
	public void changeWorkMethod(String depName, String role, int startHour, boolean isHomeWorking) {
		try {
			companyModel.changeWorkMethod(depName, role, startHour, isHomeWorking);
			JOptionPane.showMessageDialog(null, "Work method as been set");

		} catch (CantChangeRoleInSyncDepWorkMethod e) {
			e.getMessage();
		}
	}

	@Override
	public void changeWorkMethod(String depName, int startHour, boolean isHomeWorking) {
		try {
			companyModel.changeWorkMethod(depName, startHour, isHomeWorking);
			JOptionPane.showMessageDialog(null, "Work method as been set");
		} catch (CantChangeUnSyncDepartmentWorkMethod e) {
			e.getMessage();
		}
	}

	@Override
	public String showCompany() {
		return companyModel.toString();
	}

	@Override
	public void saveCompany() {
		Utils.saveObject(this.companyModel.GetDataBase(), "user.dat");
	}

	@Override
	public void loadCompany() {
		try {
			this.companyModel.SetDataBase(Utils.loadObject("user.dat"));
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (InvalidClassException e) {
			e.getMessage();
		} catch (ClassNotFoundException e) {
			e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			e.getMessage();
		}
	}

}
