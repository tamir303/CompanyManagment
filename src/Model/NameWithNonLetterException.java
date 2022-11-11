package Model;

public class NameWithNonLetterException extends CompanyException {
	public NameWithNonLetterException() {
		super("Name must contain only letters");
	}
}
