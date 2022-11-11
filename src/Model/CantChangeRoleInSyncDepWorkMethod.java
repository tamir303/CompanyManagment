package Model;

import java.io.Serializable;

public class CantChangeRoleInSyncDepWorkMethod extends CompanyException implements Serializable {
	public CantChangeRoleInSyncDepWorkMethod() {
        super("You can't change work method of SyncDepartment role, "
                + "you have to change department's work method");
    }
}
