package application;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import data_model.DB;
import data_model.ReadData;
import data_model.WriteData;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import table_edit_files.EditCell;
import table_edit_files.MyDoubleStringConverter;
import transaction_objects.Transaction;

/**
 * The controller class for the main view of the application.
 * This class handles the initialization of the view components and the logic
 * for editing table cells.
 */
public class MainController {
	@FXML
	private TableView<Transaction> table;

	@FXML
	private TableColumn<Transaction, LocalDate> colDate;

	@FXML
	private TableColumn<Transaction, String> colDescription;

	@FXML
	private TableColumn<Transaction, Double> colGas;

	@FXML
	private TableColumn<Transaction, Double> colService;

	@FXML
	private TableColumn<Transaction, Double> colJohn;

	@FXML
	private TableColumn<Transaction, Double> colMedical;

	@FXML
	private TableColumn<Transaction, Double> colMisc;

	@FXML
	private TableColumn<Transaction, Double> colTotal;

	@FXML
	private Button btn_addNewRecord;

	@FXML
	private Label numValueText;

	@FXML
	private TableView<?> table;

	@FXML
	private Label totalGas;

	@FXML
	private GridPane totalGridPane;

	@FXML
	private Label totalJohn;

	@FXML
	private Label totalMedical;

	@FXML
	private Label totalMisc;

	@FXML
	private Label totalService;

	Boolean okToSave = true;

	public void initialize() {

		totalGridPane.getStyleClass().add("total-grid-pane");
		inputVBox.getStyleClass().add("input-vbox");
		table.getStyleClass().add("my-table-view");
		colDescription.getStyleClass().add("description-column");
		colTotal.getStyleClass().add("total-column");
		errorInputLabel.getStyleClass().add("error-input-label");

		newGasInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) { // Focus lost
				okToSave = isValidDouble(newGasInput);
			}
		});
		newJohnInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) { // Focus lost
				okToSave = isValidDouble(newJohnInput);
			}
		});

		newMedicalInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) { // Focus lost
				okToSave = isValidDouble(newMedicalInput);
			}
		});

		newServiceInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) { // Focus lost
				okToSave = isValidDouble(newServiceInput);
			}
		});

		newMiscInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) { // Focus lost
				okToSave = isValidDouble(newMiscInput);
			}
		});

		btn_addRecord.setOnAction(event -> buttonAddRecord());
		btn_deleteRecord.setOnAction(event -> buttonDeleteRecord());
		btn_addNewRecord.setOnAction(event -> buttonAddNewRecord());

		// scroll to the last row
		table.scrollTo(table.getItems().size() - 1);

		newDateInput.setValue(LocalDate.now());

		colDate.setCellValueFactory(new PropertyValueFactory<Transaction, LocalDate>("date"));
		colDate.setCellFactory(column -> {
			return new TableCell<Transaction, LocalDate>() {
				private final DatePicker datePicker = new DatePicker();

				{
					datePicker.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
						if (event.getCode() == KeyCode.ENTER) {
							commitEdit(datePicker.getValue());
						}
					});

					datePicker.setOnAction(event -> {
						commitEdit(datePicker.getValue());
					});
				}

				@Override
				protected void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);

					if (empty) {
						setGraphic(null);
					} else {
						datePicker.setValue(item);
						setGraphic(datePicker);
						setPadding(new Insets(0, 5, 0, 0));
					}
				}
			};
		});
		colDate.setOnEditCommit(this::colDate_OnEditCommit);

		colDescription.setCellValueFactory(new PropertyValueFactory<Transaction, String>("description"));
		colDescription.setCellFactory(TextFieldTableCell.forTableColumn());
		colDescription.setOnEditCommit(this::colDescription_OnEditCommit);

		colGas.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("gas"));
		colGas.setCellFactory(EditCell.<Transaction, Double>forTableColumn(new MyDoubleStringConverter()));
		colGas.setOnEditCommit(this::colGas_OnEditCommit);
		String columnClass = "cell-with-double-values";
		colGas.getStyleClass().add(columnClass);

		colService.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("service"));
		colService.setCellFactory(EditCell.<Transaction, Double>forTableColumn(new MyDoubleStringConverter()));
		colService.setOnEditCommit(this::colService_OnEditCommit);
		colService.getStyleClass().add(columnClass);

		colJohn.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("john"));
		colJohn.setCellFactory(EditCell.<Transaction, Double>forTableColumn(new MyDoubleStringConverter()));
		colJohn.setOnEditCommit(this::colJohn_OnEditCommit);
		colJohn.getStyleClass().add(columnClass);

		colMedical.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("medical"));
		colMedical.setCellFactory(EditCell.<Transaction, Double>forTableColumn(new MyDoubleStringConverter()));
		colMedical.setOnEditCommit(this::colMedical_OnEditCommit);
		colMedical.getStyleClass().add(columnClass);

		colMisc.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("misc"));
		colMisc.setCellFactory(EditCell.<Transaction, Double>forTableColumn(new MyDoubleStringConverter()));
		colMisc.setOnEditCommit(this::colMisc_OnEditCommit);
		colMisc.getStyleClass().add(columnClass);

		colTotal.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("total"));
		colTotal.setCellFactory(EditCell.<Transaction, Double>forTableColumn(new MyDoubleStringConverter()));
		colTotal.getStyleClass().add("total-column");

		setTableEditable();
	}

	/************************************************/
	private void setTableEditable() {
		table.getStyleClass().add("my-table-view");

		table.setEditable(true);
		// allows the individual cells to be selected
		table.getSelectionModel().cellSelectionEnabledProperty().set(true);
		// when character or numbers pressed it will start edit in editable
		// fields
		table.setOnKeyPressed(event -> {
			if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
				editFocusedCell();
			} else if (event.getCode() == KeyCode.TAB) {
				table.getSelectionModel().selectNext();
				event.consume();
			} else if (event.getCode() == KeyCode.TAB && event.isShiftDown()) {
				selectPrevious();
				event.consume();
			}
		});
	}

	/************************************************/
	@SuppressWarnings("unchecked")
	private void editFocusedCell() {
		final TablePosition<Transaction, ?> focusedCell = table.focusModelProperty().get().focusedCellProperty().get();
		table.edit(focusedCell.getRow(), focusedCell.getTableColumn());
	}

	/************************************************/
	@SuppressWarnings("unchecked")
	private void selectPrevious() {
		if (table.getSelectionModel().isCellSelectionEnabled()) {
			TablePosition<Transaction, ?> pos = table.getFocusModel().getFocusedCell();
			if (pos.getColumn() - 1 >= 0) {
				// go to previous row
				table.getSelectionModel().select(pos.getRow(), getTableColumn(pos.getTableColumn(), -1));
			} else if (pos.getRow() < table.getItems().size()) {
				// wrap to end of previous row
				table.getSelectionModel().select(pos.getRow()
						- 1,
						table.getVisibleLeafColumn(table.getVisibleLeafColumns().size()
								- 1));
			}
		} else {
			int focusIndex = table.getFocusModel().getFocusedIndex();
			if (focusIndex == -1) {
				table.getSelectionModel().select(table.getItems().size() - 1);
			} else if (focusIndex > 0) {
				table.getSelectionModel().select(focusIndex - 1);
			}
		}
	}

	/************************************************/
	private TableColumn<Transaction, ?> getTableColumn(final TableColumn<Transaction, ?> column, int offset) {
		int columnIndex = table.getVisibleLeafIndex(column);
		int newColumnIndex = columnIndex + offset;
		return table.getVisibleLeafColumn(newColumnIndex);
	}

	/************************************************/
	@SuppressWarnings("unchecked")
	public void colDate_OnEditCommit(Event e) {
		TableColumn.CellEditEvent<Transaction, LocalDate> cellEdit;
		cellEdit = (TableColumn.CellEditEvent<Transaction, LocalDate>) e;
		Transaction transaction = cellEdit.getRowValue();

		transaction.setDate(cellEdit.getNewValue());
		if (!WriteData.updateRecord(DB.COL_TRANSACTIONS_DATE, transaction)) {
			transaction.setDate(cellEdit.getOldValue());
		}
		table.refresh();
	}

	/************************************************/
	@SuppressWarnings("unchecked")
	public void colDescription_OnEditCommit(Event e) {
		TableColumn.CellEditEvent<Transaction, String> cellEdit;
		cellEdit = (TableColumn.CellEditEvent<Transaction, String>) e;
		Transaction transaction = cellEdit.getRowValue();

		transaction.setDescription(cellEdit.getNewValue());
		if (!WriteData.updateRecord(DB.COL_TRANSACTIONS_DESCRIPTION, transaction)) {
			transaction.setDescription(cellEdit.getOldValue());
		}
		table.refresh();
	}

	/************************************************/
	@SuppressWarnings("unchecked")
	public void colGas_OnEditCommit(Event e) {
		TableColumn.CellEditEvent<Transaction, Double> cellEdit;
		cellEdit = (TableColumn.CellEditEvent<Transaction, Double>) e;

		if (cellEdit.getNewValue() != null) {
			Transaction transaction = cellEdit.getRowValue();
			transaction.setGas(cellEdit.getNewValue());
			if (!WriteData.updateRecord(DB.COL_TRANSACTIONS_GAS, transaction)) {
				transaction.setGas(cellEdit.getOldValue());
			}
			getTotals();
		}
		table.refresh();
	}

	/************************************************/
	@SuppressWarnings("unchecked")
	public void colService_OnEditCommit(Event e) {
		TableColumn.CellEditEvent<Transaction, Double> cellEdit;
		cellEdit = (TableColumn.CellEditEvent<Transaction, Double>) e;

		if (cellEdit.getNewValue() != null) {
			Transaction transaction = cellEdit.getRowValue();

			transaction.setService(cellEdit.getNewValue());
			if (!WriteData.updateRecord(DB.COL_TRANSACTIONS_SERVICE, transaction)) {
				transaction.setService(cellEdit.getOldValue());
			}
			getTotals();
		}
		table.refresh();
	}

	/************************************************/
	@SuppressWarnings("unchecked")
	public void colJohn_OnEditCommit(Event e) {
		TableColumn.CellEditEvent<Transaction, Double> cellEdit;
		cellEdit = (TableColumn.CellEditEvent<Transaction, Double>) e;

		if (cellEdit.getNewValue() != null) {
			Transaction transaction = cellEdit.getRowValue();

			transaction.setJohn(cellEdit.getNewValue());
			if (!WriteData.updateRecord(DB.COL_TRANSACTIONS_JOHN, transaction)) {
				transaction.setJohn(cellEdit.getOldValue());
			}
			getTotals();
		}
		table.refresh();
	}

	/************************************************/
	@SuppressWarnings("unchecked")
	public void colMedical_OnEditCommit(Event e) {
		TableColumn.CellEditEvent<Transaction, Double> cellEdit;
		cellEdit = (TableColumn.CellEditEvent<Transaction, Double>) e;

		if (cellEdit.getNewValue() != null) {
			Transaction transaction = cellEdit.getRowValue();

			transaction.setMedical(cellEdit.getNewValue());
			if (!WriteData.updateRecord(DB.COL_TRANSACTIONS_MEDICAL, transaction)) {
				transaction.setMedical(cellEdit.getOldValue());
			}
			getTotals();
		}
		table.refresh();
	}

	/************************************************/
	@SuppressWarnings("unchecked")
	public void colMisc_OnEditCommit(Event e) {
		TableColumn.CellEditEvent<Transaction, Double> cellEdit;
		cellEdit = (TableColumn.CellEditEvent<Transaction, Double>) e;

		if (cellEdit.getNewValue() != null) {
			Transaction transaction = cellEdit.getRowValue();

			transaction.setMisc(cellEdit.getNewValue());
			if (!WriteData.updateRecord(DB.COL_TRANSACTIONS_MISC, transaction)) {
				transaction.setMisc(cellEdit.getOldValue());
			}
			getTotals();
		}
		table.refresh();
	}

	/******************************************/
	public void updateTotals(List<Double> totalsList) {
		if (totalsList.size() == 5) {
			totalGas.setText(new DecimalFormat("#.0#").format(totalsList.get(0)));
			totalService.setText(new DecimalFormat("#.0#").format(totalsList.get(1)));
			totalJohn.setText(new DecimalFormat("#.0#").format(totalsList.get(2)));
			totalMedical.setText(new DecimalFormat("#.0#").format(totalsList.get(3)));
			totalMisc.setText(new DecimalFormat("#.0#").format(totalsList.get(4)));
		}
	}

	/******************************************/
	public void getTransactions() {
		@SuppressWarnings("unchecked")
		Task<ObservableList<Transaction>> task = new GetAllTransactions();
		table.itemsProperty().bind(task.valueProperty());

		new Thread(task).start();
	}

	public void getTotals() {
		Task<List<Double>> task = new GetTotals();

		task.setOnSucceeded(e -> {
			List<Double> totals = task.valueProperty().get();
			updateTotals(totals);

			double acctTotal = 0;
			for (Double d : totals) {
				acctTotal += d;
			}

			numValueText.setText(String.format("%.2f", acctTotal));
		});

		new Thread(task).start();
	}

	/**
	 * Adds a new record to the table based on the input values.
	 * Clears the input fields after adding the record.
	 * Updates the totals after adding the record.
	 */
	private void buttonAddRecord() {

		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(.5), errorInputLabel);
		fadeTransition.setFromValue(1.0); // Fully opaque
		fadeTransition.setToValue(0.0); // Fully transparent
		fadeTransition.setAutoReverse(true); // Reverse the animation (fade in)
		fadeTransition.setCycleCount(FadeTransition.INDEFINITE); // Repeat indefinitely

		LocalDate date = newDateInput.getValue();
		String description = newDescriptionInput.getText();
		double gas = MyUtil.stringToDouble(newGasInput.getText(), 0.);
		double service = MyUtil.stringToDouble(newServiceInput.getText(), 0.);
		double john = MyUtil.stringToDouble(newJohnInput.getText(), 0.);
		double medical = MyUtil.stringToDouble(newMedicalInput.getText(), 0.);
		double misc = MyUtil.stringToDouble(newMiscInput.getText(), 0.);

		boolean isValid = true;
		if (gas == 0 && service == 0 && john == 0 && medical == 0 && misc == 0) {
			fadeTransition.play();
			errorInputLabel.setVisible(true);
			if (!okToSave)
				errorInputLabel.setText(description);
			errorInputLabel.setText("At least one value must be entered.");
			isValid = false;
			return;
		}

		if (isValid && okToSave) {
			Transaction newTransaction = new Transaction(date, description, gas, service, john, medical, misc);
			fadeTransition.stop();
			errorInputLabel.setVisible(false);
			if (WriteData.insertRecord(newTransaction))
				table.getItems().add(newTransaction);
		}

		// Clear input fields
		newDescriptionInput.clear();
		newGasInput.clear();
		newServiceInput.clear();
		newJohnInput.clear();
		newMedicalInput.clear();
		newMiscInput.clear();

		// Update totals
		getTotals();

		// make selected row visible
		// select the record just added
		table.getSortOrder().add(colDate);

		// select the last row
		table.getSelectionModel().select(table.getItems().size() - 1);

		// scroll to the last row
		table.scrollTo(table.getItems().size() - 1);
	}

	/**
	 * Deletes the selected record from the table and updates the totals.
	 */
	private void buttonDeleteRecord() {
		Transaction transaction = table.getSelectionModel().getSelectedItem();

		if (transaction != null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirm Deletion");
			alert.setHeaderText("Are you sure you want to delete this record?");
			alert.setContentText(transaction.transactionDecription());

			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == ButtonType.OK) {
				if (WriteData.deleteRecord(transaction)) {
					table.getItems().remove(transaction);
					getTotals();
				}

				// select the record after the one just deleted
				if (table.getItems().size() > 0) {
					table.getSelectionModel().selectNext();
				}
			}

		}
	}

	// open a dialog to add a new record
	// create a new transaction object
	// add the new transaction object to the table
	// update the totals
	private void buttonAddNewRecord() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AddRecord.fxml"));
			Parent root = loader.load();

			DialogController controller = loader.getController();
			// controller.setMainController(this);

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Add New Record");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if the input in the TextField is a valid double.
	 * If the input is not valid, an error message is displayed and the TextField
	 * background color is changed to pink.
	 * If the input is valid, the error message is hidden and the TextField
	 * background color is reset to white.
	 *
	 * @param textField The TextField to check for a valid double.
	 * @return True if the input is a valid double, false otherwise.
	 */
	private Boolean isValidDouble(TextField textField) {
		Boolean isValid = true;
		try {
			Double.parseDouble(textField.getText());
			errorInputLabel.setVisible(false);
			textField.setStyle("-fx-control-inner-background: white;"); // Reset to default color if input is valid
			return isValid;
		} catch (NumberFormatException e) {
			errorInputLabel.setVisible(true);
			errorInputLabel.setText("Invalid input. Please enter a number.");
			textField.setStyle("-fx-control-inner-background: pink;"); // Change background color to indicate error
			isValid = false;
			return isValid;
		}
	}

}

/******************************************/
/**
 * A task that retrieves all transactions from the database and returns them as
 * an ObservableList.
 */
@SuppressWarnings("rawtypes")
class GetAllTransactions extends Task {
	/**
	 * Retrieves all transactions from the database and returns them as an
	 * ObservableList.
	 *
	 * @return The ObservableList of transactions.
	 */
	public ObservableList<Transaction> call() {
		return FXCollections.observableArrayList(ReadData.getTransactions(DB.ORDER_BY_ASC));
	}
}

/**
 * A task that retrieves the totals from the data source.
 */
class GetTotals extends Task<List<Double>> {
	/**
	 * Retrieves the totals from the data source.
	 *
	 * @return a list of doubles representing the totals
	 */
	public List<Double> call() {
		return ReadData.getTotals();
	}
}