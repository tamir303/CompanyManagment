package Model;

import java.io.Serializable;
import java.util.Vector;

import javax.swing.JOptionPane;

import Model.Company;

public class Department implements Profitable, Serializable, Synchronizable {
	protected String name;
	protected Vector<Employee> allEmployes;
	protected Vector<Role> allRoles;
	protected double profit;
	private boolean isSyncable;
	private int startHour;
	private boolean isHomeWorking;

	public Department(String name, boolean isSyncable, boolean isHomeWorking, int startHour)
			throws NameWithNonLetterException {
		this.name = Utils.setName(name);
		allEmployes = new Vector<Employee>();
		allRoles = new Vector<Role>();
		setSyncable(isSyncable);
		this.profit = 0;
		if (isSyncable) {
            this.isHomeWorking = isHomeWorking;
            this.startHour = startHour;
        }
        else
        {
            this.isHomeWorking = false;
            this.startHour = -1;
        }
	}
	

	public double getEfficiency() {
		double sumOfWorkingHours = 0;
		for (Employee e : this.allEmployes) {
			sumOfWorkingHours += e.getEfficiency();
		}
		return sumOfWorkingHours;
	}

	public String getProfit() {
		this.profit = this.getEfficiency() * (Company.INCOME_PER_HOUR);
		double oldIncome = Company.INCOME_PER_HOUR * this.allEmployes.size() * Company.TOTAL_HOUR;//
		StringBuffer sb = new StringBuffer("The Department :" + this.name + "");
		sb.append("\n");
		for (Role r : this.allRoles) {
			if (r.getEmployee() != null) {
				sb.append("Role name :" + r.getName()).append(", Employee " + r.getEmployee().getName())
						.append(" ," + r.getEmployee().getProfit());
				sb.append("\n");
			}
		}
		if (this.profit > oldIncome)
			sb.append("Department's profit has increased by " + (float) Math.abs(profit - (oldIncome)) + "¤");
		else if (this.profit < oldIncome)
			sb.append("Department's profit has decreased by " + (float) Math.abs(profit - (oldIncome)) + "¤");
		else
			sb.append("Department's profit didn't change");
		return sb.toString();
	}

	public Employee addEmployee(Employee e, String role) {
		if ((Utils.findRole(role, allRoles)).getEmployee() != null)
			JOptionPane.showMessageDialog(null, "Employee has been repalced by new employee");
		if (isSyncable) {
			Employee oldEmp = ((InFlexibleRole) Utils.findRole(role, allRoles)).addEmployee(e); // cast because always
																								// // InFlex
			this.allEmployes.add(e);
			this.allEmployes.remove(oldEmp);
			return oldEmp;
		} else {
			Employee oldEmp;
			if (Utils.findRole(role, allRoles).getClass().equals(InFlexibleRole.class))
				oldEmp = ((InFlexibleRole) Utils.findRole(role, allRoles)).addEmployee(e);
			else
				oldEmp = ((FlexibleRole) Utils.findRole(role, allRoles)).addEmployee(e);
			this.allEmployes.add(e);
			this.allEmployes.remove(oldEmp);
			return oldEmp;
		}

	}

	public void addRole(Role role) throws RoleAlreadyExists {
		if (Utils.findRole(role.name, this.allRoles) != null)
			throw new RoleAlreadyExists(role.getName());
		if (isSyncable) {
			InFlexibleRole ir = (InFlexibleRole) role;
			ir.setWorkMethod(this.startHour, this.isHomeWorking);
			this.allRoles.add(ir);
		} else {
			if (role.getClass().equals(InFlexibleRole.class)) {
				InFlexibleRole ir = (InFlexibleRole) role;
				this.allRoles.add(ir);
			} else {
				FlexibleRole fr = (FlexibleRole) role;
				this.allRoles.add(fr);
			}

		}
	}

	public String getName() {
		return name;
	}

	public Vector<Employee> getAllEmployes() {
		return allEmployes;
	}

	public String getAllRolesString() {
		StringBuffer sb = new StringBuffer(this.allRoles.size() + "\n");
		for (Role r : this.allRoles) {
			if (r.getEmployee() == null)
				sb.append(r.getName() + " - " + r.getClass().getSimpleName() + "\n");
			else
				sb.append(
						r.getName() + " - " + r.getClass().getSimpleName() + " - " + r.getEmployee().getName() + "\n");
		}
		return sb.toString();
	}

	public Vector<Role> getAllRoles() {
		return allRoles;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Department) || other == null) {
			return false;
		}
		Department d = (Department) other;
		return d.name.equals(this.name) && d.profit == this.profit && d.allEmployes.equals(this.allEmployes)
				&& d.allRoles.equals(this.allRoles);
	}



	@Override
	public boolean setWorkMethod(int DepStartHour, boolean isHomeWork) {// option to change sync to unsync
	 if (isSyncable) {
			// change work method of SyncDepartment and change all work method of all
			// roles in department
			this.startHour = DepStartHour;
			this.isHomeWorking = isHomeWork;
			for (Role r : this.allRoles) {
				InFlexibleRole ir = (InFlexibleRole) r;
				if (isHomeWork == false) {
					ir.setWorkMethod(this.startHour, isHomeWork);
				} else
					ir.setWorkMethod(-1, isHomeWork);
			}
			return true;
		}
		else
			return false;
	}

	@Override
	public void setSyncable(boolean isSyncable) {
		this.isSyncable=isSyncable;
	}

	public boolean isSyncable() {
		return isSyncable;
	}


	public boolean isHomeWorking() {
		return isHomeWorking;
	}


	public int getStartHour() {
		return startHour;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("Department name : " + this.name);
		if(isSyncable) {
			sb.append(", Syncable");
			if(isHomeWorking)
			sb.append(", Working from home"+"\n");
			else
				sb.append(", start Working at : "+this.startHour+":00"+"\n");
		}
		else
			sb.append("\n");
		for (Role r : this.allRoles) {
			sb.append(r.toString()).append("\n");
			
		}
		return sb.toString();
	}

}
