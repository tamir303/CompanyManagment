package Model;

import java.io.Serializable;

public class GlobalAndSaleEmployee extends GlobalSalaryEmployee implements Serializable {
	private double saleBonus;

	public GlobalAndSaleEmployee(String name, String id, int startHour, boolean PrefHomeWorking, double saleBonus)
			throws IdException, DayHourException, NameWithNonLetterException {
		super(name, id, startHour, PrefHomeWorking);
		setSaleBonus(saleBonus);
	}

	public double getSaleBonus() {
		return saleBonus;
	}

	public void setSaleBonus(double saleBonus) {
			this.saleBonus = saleBonus;
	}
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof GlobalAndSaleEmployee) || other == null) {
			return false;
		}
		GlobalAndSaleEmployee e = (GlobalAndSaleEmployee) other;
		return super.equals(e)&&e.saleBonus==this.saleBonus;
	}
	@Override
	public String toString() {
		return super.toString() + ", Sales Bonus " + this.saleBonus;
	}

}
