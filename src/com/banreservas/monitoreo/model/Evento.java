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

	public Evento() {
		entryDate = new SimpleStringProperty();
		ticketNumber = new SimpleStringProperty();
		description = new SimpleStringProperty();
		shift = new SimpleStringProperty();
		comentary = new SimpleStringProperty();
		status = new SimpleBooleanProperty();
	};

	private Evento(EventBuilder builder) {
		this(builder.id, builder.entryDate, builder.ticketNumber, builder.description, builder.shift, builder.comentary,
				builder.status, builder.severity);
	}

	public Evento(Long id, String entryDate, String ticketNumber, String description, String shift, String comentary,
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
		// if severity is null default to Severidad.BAJA
		this.severity = severity != null ? severity : Severidad.BAJA;
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
		if(this.status == null) {
			this.status = new SimpleBooleanProperty();
		}
		
		this.status.set(status);
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

		public EventBuilder id(Long id) {
			this.id = id;

			return this;
		}

		public EventBuilder entryDate(String date) {
			this.entryDate = date;

			return this;
		}

		public EventBuilder ticketNumber(String ticket) {
			this.ticketNumber = ticket;

			return this;
		}

		public EventBuilder description(String description) {
			this.description = description;

			return this;
		}

		public EventBuilder shift(String shift) {
			this.shift = shift;

			return this;
		}

		public EventBuilder comentary(String comentary) {
			this.comentary = comentary;

			return this;
		}

		public EventBuilder status(boolean status) {
			this.status = status;

			return this;
		}

		public EventBuilder severity(Severidad severity) {
			this.severity = severity;

			return this;
		}

		public Evento build() {
			return new Evento(this);
		}
	}
}
