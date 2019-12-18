package com.banreservas.monitoreo.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.banreservas.monitoreo.model.Evento;
import com.banreservas.monitoreo.model.Severidad;
import com.banreservas.monitoreo.model.Turnos;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

// Dialog class for event creation
public class EventDialog extends Dialog<Evento> {
	private GridPane grid;
	DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	final private DatePicker datePicker = new DatePicker(LocalDate.now());
	final private TextField tfEventDate = new TextField();
	final private TextField tfEventTicket = new TextField();
	private TextArea taEventDescription = new TextArea();
	private TextArea taEventComentary = new TextArea();

	BooleanBinding booleanBinding = Bindings.isEmpty(tfEventTicket.textProperty())
			.or(Bindings.isEmpty(taEventDescription.textProperty()))
			.or(Bindings.isEmpty(taEventComentary.textProperty()));

	public EventDialog(Stage callerStage) {
		super();

		initOwner(callerStage);
		setTitle("Registrar Nuevo Evento");
		setResizable(true);

		grid = new GridPane();

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		final VBox vbox = new VBox();
		VBox.setVgrow(vbox, Priority.ALWAYS);

		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 10, 0));

		tfEventDate.setPromptText("Fecha del Evento: DD/MM/AAAA");
		tfEventDate.setMinWidth(200);

		setTextFieldValidation(tfEventTicket, taEventDescription, taEventComentary);

		tfEventTicket.setPromptText("Numero de Ticket");

		Label lblEventSeverity = new Label("Indique Severidad de Evento");
		ComboBox<Severidad> cmbSeverity = new ComboBox<>(FXCollections.observableArrayList(Severidad.values()));
		cmbSeverity.getSelectionModel().selectLast();

		datePicker.setConverter(new StringConverter<LocalDate>() {

			@Override
			public String toString(LocalDate date) {
				if (date == null) {
					return LocalDate.now().toString();
				}

				return format.format(date);
			}

			@Override
			public LocalDate fromString(String dateString) {
				if (dateString == null || dateString.trim().isEmpty()) {
					return null;
				}

				return LocalDate.parse(dateString, format);
			}
		});

		vbox.getChildren().addAll(datePicker, tfEventTicket);
		// vbox.getChildren().addAll(tfEventDate, tfEventTicket);

		Label lblEventDescription = new Label("Descripcion del Evento");
		taEventDescription.setWrapText(true);
		taEventDescription.setPrefRowCount(5);

		Label lblTurno = new Label("Especifique el Turno");
		ComboBox<Turnos> cmbTurnos = new ComboBox<>(FXCollections.observableArrayList(Turnos.values()));
		cmbTurnos.getSelectionModel().selectFirst();

		Label lblComentary = new Label("Agregar Comentario");

		taEventComentary.setWrapText(true);
		taEventComentary.setPrefRowCount(5);

		vbox.getChildren().addAll(lblEventSeverity, cmbSeverity, lblEventDescription, taEventDescription, lblTurno,
				cmbTurnos, lblComentary, taEventComentary);

		grid.add(vbox, 0, 0);

		ButtonType addBtnType = new ButtonType("Agregar", ButtonBar.ButtonData.OK_DONE);
		ButtonType cancelBtnType = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

		getDialogPane().getButtonTypes().addAll(addBtnType, cancelBtnType);
		getDialogPane().lookupButton(addBtnType).disableProperty().bind(booleanBinding);

		setResultConverter(new Callback<ButtonType, Evento>() {
			@Override
			public Evento call(ButtonType button) {
				if (button == addBtnType) {
					Evento newEvent = new Evento.EventBuilder().build();

					newEvent.setEntryDate(datePicker.getValue());
					newEvent.setTicketNumber(tfEventTicket.getText());
					newEvent.setSeverity(cmbSeverity.getValue());
					newEvent.setDescription(taEventDescription.getText());
					newEvent.setShift(cmbTurnos.getValue());
					newEvent.setComentary(taEventComentary.getText());
					newEvent.setStatus(false);

					return newEvent;
				}

				return null;
			}
		});

		final Text requiredText = new Text("*Required Field");
		requiredText.setId("required-text");
		HBox hbText = new HBox(5);
		hbText.setAlignment(Pos.CENTER_LEFT);
		hbText.getChildren().add(requiredText);

		grid.add(hbText, 0, 1);

		hbText.visibleProperty().bind(booleanBinding);

		getDialogPane().setContent(grid);
	}

	private void setTextFieldValidation(final TextInputControl... textControls) {
		for (TextInputControl tf : textControls) {
			tf.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observableString, String oldValue,
						String newValue) {
					checkIfEmpty(tf);
				}

			});

			checkIfEmpty(tf);
		}

	}

	private void checkIfEmpty(TextInputControl tf) {
		final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

		if (tf.getText().trim().length() == 0) {
			tf.pseudoClassStateChanged(errorClass, true);
		} else {
			tf.pseudoClassStateChanged(errorClass, false);
		}
	}

}
