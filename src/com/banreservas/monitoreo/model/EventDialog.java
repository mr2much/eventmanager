package com.banreservas.monitoreo.model;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EventDialog extends Dialog<Evento>{

	private Button addBtn;

	public EventDialog(Stage callerStage) {
		super();

		initOwner(callerStage);

		ButtonType closeBtn = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

		getDialogPane().getButtonTypes().addAll(closeBtn);

	}

}
