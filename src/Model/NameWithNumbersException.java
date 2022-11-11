package Model;

public class NameWithNumbersException extends CompanyException {
	public NameWithNumbersException() {
		super("Name can't contain numbers");
	}
}
