import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.banreservas.monitoreo.controller.EventTableViewController;
import com.banreservas.monitoreo.model.Evento;
import com.banreservas.monitoreo.model.Turnos;
import com.banreservas.monitoreo.repository.EventRepository;
import com.banreservas.monitoreo.repository.EventRepositoryStub;
import com.banreservas.monitoreo.view.EventDialog;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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

	private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private EventTableViewController controller = new EventTableViewController();
	private EventRepository repository = new EventRepositoryStub();
	TableColumn<Evento, String> entryDateColumn = new TableColumn<>("Fecha");
	TableColumn<Evento, String> ticketNumberColumn = new TableColumn<>("Ticket");
	TableColumn<Evento, String> descriptionColumn = new TableColumn<>("Reporte de Evento");
	TableColumn<Evento, Turnos> shiftColumn = new TableColumn<>("Turno");
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

	ObservableList<Turnos> shifts = FXCollections.observableArrayList(Turnos.values());

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		controller.setEventRepository(repository);

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
					controller.add(evento);
				});

			}
		});

		createTableView();

		// set table width to be the same as the stage
		table.prefWidthProperty().bind(stage.widthProperty());

		// stage.addEventHandler(KeyEvent.KEY_RELEASED, (EventHandler<KeyEvent>)
		// e -> {
		// if (e.getCode() == KeyCode.ESCAPE) {
		// stage.close();
		// }
		// });

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
		entryDateColumn.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
		entryDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		entryDateColumn.setEditable(false);

		ticketNumberColumn.setMinWidth(60);
		ticketNumberColumn.setCellValueFactory(new PropertyValueFactory<Evento, String>("ticketNumber"));
		ticketNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		ticketNumberColumn.setOnEditCommit((EventHandler<CellEditEvent<Evento, String>>) t -> {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setTicketNumber(t.getNewValue());
		});

		descriptionColumn.setMinWidth(300);
		descriptionColumn.setOnEditCommit((EventHandler<CellEditEvent<Evento, String>>) t -> {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setDescription(t.getNewValue());
		});

		Callback<TableColumn<Evento, String>, TableCell<Evento, String>> textAreaCellFactory = new Callback<TableColumn<Evento, String>, TableCell<Evento, String>>() {

			@Override
			public TableCell<Evento, String> call(TableColumn<Evento, String> cellValue) {
				TableCell<Evento, String> cell = new TableCell<Evento, String>() {

					String backup = "";

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

							TextArea ta = new TextArea();
							ta.setWrapText(true);

							l.setOnMouseClicked(new EventHandler<MouseEvent>() {

								@Override
								public void handle(MouseEvent e) {
									if (e.getClickCount() == 2) {
										ta.setText(backup = l.getText());
										l.setGraphic(ta);
										l.setText("");
										ta.requestFocus();
									}
								}

							});

							ta.setOnKeyPressed(new EventHandler<KeyEvent>() {

								@Override
								public void handle(KeyEvent e) {
									if (e.getCode() == KeyCode.ENTER) {
										l.setGraphic(null);
										l.setText(ta.getText());
										ta.requestFocus();
										getTableView().getItems().get(getTableRow().getIndex())
												.setDescription(ta.getText());
									} else if (e.getCode() == KeyCode.ESCAPE) {
										ta.setText(backup);
										l.setGraphic(null);
										l.setText(ta.getText());
									}
								}

							});

							ta.focusedProperty().addListener((prop, oldValue, newValue) -> {
								if (!newValue) {
									l.setGraphic(null);
									l.setText(ta.getText());
								}
							});

						}
					}
				};

				Platform.runLater(() -> table.requestLayout());
				// cell.setWrapText(true);

				return cell;
			}
		};

		descriptionColumn.setCellValueFactory(new PropertyValueFactory<Evento, String>("description"));
		descriptionColumn.setCellFactory(textAreaCellFactory);

		shiftColumn.setMinWidth(120);
		shiftColumn.setCellValueFactory(new PropertyValueFactory<Evento, Turnos>("shift"));
		shiftColumn.setCellFactory(ComboBoxTableCell.forTableColumn(shifts));
		shiftColumn.setOnEditCommit((EventHandler<CellEditEvent<Evento, Turnos>>) t -> {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setShift(t.getNewValue());
		});

		comentaryColumn.setMinWidth(220);
		comentaryColumn.setCellValueFactory(new PropertyValueFactory<Evento, String>("comentary"));
		comentaryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		comentaryColumn.setOnEditCommit((EventHandler<CellEditEvent<Evento, String>>) t -> {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setComentary(t.getNewValue());
			;
		});

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

		table.setItems(controller.data());

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

		controller.addListener(new ListChangeListener<Evento>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Evento> event) {
				if (event.next()) {
					if (event.wasUpdated()) {
						System.out.println("Evento cambio: " + event.toString());
					}
				}

			}

		});

		table.getItems().addListener(new ListChangeListener<Evento>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Evento> event) {
				System.out.println("Evento cambio: " + event.toString());
			}

		});

		table.getColumns().addAll(columnas);
		table.setId("event-table");
	}

}
