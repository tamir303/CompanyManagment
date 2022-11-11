package Model;

import java.util.Vector;

import Listeners.CompanyEventsListener;

public class Company implements Profitable {
	private CompanyDB dataBase;

	protected static final int INCOME_PER_HOUR = 10;
	protected static final int TOTAL_HOUR = 9;
	protected static final int START_HOUR = 8;
	private Vector<CompanyEventsListener> allisteners;

	public Object GetDataBase() {
		return dataBase;
	}

	public void SetDataBase(Object dataBase) {
		this.dataBase = (CompanyDB) dataBase;
		this.fireAll();
	}

	public Company() {
		this.dataBase = new CompanyDB();
		this.dataBase.allDepartments = new Vector<Department>();
		this.dataBase.allEmployees = new Vector<Employee>();
		this.dataBase.profit = 0;
		allisteners = new Vector<CompanyEventsListener>();
	}

	public void addDepartment(Department dep) throws DepAlreadyExists {
		if (Utils.findDep(dep.getName(), this.dataBase.allDepartments) != null)
			throw new DepAlreadyExists(dep.getName());
		else
			this.dataBase.allDepartments.add(dep);
		fireAddedDepartment(dep);
	}

	public void addRole(String depName, Role role) throws RoleAlreadyExists {
		Department d = Utils.findDep(depName, this.dataBase.allDepartments);
		fireAddedRole(role);
		d.addRole(role);
	}

	public void addEmployees(Employee e, String depName, String role) throws EmpAlreadyExists {
		if (Utils.findEmployee(e.getId(), this.dataBase.allEmployees) != null) {
			throw new EmpAlreadyExists(e.getId());
		} else {
			this.dataBase.allEmployees.add(e);
			Employee oldEmp = Utils.findDep(depName, this.dataBase.allDepartments).addEmployee(e, role);
			if (oldEmp != null)
				this.dataBase.allEmployees.remove(oldEmp);
			fireAddedEmployee(e);
		}
	}

	public String getAllRolesString(String depName) {
		return Utils.findDep(depName, this.dataBase.allDepartments).getAllRolesString();
	}

	public int getIncomePerHour() {
		return INCOME_PER_HOUR;
	}

	public int getTotalHour() {
		return TOTAL_HOUR;
	}

	@Override
	public double getEfficiency() {
		double sumOfWorkingHours = 0;
		for (Department d : this.dataBase.allDepartments) {
			sumOfWorkingHours += d.getEfficiency();
		}
		return sumOfWorkingHours;
	}

	@Override
	public String getProfit() {
		this.dataBase.profit = this.getEfficiency() * (Company.INCOME_PER_HOUR);
		double oldIncome = Company.INCOME_PER_HOUR * this.dataBase.allEmployees.size() * Company.TOTAL_HOUR;//
		if (this.dataBase.profit > oldIncome)
			return "Company's profit has increased by " + (float) Math.abs(dataBase.profit - (oldIncome)) + "¤";
		if (this.dataBase.profit < oldIncome)
			return "Company's profit has decreased by " + (float) Math.abs(dataBase.profit - (oldIncome)) + "¤";
		return "Company's profit didn't change";
	}

	public void registerListener(CompanyEventsListener listener) {
		this.allisteners.add(listener);
	}

	public void fireAddedEmployee(Employee e) {
		for (CompanyEventsListener l : this.allisteners) {
			l.addedEmployeeToModelEvent(e);
		}
	}

	public void fireAddedDepartment(Department d) {
		for (CompanyEventsListener l : this.allisteners) {	
			l.addedDepartmentToModelEvent(d);
		}
	}

	public void fireAddedRole(Role r) {
		for (CompanyEventsListener l : this.allisteners) {
			if (r.getClass().equals(InFlexibleRole.class)) {
				l.addedInFlexibleRoleToModelEvent((InFlexibleRole) r);
			} else
				l.addedFlexibleRoleToModelEvent((FlexibleRole) r);
		}
	}

	private void fireAll() {
		for (Department d : this.dataBase.allDepartments) {
			fireAddedDepartment(d);
		}
	}
//Role Change Work Method
	public void changeWorkMethod(String depName, String role, int startHour, boolean isHomeWorking)
			throws CantChangeRoleInSyncDepWorkMethod {
		Department dp = Utils.findDep(depName, this.dataBase.allDepartments);
		if (dp.isSyncable())
			throw new CantChangeRoleInSyncDepWorkMethod();
		else {
			if (Utils.findRole(role, dp.getAllRoles()).getClass().equals(InFlexibleRole.class))
				((InFlexibleRole) Utils.findRole(role, dp.getAllRoles())).setWorkMethod(startHour, isHomeWorking);
			else
				((FlexibleRole) Utils.findRole(role, dp.getAllRoles())).setWorkMethod(startHour, isHomeWorking);
		}
	}
//Department change work method
	public void changeWorkMethod(String depName, int startHour, boolean isHomeWorking)
			throws CantChangeUnSyncDepartmentWorkMethod {
		Department d= Utils.findDep(depName, this.dataBase.allDepartments);
		if (d.isSyncable()) {
			d.setWorkMethod(startHour,isHomeWorking);
			this.fireAddedDepartment(Utils.findDep(depName, this.dataBase.allDepartments));
		} else
			throw new CantChangeUnSyncDepartmentWorkMethod();
	}

	public String getTotalEfficiency() {
		StringBuffer sb = new StringBuffer("Total Efficiency: ");
		sb.append("\n");
		for (Department d : this.dataBase.allDepartments) {
			sb.append(d.getProfit());
			sb.append("\n");
			sb.append("---------------------------------------------");
			sb.append("\n");
		}
		sb.append(this.getProfit());
		return sb.toString();
	}

	public void loadHardCode() {
		try {
			Department A1 = new Department("QASync", true, false, 8);
			Department A3 = new Department("RnDUnSyncDep",false,false,0);
			InFlexibleRole B1 = new InFlexibleRole("InFlexRoleQA", 8, false);
			InFlexibleRole B2 = new InFlexibleRole("InFlexRoleDevOPs", -1, true);
			FlexibleRole B3 = new FlexibleRole("FlexRoleBackOffice");
			InFlexibleRole B4 = new InFlexibleRole("InFlexRoleManager", 7, false);
			A1.addRole(B1);
			A1.addRole(B2);
			A3.addRole(B3);
			A3.addRole(B4);
			this.addDepartment(A1);
			this.addDepartment(A3);
			GlobalAndSaleEmployee E1 = new GlobalAndSaleEmployee("StartEarlyEmp", "123123123", 6, false, 0);
			GlobalSalaryEmployee E2 = new GlobalSalaryEmployee("StartLateEmp", "123123124", 10, false);
			PerHourEmployee E3 = new PerHourEmployee("NoChangeEmp", "123123125", 8, false, 100);
			GlobalAndSaleEmployee E4 = new GlobalAndSaleEmployee("HomeWorkingEmp", "123123126", 6, false, 0);
			this.addEmployees(E1, "QASync", "InFlexRoleQA");
			this.addEmployees(E2, "QASync", "InFlexRoleDevOPs");
			this.addEmployees(E3, "RnDUnSyncDep", "FlexRoleBackOffice");
			this.addEmployees(E4, "RnDUnSyncDep", "InFlexRoleManager");
		} catch (IdException | DepAlreadyExists | DayHourException | RoleAlreadyExists | EmpAlreadyExists
				| NameWithNonLetterException e) {
			e.getMessage();
		}
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Company) || other == null) {
			return false;
		}
		Company c = (Company) other;
		return this.dataBase.equals(c.dataBase);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("Company Departments:");
		sb.append("\n");
		String str = ("-");

		for (Department d : this.dataBase.allDepartments) {
			sb.append(d.toString());
			sb.append(str.repeat(120));
			sb.append("\n");
		}
		return sb.toString();
	}

}