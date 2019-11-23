package com.banreservas.monitoreo.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Evento {
	private long id;
	private SimpleStringProperty entryDate;
	private SimpleStringProperty ticketNumber;
	private SimpleStringProperty description;
	private SimpleStringProperty shift;
	private SimpleStringProperty comentary;
	private SimpleBooleanProperty status;
	private Severidad severity;;

	public Evento() {
		entryDate = new SimpleStringProperty();
		ticketNumber = new SimpleStringProperty();
		description = new SimpleStringProperty();
		shift = new SimpleStringProperty();
		comentary = new SimpleStringProperty();
		status = new SimpleBooleanProperty();
	}

	private Evento(EventBuilder builder) {
		this(builder.id, builder.entryDate, builder.ticketNumber, builder.description, builder.shift, builder.comentary,
				builder.status, builder.severity);
	}

	public Evento(long id, String entryDate, String ticketNumber, String description, String shift, String comentary,
			boolean status, Severidad severity) {
		super();
		this.id = id;
		this.entryDate = new SimpleStringProperty(entryDate);
		this.ticketNumber = new SimpleStringProperty(ticketNumber);
		this.description = new SimpleStringProperty(description);
		this.shift = new SimpleStringProperty(shift);
		this.comentary = new SimpleStringProperty(comentary);
		setStatus(status);
		setSeverity(severity);
	}

	public final String getComentary() {
		return comentary.get();
	}

	public final StringProperty getComentaryProperty() {
		return comentary;
	}

	public final String getDescription() {
		return description.get();
	}

	public final StringProperty getDescriptionProperty() {
		return description;
	}

	public final String getEntryDate() {
		return entryDate.get();
	}

	public final StringProperty getEntryDateProperty() {
		return entryDate;
	}

	public final long getId() {
		return id;
	}

	public final Severidad getSeverity() {
		return severity;
	}

	public final String getShift() {
		return shift.get();
	}

	public final StringProperty getShiftProperty() {
		return shift;
	}

	public final boolean getStatus() {
		return status.get();
	}

	public final BooleanProperty getStatusProperty() {
		return status;
	}

	public final String getTicketNumber() {
		return ticketNumber.get();
	}

	public final StringProperty getTicketNumberProperty() {
		return ticketNumber;
	}

	public final void setComentary(String comentary) {
		this.comentary.set(comentary);
	}

	public final void setDescription(String description) {
		this.description.set(description);
	}

	public final void setEntryDate(String entryDate) {
		this.entryDate.set(entryDate);
	}

	public final void setId(long id) {
		this.id = id;
	}

	public final void setSeverity(Severidad severity) {
		// if severity is null default to Severidad.BAJA
		this.severity = severity != null ? severity : Severidad.BAJA;
	}

	public final void setShift(String shift) {
		this.shift.set(shift);
	}

	public final void setStatus(boolean status) {
		if (this.status == null) {
			this.status = new SimpleBooleanProperty();
		}

		this.status.set(status);
	}

	public final void setTicketNumber(String ticketNumber) {
		this.ticketNumber.set(ticketNumber);
	}

	@Override
	public String toString() {
		return "Evento [entryDate=" + getEntryDate() + ", ticketNumber=" + getTicketNumber() + ", description="
				+ getDescription() + ", shift=" + getShift() + ", comentary=" + getComentary() + ", status="
				+ getStatus() + "]";
	}

	public static class EventBuilder {
		private Long id;
		private String entryDate;
		private String ticketNumber;
		private String description;
		private String shift;
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

		public EventBuilder entryDate(String date) {
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

		public EventBuilder shift(String shift) {
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
