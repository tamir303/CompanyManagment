package Model;

import java.io.Serializable;

public class CantChangeUnSyncDepartmentWorkMethod extends CompanyException implements Serializable {
	public CantChangeUnSyncDepartmentWorkMethod() {
		super("Can't change work method of UnSyncDepartment");
	}
}
