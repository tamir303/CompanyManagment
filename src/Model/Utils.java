package Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public final class Utils {

	public static void saveObject(Object ObjectToSave, String FileName) {
		ObjectOutputStream outFile;
		try {
			outFile = new ObjectOutputStream(new FileOutputStream(FileName));
			outFile.writeObject(ObjectToSave);
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object loadObject(String FileName) throws ClassNotFoundException, IOException {
		ObjectInputStream inFile = new ObjectInputStream(new FileInputStream(FileName));
		Object object = inFile.readObject();
		inFile.close();
		return object;
	}

	public static Department findDep(String depName, Vector<Department> allDepartments) {
		for (Department d : allDepartments) {
			if (d.getName().equals(depName))
				return d;
		}
		return null;
	}

	public static Role findRole(String rolName, Vector<? extends Role> allRoles) {
		for (Role r : allRoles) {
			if (r.getName().equals(rolName))
				return r;
		}
		return null;
	}

	public static Employee findEmployee(String id, Vector<Employee> allEmployees) {
		for (Employee d : allEmployees) {
			if (d.getId().equals(id))
				return d;
		}
		return null;
	}

	public static int setHourDay(int hour) throws DayHourException {
		if (hour < 0 || hour > 23) {
			throw new DayHourException();
		}
		return hour;
	}

	public static String setName(String name) throws NameWithNonLetterException {
		for (int i = 0; i < name.length(); i++) {
			if (!(Character.isLetter(name.charAt(i))) && (name.charAt(i) != ' '))
				throw new NameWithNonLetterException();
		}
		return name;
	}

	public static Class EmployeeType(boolean isSalesMan, int hourPerMonth) {
		if (hourPerMonth == 160) {
			if (isSalesMan) {
				return GlobalAndSaleEmployee.class;
			} else
				return GlobalSalaryEmployee.class;
		}
		return PerHourEmployee.class;
	}

	public static Class EmployeeType(Employee e) {
		if (e.getClass().equals(GlobalSalaryEmployee.class))
			return GlobalSalaryEmployee.class;
		else if (e.getClass().equals(GlobalAndSaleEmployee.class))
			return GlobalAndSaleEmployee.class;
		else
			return PerHourEmployee.class;
	}

	public static Class RoleType(boolean isFlexable) {
		if (isFlexable)
			return FlexibleRole.class;
		else
			return InFlexibleRole.class;
	}
}
