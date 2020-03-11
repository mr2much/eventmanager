package com.banreservas.monitoreo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private Button accessButton;

	@FXML
	private TextField usrTextField;

	@FXML
	private Button cancelButton;

	private String username;

	public void handleAccesarButtonAction(ActionEvent e) {
		username = usrTextField.getText();
		((Stage) accessButton.getScene().getWindow()).close();
	}

	public void cancelButtonAction(ActionEvent e) {
		// get stage through button
		Stage stage = (Stage) cancelButton.getScene().getWindow();

		stage.close();
	}

	public String getUsername() {
		return username;
	}

}
