package com.banreservas.monitoreo.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Evento {
	private Long id;
	private SimpleStringProperty entryDate;
	private SimpleStringProperty ticketNumber;
	private SimpleStringProperty description;
	private SimpleStringProperty shift;
	private SimpleStringProperty comentary;
	private SimpleBooleanProperty status;
	private Severidad severity;

	public Evento() {};

	public Evento(Long id, String entryDate, String ticketNumber, String description, String shift, String comentary,
			boolean status, Severidad severity) {
		super();
		this.id = id;
		this.entryDate = new SimpleStringProperty(entryDate);
		this.ticketNumber = new SimpleStringProperty(ticketNumber);
		this.description = new SimpleStringProperty(description);
		this.shift = new SimpleStringProperty(shift);
		this.comentary = new SimpleStringProperty(comentary);
		this.status = new SimpleBooleanProperty(status);
		this.severity = severity;
	}

	public Severidad getSeverity() {
		return severity;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setSeverity(Severidad severity) {
		this.severity = severity;
	}

	public String getComentary() {
		return comentary.get();
	}

	public String getDescription() {
		return description.get();
	}

	public String getEntryDate() {
		return entryDate.get();
	}

	public String getShift() {
		return shift.get();
	}

	public Boolean getStatus() {
		return status.getValue();
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber.set(ticketNumber);
	}

	public String getTicketNumber() {
		return ticketNumber.get();
	}

	public void setComentary(String comentary) {
		this.comentary.set(comentary);
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public void setEntryDate(String entryDate) {
		this.entryDate.set(entryDate);
	}

	public void setShift(String shift) {
		this.shift.set(shift);
	}

	public void setStatus(boolean status) {
		this.status.set(status);
	}

	@Override
	public String toString() {
		return "Evento [entryDate=" + getEntryDate() + ", ticketNumber=" + getTicketNumber() + ", description="
				+ getDescription() + ", shift=" + getShift() + ", comentary=" + getComentary() + ", status="
				+ getStatus() + "]";
	}
}
