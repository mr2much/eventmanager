package com.banreservas.monitoreo.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Evento {

	private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private long id;
	private SimpleObjectProperty<LocalDate> entryDate = new SimpleObjectProperty<>();
	private SimpleStringProperty ticketNumber = new SimpleStringProperty();
	private SimpleStringProperty description = new SimpleStringProperty();
	private SimpleStringProperty comentary = new SimpleStringProperty();
	private SimpleBooleanProperty status = new SimpleBooleanProperty();
	private Turnos shift;
	private Severidad severity;

	public Evento() {

	}

	private Evento(EventBuilder builder) {
		this(builder.id, builder.entryDate, builder.ticketNumber, builder.description, builder.shift, builder.comentary,
				builder.status, builder.severity);
	}

	public Evento(long id, LocalDate entryDate, String ticketNumber, String description, Turnos shift, String comentary,
			boolean status, Severidad severity) {
		super();
		this.id = id;
		this.entryDate = new SimpleObjectProperty<>(entryDate);
		this.ticketNumber = new SimpleStringProperty(ticketNumber);
		this.description = new SimpleStringProperty(description);
		this.shift = shift;
		this.comentary = new SimpleStringProperty(comentary);
		setStatus(status);
		setSeverity(severity);
	}

	public final String getComentary() {
		return comentary.get();
	}

	public final SimpleStringProperty getComentaryProperty() {
		return comentary;
	}

	public final String getDescription() {
		return description.get();
	}

	public final SimpleStringProperty getDescriptionProperty() {
		return description;
	}

	public final String getEntryDate() {
		return dateFormat.format(entryDate.get());
	}

	public final SimpleObjectProperty<LocalDate> getEntryDateProperty() {
		return entryDate;
	}

	public final long getId() {
		return id;
	}

	public final Severidad getSeverity() {
		return severity;
	}

	public final Turnos getShift() {
		return shift;
	}

	public final boolean getStatus() {
		return status.get();
	}

	public final SimpleBooleanProperty getStatusProperty() {
		return status;
	}

	public final String getTicketNumber() {
		return ticketNumber.get();
	}

	public final SimpleStringProperty getTicketNumberProperty() {
		return ticketNumber;
	}

	public final void setComentary(String comentary) {
		this.comentary.set(comentary);
	}

	public final void setDescription(String description) {
		this.description.set(description);
	}

	public final void setEntryDate(LocalDate entryDate) {
		this.entryDate.set(entryDate);
	}

	public final void setId(long id) {
		this.id = id;
	}

	public final void setSeverity(Severidad severity) {
		// if severity is null default to Severidad.BAJA
		this.severity = severity != null ? severity : Severidad.BAJA;
	}

	public final void setShift(Turnos shift) {
		this.shift = shift;
	}

	public final void setStatus(boolean status) {
		if (this.status == null) {
			this.status = new SimpleBooleanProperty();
		}

		this.status.set(status);
	}

	public final void setTicketNumber(String ticketNumber) {
		if (this.ticketNumber == null) {
			System.out.println("Setting ticketnumberproperty of " + this.ticketNumber);
			this.ticketNumber = new SimpleStringProperty();
		}

		this.ticketNumber.set(ticketNumber);
	}

	@Override
	public String toString() {
		return "Evento [id=" + id + ", entryDate=" + entryDate.get() + ", ticketNumber=" + ticketNumber.get()
				+ ", description=" + description.get() + ", shift=" + shift + ", comentary=" + comentary.get()
				+ ", status=" + status.get() + ", severity=" + severity + "]";
	}

	public static class EventBuilder {
		private long id;
		private LocalDate entryDate;
		private String ticketNumber;
		private String description;
		private Turnos shift;
		private String comentary;
		private boolean status;
		private Severidad severity;

		public EventBuilder() {
		}

		public Evento build() {
			return new Evento(this);
		}

		public EventBuilder comentary(String comentary) {
			this.comentary = comentary;

			return this;
		}

		public EventBuilder description(String description) {
			this.description = description;

			return this;
		}

		public EventBuilder entryDate(LocalDate date) {
			this.entryDate = date;

			return this;
		}

		public EventBuilder id(Long id) {
			this.id = id;

			return this;
		}

		public EventBuilder severity(Severidad severity) {
			this.severity = severity;

			return this;
		}

		public EventBuilder shift(Turnos shift) {
			this.shift = shift;

			return this;
		}

		public EventBuilder status(boolean status) {
			this.status = status;

			return this;
		}

		public EventBuilder ticketNumber(String ticket) {
			this.ticketNumber = ticket;

			return this;
		}
	}

}
