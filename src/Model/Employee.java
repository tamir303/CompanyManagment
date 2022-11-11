package Model;

import java.io.Serializable;

public abstract class Employee implements ChangeWorkMethod, Profitable, Serializable {
	public static enum prefType {
		StartEarly, StartLate, NoChange, WorkFromHome
	};

	protected String name;
	protected prefType preference;
	protected int prefStartHour;
	protected int realStartHour;
	protected int hourPerMonth;
	protected boolean isHomeWorking;
	protected double profit;
	protected String id;

	public Employee(String name, String id, int prefStartHours, boolean PrefHomeWorking, int hourPerMonth)
			throws IdException, DayHourException, NameWithNonLetterException {
		this.prefStartHour = Utils.setHourDay(prefStartHours);
		this.preference = setPreference(prefStartHours, PrefHomeWorking);
		this.hourPerMonth = hourPerMonth;
		setID(id);
		this.name = Utils.setName(name);
	}

	private void setID(String id) throws IdException {
		if (!(id.matches("[0-9]+")))
			throw new IdException();
		if (id.length() != 9)
			throw new IdException(id);
		this.id = id;
	}

	@Override
	public boolean setWorkMethod(int DepStartHour, boolean isWorkingFromHome) {
		if (isWorkingFromHome) {
			this.isHomeWorking = true;
			this.realStartHour = -1;
		} else {
			this.isHomeWorking = false;
			this.realStartHour = DepStartHour;
		}
		this.profit = Company.INCOME_PER_HOUR * getEfficiency();
		return true;
	}

	public double getEfficiency() { // compares the real working time to employee's preference
		// calculates employee's hours of efficency or deficiency by comparing
		// his preferred work method to the real one

		if (this.isHomeWorking) {
			if (this.preference.equals(this.preference.WorkFromHome))
				return Company.TOTAL_HOUR * 1.1;// efficiency
			else // deficiency
				return Company.TOTAL_HOUR * 0.8;
		} // realStartHour -prefStartHour
		else if (this.preference.equals(this.preference.WorkFromHome)) {
			return Company.TOTAL_HOUR * 0.8;
		}

		if (this.preference.equals(this.preference.NoChange)) {
			if (this.realStartHour == Company.START_HOUR) {
				return Company.TOTAL_HOUR;
			} else
				return Math.abs(Company.START_HOUR - this.realStartHour) * 0.8
						+ (Company.TOTAL_HOUR - Math.abs(Company.START_HOUR - this.realStartHour));
		}

		if (this.preference.equals(this.preference.StartEarly)) {
			if (this.realStartHour >= 8)
				return Math.abs(realStartHour - prefStartHour) * 0.8
						+ (Company.TOTAL_HOUR - Math.abs(realStartHour - prefStartHour));
			else if (realStartHour < prefStartHour)
				return Math.abs(Company.START_HOUR - this.prefStartHour) * 1.2
						+ (Company.TOTAL_HOUR - Math.abs(Company.START_HOUR - this.prefStartHour));
			else
				return Math.abs(Company.START_HOUR - this.realStartHour) * 1.2
						+ (Company.TOTAL_HOUR - Math.abs(Company.START_HOUR - this.realStartHour));
		}
		if (this.preference.equals(this.preference.StartLate)) {
			if (this.realStartHour <= 8)
				return Math.abs(realStartHour - prefStartHour) * 0.8
						+ (Company.TOTAL_HOUR - Math.abs(realStartHour - prefStartHour));
			else if (realStartHour < prefStartHour)
				return Math.abs(Company.START_HOUR - this.realStartHour) * 1.2
						+ (Company.TOTAL_HOUR - Math.abs(Company.START_HOUR - this.realStartHour));
			else
				return Math.abs(Company.START_HOUR - this.prefStartHour) * 1.2
						+ (Company.TOTAL_HOUR - Math.abs(Company.START_HOUR - this.prefStartHour));

		}
		return 0;
	}

	public prefType setPreference(int prefStartHours, boolean prefHomeWorking) {
		// sets employee's enum type of preference according to his preffered
		// work method
		if (prefHomeWorking) {
			this.prefStartHour = -1;
			return prefType.WorkFromHome;
		} else {
			if (prefStartHours < Company.START_HOUR) {
				return prefType.StartEarly;
			} else if (prefStartHours > Company.START_HOUR)
				return prefType.StartLate;
			else
				return prefType.NoChange;
		}
	}

	public void setStartHours(int startHour) {
		this.realStartHour = startHour;
	}

	public prefType getPreference() {
		return preference;
	}

	public String getName() {
		return name;
	}

	public int getSTART_HOUR() {
		return Company.START_HOUR;
	}

	public int getPrefStartHour() {
		return prefStartHour;
	}

	public int getRealStartHour() {
		return realStartHour;
	}

	public boolean isHomeWorking() {
		return isHomeWorking;
	}

	public int getStartHour() {
		return this.realStartHour;
	}

	public String getId() {
		return id;
	}

	public int getHourPerMonth() {
		return hourPerMonth;
	}

	public String getProfit() {
		if (this.profit > 90)
			return "Worker's profit has increased by "
					+ Math.abs(profit - (Company.INCOME_PER_HOUR * Company.TOTAL_HOUR)) + "¤";
		if (this.profit < 90)
			return "Worker's profit has decreased by "
					+ Math.abs(profit - (Company.INCOME_PER_HOUR * Company.TOTAL_HOUR)) + "¤";
		return "Worker's profit didn't change";
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Employee) || other == null) {
			return false;
		}
		Employee e = (Employee) other;
		return this.isHomeWorking == e.isHomeWorking && this.id.equals(e.id) && this.hourPerMonth == e.hourPerMonth
				&& this.name.equals(e.name) && this.preference.equals(e.preference)
				&& this.prefStartHour == prefStartHour && this.realStartHour == e.realStartHour
				&& this.profit == e.profit;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(", Employee: name " + name + ", preference " + preference);
		if (this.isHomeWorking)
			sb.append(", Working From Home" + "\n");
		else
			sb.append(", Start working at :" + realStartHour +":00"+ "\n");
		return sb.toString();
	}
}