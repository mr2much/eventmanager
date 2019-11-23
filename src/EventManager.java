import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.banreservas.monitoreo.model.Evento;
import com.banreservas.monitoreo.model.Severidad;
import com.banreservas.monitoreo.model.Turnos;
import com.banreservas.monitoreo.view.EventDialog;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EventManager extends Application {

	TableColumn<Evento, String> entryDateColumn = new TableColumn<>("Fecha");
	TableColumn<Evento, String> ticketNumberColumn = new TableColumn<>("Ticket");
	TableColumn<Evento, String> descriptionColumn = new TableColumn<>("Reporte de Evento");
	TableColumn<Evento, String> shiftColumn = new TableColumn<>("Turno");
	TableColumn<Evento, String> comentaryColumn = new TableColumn<>("Comentarios");
	TableColumn<Evento, Boolean> statusColumn = new TableColumn<>("Estatus");

	private List<TableColumn<Evento, ?>> columnas;

	{
		columnas = new ArrayList<>();

		columnas.add(entryDateColumn);
		columnas.add(ticketNumberColumn);
		columnas.add(descriptionColumn);
		columnas.add(shiftColumn);
		columnas.add(comentaryColumn);
		columnas.add(statusColumn);
	}

	private TableView<Evento> table = new TableView<>();

	ObservableList<String> shifts = FXCollections.observableArrayList("07:00 AM - 03:59 PM", "04:00 PM - 11:59 PM",
			"11:00 PM - 06:59 AM");

	private Long id = 1L;
	private final ObservableList<Evento> data = FXCollections.observableArrayList(new Evento(id, "11/11/2019",
			"335361543",
			"Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas)",
			Turnos.MADRUGADA, "Gerencia Soporte Sistemas Distribuidos.", false, Severidad.ALTA),
			new Evento(id++, "30/10/2019", "335362015", "Inconvenientes con las consultas de firmas vía Siebel CRM",
					Turnos.VESPERTINO, "DTEL Zona Metro Este", true, Severidad.BAJA),
			new Evento(id++, "09/11/2019", "335361566",
					"Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas) Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas) Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas)",
					Turnos.MATUTINO, "Gerencia Soporte Sistemas Distribuidos.", false, Severidad.MEDIA),
			new Evento(id++, "09/11/2019", "335361766",
					"Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas) Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas) Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas) Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas) Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas) Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas)",
					Turnos.MATUTINO, "Gerencia Soporte Sistemas Distribuidos.", true, Severidad.BAJA));

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Eventos Cambio de Turno - Centro de Monitoreo");

		GridPane grid = new GridPane();

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 10, 25, 10));

		final VBox vbox = new VBox();
		VBox.setVgrow(vbox, Priority.ALWAYS);
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 10, 10, 10));

		Button addBtn = new Button("Registrar Evento");

		addBtn.setOnMouseClicked((EventHandler<MouseEvent>) e -> {
			if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
				// open event creation dialog
				EventDialog dlg = new EventDialog(stage);
				Optional<Evento> optionalEvento = dlg.showAndWait();

				optionalEvento.ifPresent((Evento evento) -> {
					System.out.println("Evento: " + evento.toString());
				});

			}
		});

		createTableView();

		// set table width to be the same as the stage
		table.prefWidthProperty().bind(stage.widthProperty());

		stage.addEventHandler(KeyEvent.KEY_RELEASED, (EventHandler<KeyEvent>) e -> {
			if (e.getCode() == KeyCode.ESCAPE) {
				stage.close();
			}
		});

		vbox.getChildren().addAll(addBtn, table);
		grid.add(vbox, 0, 0);

		Scene scene = new Scene(grid, 1200, 500);
		scene.getStylesheets().add(EventManager.class.getResource("css/styles.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	private void createTableView() {
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setEditable(true);

		// table.setFixedCellSize(60.0);

		entryDateColumn.setMinWidth(60);
		entryDateColumn.setCellValueFactory(new PropertyValueFactory<Evento, String>("entryDate"));
		entryDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		entryDateColumn.setOnEditCommit((EventHandler<CellEditEvent<Evento, String>>) t -> {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setEntryDate(t.getNewValue());
		});

		ticketNumberColumn.setMinWidth(60);
		ticketNumberColumn.setCellValueFactory(new PropertyValueFactory<Evento, String>("ticketNumber"));
		ticketNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		ticketNumberColumn.setOnEditCommit((EventHandler<CellEditEvent<Evento, String>>) t -> {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setTicketNumber(t.getNewValue());
		});

		descriptionColumn.setMinWidth(300);

		Callback<TableColumn<Evento, String>, TableCell<Evento, String>> textAreaCell = new Callback<TableColumn<Evento, String>, TableCell<Evento, String>>() {

			@Override
			public TableCell<Evento, String> call(TableColumn<Evento, String> cellValue) {
				TableCell<Evento, String> cell = new TableCell<Evento, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						if (item == getItem()) {
							return;
						}

						super.updateItem(item, empty);

						if (item == null) {
							super.setText(null);
							super.setGraphic(null);
						} else {
							super.setText(null);
							Label l = new Label(item);
							l.setWrapText(true);

							VBox box = new VBox(l);

							l.heightProperty().addListener((observable, oldValue, newValue) -> {
								box.setPrefHeight(newValue.doubleValue() + 7);
								Platform.runLater(() -> this.getTableRow().requestLayout());
							});

							super.setGraphic(box);
						}
					}
				};

				Platform.runLater(() -> table.requestLayout());
				cell.setWrapText(true);

				return cell;
			}
		};

		descriptionColumn.setCellValueFactory(new PropertyValueFactory<Evento, String>("description"));
		descriptionColumn.setCellFactory(textAreaCell);

		shiftColumn.setMinWidth(120);
		shiftColumn.setCellValueFactory(new PropertyValueFactory<Evento, String>("shift"));
		shiftColumn.setCellFactory(ComboBoxTableCell.forTableColumn(shifts));
//		shiftColumn.setOnEditCommit((EventHandler<CellEditEvent<Evento, String>>) t -> {
//			t.getTableView().getItems().get(t.getTablePosition().getRow()).setShift(t.getNewValue());
//		});

		comentaryColumn.setMinWidth(220);
		comentaryColumn.setCellValueFactory(new PropertyValueFactory<Evento, String>("comentary"));
		comentaryColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		statusColumn.setMinWidth(40);
		statusColumn.setCellValueFactory(new PropertyValueFactory<Evento, Boolean>("status"));
		Callback<TableColumn<Evento, Boolean>, TableCell<Evento, Boolean>> cellFactory = new Callback<TableColumn<Evento, Boolean>, TableCell<Evento, Boolean>>() {
			public TableCell<Evento, Boolean> call(TableColumn<Evento, Boolean> p) {
				TableCell<Evento, Boolean> cell = new TableCell<Evento, Boolean>() {
					@Override
					public void updateItem(Boolean item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							if (item) {
								setStyle("-fx-background-color: green;");
							} else {
								setStyle("-fx-background-color: yellow;");
							}
						}

						Platform.runLater(() -> table.requestLayout());
					}
				};

				return cell;
			}
		};

		statusColumn.setCellFactory(cellFactory);

		table.setItems(data);

		// Estatus del evento cambia cuando se hace right click en el row
		// correspondiente al evento
		table.setRowFactory(tv -> {
			TableRow<Evento> row = new TableRow<>();
			if (!row.isEmpty()) {
				System.out.println("Is not empty");
			} else {
				System.out.println("Not empty");
			}
			row.setOnMouseClicked((EventHandler<MouseEvent>) e -> {
				if (e.getButton() == MouseButton.SECONDARY) {
					Platform.runLater(() -> table.requestLayout());
					Evento evento = row.getTableView().getSelectionModel().getSelectedItem();

					boolean value = evento.getStatus();
					evento.setStatus(!value);

					row.getTableView().getItems().set(row.getIndex(), evento);
				}

			});

			return row;
		});

		table.getColumns().addAll(columnas);
		table.setId("event-table");
	}

}
