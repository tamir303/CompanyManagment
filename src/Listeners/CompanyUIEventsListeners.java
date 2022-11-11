package Listeners;

public interface CompanyUIEventsListeners {

	void addEmployeeToUI(String name, String Id, int prefStartHours, Boolean PrefHomeWorking, 
	int hourPerMonth,double saleBonus,boolean isSalesMan,String depName,String role);
	void addDepartemntToUI(String depName,boolean isSync,int depStartHour,boolean isHomeWorking);
	void addRoleToUI(String depName, String roleName, int startHour,boolean isHomeWorking, boolean isFlexable);
	String getRolesToUI(String depName);
	void changeWorkMethod(String depName, String role, int startHour, boolean isHomeWorking);
    void changeWorkMethod(String depName, int startHour, boolean isHomeWorking);
	String showCompany();
	void saveCompany();
	void loadCompany();
	String showProfit();
}
