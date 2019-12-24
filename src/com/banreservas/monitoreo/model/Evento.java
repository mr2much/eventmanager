package com.banreservas.monitoreo.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Evento {

	private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private long id;
	private SimpleStringProperty entryDate;
	private SimpleStringProperty ticketNumber;
	private SimpleStringProperty description;
	private SimpleStringProperty comentary;
	private SimpleBooleanProperty status;
	private EventInfo eventInfo;
	private Turnos shift;
	private Severidad severity;

	{
		entryDate = new SimpleStringProperty();
		ticketNumber = new SimpleStringProperty();
		description = new SimpleStringProperty();
		comentary = new SimpleStringProperty();
		status = new SimpleBooleanProperty();
		eventInfo = new EventInfo();
	}

	public Evento() {

	}

	private Evento(EventBuilder builder) {
		this(builder.id, builder.entryDate, builder.ticketNumber, builder.description, builder.shift, builder.comentary,
				builder.status, builder.severity, builder.eventInfo);
	}

	public Evento(long id, LocalDate entryDate, String ticketNumber, String description, Turnos shift, String comentary,
			boolean status, Severidad severity, EventInfo eventInfo) {
		super();
		this.id = id;
		setEntryDate(entryDate);
		this.ticketNumber = new SimpleStringProperty(ticketNumber);
		this.description = new SimpleStringProperty(description);
		this.shift = shift;
		this.comentary = new SimpleStringProperty(comentary);
		setStatus(status);
		setSeverity(severity);
		this.eventInfo = eventInfo;
	}

	public void setEventInfo(EventInfo eventInfo) {
		this.eventInfo = eventInfo;
	}

	public EventInfo getEventInfo() {
		return eventInfo;
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
		return entryDate.get();
	}

	public final SimpleStringProperty getEntryDateProperty() {
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

	public final void setEntryDate(LocalDate date) {
		this.entryDate.set(date.format(dateFormat));
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
				+ ", description=" + description.get() + ", comentary=" + comentary.get() + ", status=" + status.get()
				+ ", shift=" + shift + ", severity=" + severity + ", eventInfo=" + eventInfo.toString() + "]";
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
		private EventInfo eventInfo;

		public EventBuilder() {
		}

		public Evento build() {
			return new Evento(this);
		}

		public EventBuilder eventInfo(EventInfo info) {
			this.eventInfo = info;

			return this;
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
