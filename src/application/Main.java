package application;
	
import java.io.IOException;

import Controller.Controller;
import Model.Company;
import View.AbstractCompanyView;
import View.CompanyView;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException, InterruptedException {
		Company theModel = new Company();
		AbstractCompanyView theView1 = new CompanyView(primaryStage);
		Controller controller = new Controller(theModel, theView1);		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
