package transaction_objects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Transaction {
	private int id = 0;
	private SimpleObjectProperty<LocalDate> date = new SimpleObjectProperty<LocalDate>();
	private SimpleStringProperty description = new SimpleStringProperty("");
	private SimpleDoubleProperty gas = new SimpleDoubleProperty();
	private SimpleDoubleProperty service = new SimpleDoubleProperty(0.0);
	private SimpleDoubleProperty john = new SimpleDoubleProperty(0.0);
	private SimpleDoubleProperty medical = new SimpleDoubleProperty(0.0);
	private SimpleDoubleProperty misc = new SimpleDoubleProperty(0.0);
	private SimpleDoubleProperty total = new SimpleDoubleProperty(0.0);

	/*********************************************************/
	public Transaction() {
	
		// This constructor is intentionally left empty.
		// Add implementation if needed.
	}

	public Transaction(LocalDate date, String description, double gas, double service, double john, double medical, double misc) {
		this.date.set(date);
		this.description.set(description);
		this.gas.set(gas);
		this.service.set(service);
		this.john.set(john);
		this.medical.set(medical);
		this.misc.set(misc);
		updateTotal();
	}
	// ----------------------------------------
	public int getId() {
		return id;
	}

	public void setId(int newId) {
		this.id = newId;
	}

	// ----------------------------------------
	// Date getters and setters
	// ----------------------------------------
	public LocalDate getDate() {
		return date.get();
	}

	public void setDate(LocalDate newDate) {
		this.date.set(newDate);
	}

	public ObjectProperty<LocalDate> getDateProperty()
	{
		return this.date;
	}

	// ----------------------------------------
	public void setDescription(String disc) {
		this.description.set(disc);
	}

	public String getDescription() {
		return description.get();
	}

	public StringProperty descriptionProperty() {
		return this.description;
	}

	// ----------------------------------------
	public double getGas() {
		return gas.get();
	}

	public void setGas(double gas) {
		this.gas.set(gas);
		updateTotal();
	}

	// ----------------------------------------
	public double getService() {
		return service.get();
	}

	public void setService(double service) {
		this.service.set(service);
		updateTotal();
	}

	// ----------------------------------------
	public double getJohn() {
		return john.get();
	}

	public void setJohn(double john) {
		this.john.set(john);
		updateTotal();
	}

	// ----------------------------------------
	public double getMedical() {
		return medical.get();
	}

	public void setMedical(double med) {
		this.medical.set(med);
		updateTotal();
	}

	// ----------------------------------------
	public double getMisc() {
		return misc.get();
	}

	public void setMisc(double misc) {
		this.misc.set(misc);
		updateTotal();
	}

	// ----------------------------------------
	public double getTotal() {
		return total.get();
	}

	// ----------------------------------------
	protected void updateTotal() {
		total.set(getGas() + getService() + getJohn() +
				getMedical() + getMisc());
	}

	@Override
	public String toString() {
		return "ID= " + getId()
				+ " Date= " + getDate()
				+ " Disc= " + getDescription()
				+ " gas= " + getGas()
				+ " service= " + getService()
				+ " john= " + getJohn()
				+ " medical= " + getMedical()
				+ " misc= " + getMisc()
				+ " total= " + getTotal() + "]";
	}

	public String transactionDecription() {
		return "Date: " + getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
		 "\nDescription: " + getDescription() + 
		 "\nTotal:  " + getTotal();
	}

}
