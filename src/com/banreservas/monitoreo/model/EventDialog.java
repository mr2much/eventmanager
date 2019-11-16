package com.banreservas.monitoreo.model;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Dialog class for event creation
public class EventDialog extends Dialog<Evento> {
	private GridPane grid;
	private Button addBtn;

	public EventDialog(Stage callerStage) {
		super();

		initOwner(callerStage);
		setTitle("Registrar Nuevo Evento");

		grid = new GridPane();

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		final VBox vbox = new VBox();
		VBox.setVgrow(vbox, Priority.ALWAYS);

		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 10, 0));
		// Label eventType = new Label("Seleccionar Tipo de Evento");

		final TextField tfEventDate = new TextField();
		tfEventDate.setPromptText("Fecha del Evento: DD/MM/AAAA");
		tfEventDate.setMinWidth(200);

		final TextField tfEventTicket = new TextField();
		tfEventTicket.setPromptText("Numero de Ticket");

		Label lblEventSeverity = new Label("Indique Severidad de Evento");
		ComboBox<String> cmbSeverity = new ComboBox(FXCollections.observableArrayList(Severidad.values()));
		vbox.getChildren().addAll(tfEventDate, tfEventTicket);

		Label lblEventDescription = new Label("Descripcion del Evento");
		TextArea taEventDescription = new TextArea();
		taEventDescription.setWrapText(true);

		Label lblTurno = new Label("Especifique el Turno");
		ComboBox<String> cmbTurnos = new ComboBox(FXCollections.observableArrayList(Turnos.values()));

		Label lblComentary = new Label("Agregar Comentario");
		TextArea taEventComentary = new TextArea();
		taEventComentary.setWrapText(true);

		vbox.getChildren().addAll(lblEventSeverity, cmbSeverity, lblEventDescription, taEventDescription, lblTurno,
				cmbTurnos, lblComentary, taEventComentary);

		grid.add(vbox, 0, 0);

		ButtonType closeBtn = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);

		getDialogPane().getButtonTypes().addAll(closeBtn);
		getDialogPane().setContent(grid);
	}

}
