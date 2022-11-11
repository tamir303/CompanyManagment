package Model;

import java.io.Serializable;

import javax.swing.JOptionPane;

public class CompanyException extends Exception implements Serializable {
	public CompanyException() {
		System.out.println("Exception: There is A general Exception");
	}

	public CompanyException(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

}
