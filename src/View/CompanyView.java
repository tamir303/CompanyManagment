package View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Vector;
import javax.swing.JOptionPane;
import Listeners.CompanyUIEventsListeners;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class CompanyView implements AbstractCompanyView {
	private Vector<CompanyUIEventsListeners> allListeners = new Vector<CompanyUIEventsListeners>();
	private ComboBox<String> cmbAllDeparments = new ComboBox<String>();
	private ComboBox<String> cmbRoles = new ComboBox<String>();
	private ComboBox<String> cmbStartHour = new ComboBox<String>();

	public CompanyView(Stage theStage) {
		theStage.setTitle("Main");
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));// 550, 315);
		Scene MainScene = new Scene(new Group(gpRoot), 620, 640, Color.LIGHTSKYBLUE);
		TextArea taArea = new TextArea();
		taArea.setEditable(false);
		taArea.setStyle("-fx-font: 13 arial;");

		// create Background Image
		FileInputStream input;
		try {
			input = new FileInputStream("img.jpg");
			Image image = new Image(input, 620, 650, false, false);
			BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
			Background background = new Background(backgroundimage);
			gpRoot.setBackground(background);
		} catch (FileNotFoundException e) {
			e.getMessage();
		}
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		Label lbRoot = new Label("Company Efficiency Calculator");
		setLabel(lbRoot);
		Button btnEmployee = new Button("Add Employee");
		Button btnDepartment = new Button("Add Department");
		Button btnRole = new Button("Add Role");
		Button btnRoleWM = new Button("Change Role Work Method");
		Button btnDepWM = new Button("Change Department Work Method");
		Button btnShowCompany = new Button("Show Company");
		Button btnShowProfit = new Button("Show Company Profit");
		Button btnReturn = new Button("Main");
		btnRole.setStyle("-fx-font: 15 arial; -fx-base: #FFFFFF;");
		btnRoleWM.setStyle("-fx-font: 15 arial; -fx-base: #FFFFFF;");
		btnDepWM.setStyle("-fx-font: 15 arial; -fx-base: #FFFFFF;");
		btnShowCompany.setStyle("-fx-font: 15 arial; -fx-base: #FFFFFF;");
		btnShowProfit.setStyle("-fx-font: 15 arial; -fx-base: #FFFFFF;");
		btnReturn.setStyle("-fx-font: 15 arial; -fx-base: #FFFFFF;");
		btnDepartment.setStyle("-fx-font: 15 arial; -fx-base: #FFFFFF;");
		btnEmployee.setStyle("-fx-font: 15 arial; -fx-base: #FFFFFF;");
		gpRoot.add(lbRoot, 5, 0);
		gpRoot.add(btnRole, 5, 2);
		gpRoot.add(btnDepartment, 5, 1);
		gpRoot.add(btnEmployee, 5, 3);
		gpRoot.add(btnRoleWM, 5, 4);
		gpRoot.add(btnDepWM, 5, 5);
		gpRoot.add(btnShowProfit, 5, 6);
		gpRoot.add(btnShowCompany, 5, 7);
		gpRoot.add(taArea, 5, 8);
		taArea.setPrefSize(550, 315);
		// cmb Hours 0:00-23:00
		for (int i = 0; i <= 23; i++) {
			cmbStartHour.getItems().add(i + ":00");
		}
		cmbAllDeparments.setValue(null);
		cmbRoles.setValue(null);
		// RETURN TO MAIN BUTTON
		btnReturn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				theStage.setScene(MainScene);
				theStage.setTitle("Main");
				theStage.show();
			}
		});
		// Department
		btnDepartment.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				theStage.setTitle("Add New Department");
				GridPane gbDepartment = new GridPane();
				gbDepartment.setPadding(new Insets(10));
				gbDepartment.setHgap(10);
				gbDepartment.setVgap(10);
				Label lblDepName = new Label("Enter Deparment name: ");
				setLabel(lblDepName);
				TextField tfDeparmentName = new TextField();
				Label lblStartHour = new Label("Enter Start Hour");
				setLabel(lblStartHour);
				lblStartHour.setVisible(false);
				cmbStartHour.setValue("1:00");
				CheckBox chIsSync = new CheckBox("Make all roles in deparment syncable?");
				chIsSync.setFont(new Font("Make all roles in deparment syncable?", 15));
				chIsSync.setTextFill(Color.WHITE);
				chIsSync.setVisible(true);
				cmbStartHour.setVisible(false);
				CheckBox chIsWorkingFromHome = new CheckBox("Make all work from home");
				chIsWorkingFromHome.setFont(new Font("Make all work from home", 15));
				chIsWorkingFromHome.setTextFill(Color.WHITE);
				chIsWorkingFromHome.setVisible(false);
				chIsSync.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						lblStartHour.setVisible(chIsSync.isSelected());
						cmbStartHour.setVisible(chIsSync.isSelected());
						chIsWorkingFromHome.setVisible(chIsSync.isSelected());
					}
				});
				chIsWorkingFromHome.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						cmbStartHour.setVisible(!chIsWorkingFromHome.isSelected());
						lblStartHour.setVisible(!chIsWorkingFromHome.isSelected());
					}
				});
				Button btnAddDepartment = new Button("Add Department");
				btnAddDepartment.setStyle("-fx-font: 15 arial; -fx-base: #FFFFFF;");
				if (cmbStartHour.getValue() == null)
					cmbStartHour.setValue("1:00");
				btnAddDepartment.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent action) {
						if (!tfDeparmentName.getText().isBlank() && !tfDeparmentName.getText().contains(" ")) {
							for (CompanyUIEventsListeners l : allListeners) {
								l.addDepartemntToUI(tfDeparmentName.getText(), chIsSync.isSelected(),
										Integer.parseInt(cmbStartHour.getValue().split(":")[0]),
										chIsWorkingFromHome.isSelected());
							}
						} else {
							JOptionPane.showMessageDialog(null, "Deparment name can't be empty or contain spaces");
						}
					}
				});
				gbDepartment.add(lblDepName, 1, 0);
				gbDepartment.add(tfDeparmentName, 1, 1);
				gbDepartment.add(chIsSync, 2, 0);
				gbDepartment.add(chIsWorkingFromHome, 2, 1);
				gbDepartment.add(lblStartHour, 1, 2);
				gbDepartment.add(cmbStartHour, 2, 2);
				gbDepartment.add(btnReturn, 1, 3);
				gbDepartment.add(btnAddDepartment, 2, 3);
				theStage.setScene(new Scene(new Group(gbDepartment), 600, 150, Color.LIGHTSKYBLUE));
			}
		});
		// Role
		btnRole.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				theStage.setTitle("Add New Role");
				GridPane gbRole = new GridPane();
				gbRole.setPadding(new Insets(10));
				gbRole.setHgap(10);
				gbRole.setVgap(10);
				Label lblRoleName = new Label("Enter Role name: ");
				setLabel(lblRoleName);
				TextField tfRoleName = new TextField();
				Label lblStartHour = new Label("Enter Start Hour");
				setLabel(lblStartHour);
				cmbStartHour.setValue("1:00");
				cmbStartHour.setVisible(false);
				CheckBox chIsFlexiableRole = new CheckBox("Flexiable role?");
				chIsFlexiableRole.setFont(new Font("Flexiable role?", 15));
				chIsFlexiableRole.setTextFill(Color.WHITE);
				chIsFlexiableRole.setVisible(false);
				CheckBox chIsHomeWorking = new CheckBox(" Working from home");
				chIsHomeWorking.setFont(new Font(" Working from home", 15));
				chIsHomeWorking.setTextFill(Color.WHITE);
				chIsHomeWorking.setVisible(false);
				Label lblEnterDepartment = new Label("Enter Department :");
				setLabel(lblEnterDepartment);
				cmbAllDeparments.setValue(null);
				cmbAllDeparments.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						if (cmbAllDeparments.getValue() != null) {
							String type = cmbAllDeparments.getValue().split(" ")[1];
							if (type.equals("UnSync")) {
								chIsFlexiableRole.setVisible(true);
								chIsHomeWorking.setVisible(true);
								cmbStartHour.setVisible(true);
							} else {
								chIsFlexiableRole.setVisible(false);
								chIsHomeWorking.setVisible(false);
								cmbStartHour.setVisible(false);
							}
						}
					}

				});

				chIsFlexiableRole.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						cmbStartHour.setVisible(!chIsFlexiableRole.isSelected());
						chIsHomeWorking.setVisible(!chIsFlexiableRole.isSelected());
					}
				});
				chIsHomeWorking.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						cmbStartHour.setVisible(!chIsHomeWorking.isSelected());
					}
				});
				Button btnAddRole = new Button("Add Role");
				btnAddRole.setStyle("-fx-font: 15 arial; -fx-base: #FFFFFF;");
				btnAddRole.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent action) {
						if (cmbAllDeparments.getValue() != null) {
							if (!tfRoleName.getText().isBlank() && !tfRoleName.getText().contains(" ")) {
								for (CompanyUIEventsListeners l : allListeners) {
									System.out.println(cmbAllDeparments.getValue().split(" ")[0]);
									l.addRoleToUI(cmbAllDeparments.getValue().split(" ")[0], tfRoleName.getText(),
											Integer.parseInt(cmbStartHour.getValue().split(":")[0]),
											chIsHomeWorking.isSelected(), chIsFlexiableRole.isSelected());
								}
							} else {
								JOptionPane.showMessageDialog(null, "Role name can't be empty or contain spaces");
							}
						} else
							JOptionPane.showMessageDialog(null, "Please Enter all fields");
					}
				});
				gbRole.add(lblRoleName, 1, 0);
				gbRole.add(tfRoleName, 1, 1);
				gbRole.add(lblEnterDepartment, 2, 0);
				gbRole.add(cmbAllDeparments, 2, 1);
				gbRole.add(chIsFlexiableRole, 3, 0);
				gbRole.add(cmbStartHour, 3, 1);
				gbRole.add(chIsHomeWorking, 4, 0);
				gbRole.add(btnAddRole, 2, 2);
				gbRole.add(btnReturn, 1, 2);
				theStage.setScene(new Scene(new Group(gbRole), 800, 150, Color.LIGHTSKYBLUE));
				theStage.show();
			}
		});
		// Employee
		btnEmployee.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				theStage.setTitle("Add New Employee");
				GridPane gbEmployee = new GridPane();
				GridPane gbNext = new GridPane();
				gbEmployee.setPadding(new Insets(10));
				gbEmployee.setHgap(10);
				gbEmployee.setVgap(10);
				gbNext.setPadding(new Insets(10));
				gbNext.setHgap(10);
				gbNext.setVgap(10);
				Label lblEmployeeName = new Label("Enter Employee name: ");
				setLabel(lblEmployeeName);
				TextField tfEmployeeName = new TextField();
				Label lblEmployeeID = new Label("Enter Employee ID: ");
				setLabel(lblEmployeeID);
				TextField tfEmployeeID = new TextField();
				Label lblEmployeeType = new Label("Enter Employee Payment Method ");
				setLabel(lblEmployeeType);
				ComboBox<String> cmbEmpType = new ComboBox<String>();
				String perHour = "Per Hour Employee";
				String globalSalaray = "Global Salary Employee";
				String globalAndSaleSalaray = "Global Salary and Sales Employee";
				cmbEmpType.getItems().add(perHour);
				cmbEmpType.getItems().add(globalSalaray);
				cmbEmpType.getItems().add(globalAndSaleSalaray);
				Label lblHourPerMonth = new Label("Enter Hours Per Month: ");
				setLabel(lblHourPerMonth);
				cmbStartHour.setVisible(true);
				ComboBox<Integer> cmbHourPerMonth = new ComboBox<Integer>();
				for (int i = 100; i <= 150; i = i + 10) {
					cmbHourPerMonth.getItems().add(i);
				}
				cmbHourPerMonth.setValue(100);
				lblHourPerMonth.setVisible(true);
				cmbHourPerMonth.setVisible(true);

				ComboBox<Double> cmbBonusPerSale = new ComboBox<Double>();
				for (int i = 100; i <= 1000; i = i + 100) {
					cmbBonusPerSale.getItems().add((double) i);
				}
				Label lblBonusPerSale = new Label("Enter Bonus Per Per Sale: ");
				setLabel(lblBonusPerSale);
				cmbBonusPerSale.setValue(100.0);
				Label lblPrefStartHour = new Label("Enter  Prefrence Start Hour");
				setLabel(lblPrefStartHour);
				cmbStartHour.setValue("8:00");
				Label lblDepartment = new Label("Enter a deparment");
				setLabel(lblDepartment);
				Label lblRole = new Label("choose a role");
				setLabel(lblRole);
				cmbAllDeparments.setValue(null);
				cmbRoles.setValue(null);
				CheckBox chPrefWorkFromHome = new CheckBox("prefence to work from home");
				chPrefWorkFromHome.setFont(new Font("prefence to work from home", 15));
				chPrefWorkFromHome.setTextFill(Color.WHITE);
				chPrefWorkFromHome.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						lblPrefStartHour.setVisible(!chPrefWorkFromHome.isSelected());
					}
				});
				cmbHourPerMonth.setVisible(false);
				cmbBonusPerSale.setVisible(false);
				lblBonusPerSale.setVisible(false);
				lblHourPerMonth.setVisible(false);
				cmbEmpType.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						if (cmbEmpType.getValue().equals(globalSalaray)
								|| cmbEmpType.getValue().equals(globalAndSaleSalaray))
							cmbHourPerMonth.setValue(160);
						cmbHourPerMonth.setVisible(cmbEmpType.getValue().equals(perHour));
						lblHourPerMonth.setVisible(cmbEmpType.getValue().equals(perHour));
						lblBonusPerSale.setVisible(cmbEmpType.getValue().equals(globalAndSaleSalaray));
						cmbBonusPerSale.setVisible(cmbEmpType.getValue().equals(globalAndSaleSalaray));
					}
				});
				chPrefWorkFromHome.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						lblPrefStartHour.setVisible(!chPrefWorkFromHome.isSelected());
						cmbStartHour.setVisible(!chPrefWorkFromHome.isSelected());
					}
				});
				cmbAllDeparments.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						if (cmbAllDeparments.getValue() != null)
							getRolesToUI(cmbAllDeparments.getValue().split(" ")[0]);
					}
				});
				Scene EmployeeSence = new Scene(new Group(gbEmployee), 700, 200, Color.LIGHTSKYBLUE);
				Button btnBack = new Button("Back");
				btnBack.setStyle("-fx-font: 15 arial; -fx-base: #FFFFFF;");
				// RETURN TO employee menu
				btnBack.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent action) {
						theStage.setScene(EmployeeSence);
						theStage.show();
					}
				});
				Button btnNext = new Button("Next");
				btnNext.setStyle("-fx-font: 15 arial; -fx-base: #FFFFFF;");
				btnNext.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent action) {
						if (tfEmployeeID.getText().isBlank() || tfEmployeeName.getText().isBlank()
								|| cmbEmpType.getValue() == null || cmbEmpType.getValue() == null
								|| (cmbStartHour.getValue() == null && (!chPrefWorkFromHome.isSelected())))
							JOptionPane.showMessageDialog(null, "Please Enter all Fields");
						else {
							theStage.setScene(new Scene(new Group(gbNext), 700, 200, Color.LIGHTSKYBLUE));
							theStage.setTitle("Main");
						}
						theStage.show();
					}
				});
				Button btnAddEmployee = new Button("Add Employee");
				btnAddEmployee.setStyle("-fx-font: 15 arial; -fx-base: #FFFFFF;");
				btnAddEmployee.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent action) {
						if (cmbAllDeparments.getValue() != null && cmbRoles.getValue() != null) {
							for (CompanyUIEventsListeners l : allListeners) {
								l.addEmployeeToUI(tfEmployeeName.getText(), tfEmployeeID.getText(),
										Integer.parseInt(cmbStartHour.getValue().split(":")[0]),
										chPrefWorkFromHome.isSelected(), cmbHourPerMonth.getValue(),
										cmbBonusPerSale.getValue(), cmbEmpType.getValue().equals(globalAndSaleSalaray),
										cmbAllDeparments.getValue().split(" ")[0], cmbRoles.getValue().split(" ")[0]);
								theStage.setScene(MainScene);
								theStage.show();
							}
						} else {
							JOptionPane.showMessageDialog(null, "Please Enter all Fields\"");
						}
					}
				});
				gbNext.add(lblDepartment, 1, 0);
				gbNext.add(cmbAllDeparments, 1, 1);
				gbNext.add(lblRole, 2, 0);
				gbNext.add(cmbRoles, 2, 1);
				gbNext.add(btnBack, 1, 2);
				gbNext.add(btnAddEmployee, 2, 2);
				gbNext.add(btnReturn, 3, 2);
				gbEmployee.add(lblEmployeeName, 1, 0);
				gbEmployee.add(tfEmployeeName, 1, 1);
				gbEmployee.add(lblEmployeeID, 2, 0);
				gbEmployee.add(tfEmployeeID, 2, 1);
				gbEmployee.add(lblEmployeeType, 3, 0);
				gbEmployee.add(cmbEmpType, 3, 1);
				gbEmployee.add(lblPrefStartHour, 1, 2);
				gbEmployee.add(cmbStartHour, 1, 3);
				gbEmployee.add(lblBonusPerSale, 3, 2);
				gbEmployee.add(cmbBonusPerSale, 3, 3);
				gbEmployee.add(lblHourPerMonth, 3, 2);
				gbEmployee.add(cmbHourPerMonth, 3, 3);
				gbEmployee.add(chPrefWorkFromHome, 2, 2);
				gbEmployee.add(btnReturn, 1, 4);
				gbEmployee.add(btnNext, 3, 4);
				theStage.setScene(EmployeeSence);
				theStage.show();
			}
		});
		// Change role work method
		btnRoleWM.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				theStage.setTitle("Change Role Work Method");
				GridPane gbRoleWM = new GridPane();
				gbRoleWM.setHgap(10);
				gbRoleWM.setVgap(10);
				gbRoleWM.setPadding(new Insets(10));
				Button btnSetChange = new Button("Apply Change");
				btnSetChange.setStyle("-fx-font: 15 arial; -fx-base: #FFFFFF;");
				Label lblWorkMethod = new Label("Set Work Method : ");
				setLabel(lblWorkMethod);
				Label lblnewStartHour = new Label("Set new Start Hour : ");
				setLabel(lblnewStartHour);
				Label lblChooseRole = new Label("Choose Role From Department: ");
				setLabel(lblChooseRole);
				CheckBox chIsWorkingFromHome = new CheckBox("Work From Home? ");
				chIsWorkingFromHome.setFont(new Font("Make all roles in deparment syncable?", 15));
				chIsWorkingFromHome.setTextFill(Color.WHITE);
				cmbStartHour.setValue("1:00");
				cmbAllDeparments.setValue(null);
				cmbRoles.setValue(null);
				cmbAllDeparments.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						if (cmbAllDeparments.getValue() != null)
							getRolesToUI(cmbAllDeparments.getValue().split(" ")[0]);
					}
				});
				chIsWorkingFromHome.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						cmbStartHour.setVisible(!chIsWorkingFromHome.isSelected());
					}
				});
				btnSetChange.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent action) {
						if (!(cmbRoles.getValue() == null))
							for (CompanyUIEventsListeners l : allListeners) {
								l.changeWorkMethod(cmbAllDeparments.getValue().split(" ")[0],
										cmbRoles.getValue().split(" ")[0],
										Integer.parseInt(cmbStartHour.getValue().split(":")[0]),
										chIsWorkingFromHome.isSelected());
							}
						else
							JOptionPane.showMessageDialog(null, "Please Enter all Fields\"");
					}
				});
				gbRoleWM.add(lblChooseRole, 1, 1);
				gbRoleWM.add(cmbAllDeparments, 2, 1);
				gbRoleWM.add(cmbRoles, 3, 1);
				gbRoleWM.add(lblWorkMethod, 1, 2);
				gbRoleWM.add(lblnewStartHour, 1, 3);
				gbRoleWM.add(cmbStartHour, 2, 3);
				gbRoleWM.add(chIsWorkingFromHome, 2, 2);
				gbRoleWM.add(btnSetChange, 2, 4);
				gbRoleWM.add(btnReturn, 1, 4);
				theStage.setScene(new Scene(new Group(gbRoleWM), 750, 200, Color.LIGHTSKYBLUE));
			}
		});
		// Change department work method
		btnDepWM.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				GridPane gbDepWM = new GridPane();
				gbDepWM.setPadding(new Insets(10));
				gbDepWM.setHgap(10);
				gbDepWM.setVgap(10);
				Button btnSetChange = new Button("Apply Change");
				btnSetChange.setStyle("-fx-font: 15 arial; -fx-base: #FFFFFF;");
				Label lblWorkMethod = new Label("Set Work Method: ");
				setLabel(lblWorkMethod);
				Label lblSetnewHour = new Label("Set new start hour: ");
				setLabel(lblSetnewHour);
				Label lblChooseRole = new Label("Choose Role From Department: ");
				setLabel(lblChooseRole);
				CheckBox chIsWorkingFromHome = new CheckBox("Work From Home? ");
				chIsWorkingFromHome.setTextFill(Color.WHITE);
				cmbStartHour.setValue("1:00");
				cmbStartHour.setVisible(true);
				cmbAllDeparments.setValue(null);
				chIsWorkingFromHome.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						cmbStartHour.setVisible(!chIsWorkingFromHome.isSelected());
					}
				});
				btnSetChange.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent action) {
						if (!(cmbAllDeparments.getValue() == null)) {
							for (CompanyUIEventsListeners l : allListeners) {
								l.changeWorkMethod(cmbAllDeparments.getValue().split(" ")[0],
										Integer.parseInt(cmbStartHour.getValue().split(":")[0]),
										chIsWorkingFromHome.isSelected());
							}
						} else
							JOptionPane.showMessageDialog(null, "Please Enter all Fields\"");
					}
				});
				gbDepWM.add(lblChooseRole, 1, 1);
				gbDepWM.add(cmbAllDeparments, 2, 1);
				gbDepWM.add(lblWorkMethod, 1, 2);
				gbDepWM.add(lblSetnewHour, 1, 3);
				gbDepWM.add(cmbStartHour, 2, 3);
				gbDepWM.add(chIsWorkingFromHome, 2, 2);
				gbDepWM.add(btnReturn, 1, 4);
				gbDepWM.add(btnSetChange, 2, 4);
				theStage.setScene(new Scene(new Group(gbDepWM), 700, 200, Color.LIGHTSKYBLUE));
			}
		});
		// show Company
		btnShowCompany.setOnAction(new EventHandler<ActionEvent>() {
			// TextArea taButton=new TextArea();
			public void handle(ActionEvent action) {
				for (CompanyUIEventsListeners l : allListeners) {
					taArea.setText(l.showCompany());
				}
			}
		});
		// show Company Profit
		btnShowProfit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				for (CompanyUIEventsListeners l : allListeners) {
					taArea.setText(l.showProfit());
				}
			}
		});
		// save company option
		theStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent we) {
				Stage exitStage = new Stage();
				GridPane gbExit = new GridPane();
				gbExit.setPadding(new Insets(10));
				gbExit.setHgap(15);
				gbExit.setVgap(15);
				Button btnExitNoSave = new Button("Exit without Save");
				Button btnExitYesSave = new Button("Save and Exit");
				btnExitYesSave.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent action) {
						for (CompanyUIEventsListeners l : allListeners) {
							l.saveCompany();
						}
						JOptionPane.showMessageDialog(null, "The Company has been Saved");
						exitStage.close();
					}
				});
				btnExitNoSave.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent action) {
						JOptionPane.showMessageDialog(null, "The Company wasn't Saved");
						exitStage.close();
					}
				});
				gbExit.add(btnExitNoSave, 1, 1);
				gbExit.add(btnExitYesSave, 2, 1);
				exitStage.setScene(new Scene(new Group(gbExit), 260, 80, Color.LIGHTSKYBLUE));
				exitStage.setResizable(false);
				exitStage.initStyle(StageStyle.UTILITY);
				exitStage.show();
			}
		});
		theStage.setScene(MainScene);
		theStage.setResizable(false);
		theStage.show();
	}

	@Override
	public void registerListener(CompanyUIEventsListeners listener) {
		this.allListeners.add(listener);
	}

	@Override
	public void addEmployeeToUI(String EmployeeName, String id, int prefStartHour, Boolean PrefHomeWorking,
			int hourPerMonth) {
	}

	@Override
	public void addDepartemntToUI(String name, boolean isSync, int startHour, boolean homeWorking) {
		if (isSync)
			addSyncDepartemntToUI(name, startHour, homeWorking);
		else
			addUnSyncDepartmentToUI(name);
	}

	private void addSyncDepartemntToUI(String name, int startHour, boolean homeWorking) {
		removeFromDepCmb(name);
		String str;
		if (homeWorking)
			str = name + " Syncable, home Working";
		else
			str = name + " Syncable, starts at " + startHour;
		this.cmbAllDeparments.getItems().add(str);
	}

	private void addUnSyncDepartmentToUI(String name) {
		removeFromDepCmb(name);
		this.cmbAllDeparments.getItems().add(name + " UnSync");
	}

	private void removeFromDepCmb(String name) {
		for (String cmbDep : cmbAllDeparments.getItems())
			if (cmbDep.contains(name)) {
				cmbAllDeparments.getItems().remove(cmbDep);
				break;
			}
	}

	private void setLabel(Label lb) {
		lb.setFont(new Font(lb.getText(), 15));
		lb.setTextFill(Color.WHITE);
	}

	@Override
	public void addInFlexibleRoleToUI(String name, int startHour, boolean homeWork) {
	}

	@Override
	public void addFlexibleRoleToUI(String name, int startHour, boolean homeWork) {
	}

	@Override
	public void getRolesToUI(String depName) {
		this.cmbRoles.getItems().clear();
		int listSize = Integer.valueOf((this.allListeners.get(0).getRolesToUI(depName)).split("\n")[0]);
		for (int i = 1; i <= listSize; i++) {
			this.cmbRoles.getItems().add((this.allListeners.get(0).getRolesToUI(depName)).split("\n")[i]);
		}
	}
}