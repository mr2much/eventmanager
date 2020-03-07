import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.banreservas.monitoreo.controller.EventTableViewController;
import com.banreservas.monitoreo.controller.UserViewController;
import com.banreservas.monitoreo.model.EventInfo;
import com.banreservas.monitoreo.model.Evento;
import com.banreservas.monitoreo.model.Turnos;
import com.banreservas.monitoreo.repository.EventRepository;
import com.banreservas.monitoreo.repository.EventRepositoryStub;
import com.banreservas.monitoreo.repository.UserRepository;
import com.banreservas.monitoreo.repository.UserRepositoryStub;
import com.banreservas.monitoreo.view.EventDialog;
import com.banreservas.monitoreo.view.EventInfoTooltip;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EventManager extends Application {

	private MessageHandler messageHandler = new MessageHandler();

	private static final String localUsername = System.getProperty("user.name");
	private EventTableViewController eventController = new EventTableViewController();
	private UserViewController userController = new UserViewController();

	private EventRepository eventRepository = new EventRepositoryStub();
	private UserRepository userRepository = new UserRepositoryStub();

	TableColumn<Evento, String> entryDateColumn = new TableColumn<>("Fecha");
	TableColumn<Evento, String> ticketNumberColumn = new TableColumn<>("Ticket");
	TableColumn<Evento, String> descriptionColumn = new TableColumn<>("Reporte de Evento");
	TableColumn<Evento, Turnos> shiftColumn = new TableColumn<>("Turno");
	TableColumn<Evento, String> comentaryColumn = new TableColumn<>("Comentarios");
	TableColumn<Evento, Boolean> statusColumn = new TableColumn<>("Estatus");
	EventInfoTooltip eventInfoTooltip = new EventInfoTooltip();

	private static final String HOST = "172.26.150.23";
	private static final int TIMEOUT = 5000;

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
		if (showLoginScreen(stage)) {
			showEventManagerScreen(stage);
		}
	}

	public boolean showLoginScreen(Stage stage) throws IOException {
		try {
			GridPane root = FXMLLoader.load(getClass().getResource("res/login_form_content.fxml"));

			Scene loginScene = new Scene(root, 375, 200);
			stage.setScene(loginScene);
			stage.show();
		} catch (IOException ex) {
			throw new IOException("Error when opening login_form_content.fxml", ex);
		}

		return false;
	}

	public void showEventManagerScreen(Stage stage) throws IOException {
		eventController.setRepository(eventRepository);
		userController.setRepository(userRepository);

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
					eventController.add(evento);
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

		HBox userView = new HBox(10);

		ListView<String> onlineUsers = new ListView<>(userController.data());
		userView.getChildren().add(onlineUsers);
		grid.add(userView, 1, 0, 15, 1);

		Scene scene = new Scene(grid, 1300, 500);

		try {
			messageHandler = new MessageHandler(localUsername);
			messageHandler.start();
		} catch (SocketException ex) {
			System.out.println("Network connection couldn't be established: " + ex.getMessage());
			System.out.println("Reason: " + ex.getCause());
			System.out.println("Working in offline mode");

			messageHandler.setUsername(localUsername);
		}

		scene.getStylesheets().add(EventManager.class.getResource("@../../res/css/styles.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	private void createTableView() {
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setEditable(true);

		table.setItems(eventController.data());
		table.setRowFactory(tv -> new TableRow<Evento>() {

			@Override
			protected void updateItem(Evento item, boolean empty) {
				super.updateItem(item, empty);

				if (!empty) {
					this.setOnMouseClicked((EventHandler<MouseEvent>) e -> {
						if (e.getButton() == MouseButton.SECONDARY) {
							Platform.runLater(() -> table.requestLayout());
							boolean value = item.getStatus();
							item.setStatus(!value);
							this.getTableView().getItems().set(getIndex(), item);
						}

					});

					eventInfoTooltip = getEventInfoTooltip(item);
					this.setTooltip(eventInfoTooltip);
				}

			}
		});

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
			updateEventInfoTooltip(t.getRowValue());
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
										getTableView().getItems().get(getTableRow().getIndex())
												.setDescription(ta.getText());
										updateEventInfoTooltip(getTableView().getItems().get(getTableRow().getIndex()));

										ta.requestFocus();
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
			updateEventInfoTooltip(t.getRowValue());
		});

		comentaryColumn.setMinWidth(220);
		comentaryColumn.setCellValueFactory(new PropertyValueFactory<Evento, String>("comentary"));
		comentaryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		comentaryColumn.setOnEditCommit((EventHandler<CellEditEvent<Evento, String>>) t -> {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setComentary(t.getNewValue());
			updateEventInfoTooltip(t.getRowValue());
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

		eventController.addListener(new ListChangeListener<Evento>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Evento> event) {
				if (event.next()) {
					if (event.wasReplaced()) {

					}
				}
			}

		});

		table.getItems().addListener(new ListChangeListener<Evento>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Evento> event) {
				if (event.next()) {
					updateEventInfoTooltip(eventController.get(event.getFrom()));
				}
			}

		});

		table.getColumns().addAll(columnas);

		table.setId("event-table");
	}

	private EventInfoTooltip getEventInfoTooltip(Evento evento) {
		return new EventInfoTooltip(evento.getEventInfo());
	}

	private void updateEventInfoTooltip(Evento event) {
		EventInfo eventInfo = event.getEventInfo();
		eventInfo.setEditDate(LocalDate.now());
		eventInfo.setEditUsername(localUsername);

		if (isSolved(event)) {
			eventInfo.setCloseDate(LocalDate.now());
		} else {
			eventInfo.setCloseDate(null);
		}

		event.setEventInfo(eventInfo);

		eventInfoTooltip.setEventInfo(eventInfo);
		updateTable();
	}

	private void updateTable() {
		TableColumn<?, ?> column = table.getColumns().get(0);
		column.setVisible(false);
		column.setVisible(true);
	}

	private boolean isSolved(Evento event) {
		return event.getStatus();
	}

	private class MessageHandler extends Thread {

		private ObjectInputStream input;
		private ObjectOutputStream output;
		private String username;
		private Socket socket;

		public MessageHandler() {
		}

		public MessageHandler(String username) throws SocketException, IOException {
			this.username = username;

			try {
				socket = new Socket(HOST, TIMEOUT);

				output = new ObjectOutputStream(socket.getOutputStream());
				input = new ObjectInputStream(socket.getInputStream());
			} catch (SocketException e) {
				throw new SocketException(String
						.format("Cannot connect to host: %s\nMake sure the server is running and try again.", HOST));
			}
		}

		public void setUsername(String username) {
			this.username = username;
		}

		@Override
		public void run() {
			System.out.println("Client running");
		}

	}
}
