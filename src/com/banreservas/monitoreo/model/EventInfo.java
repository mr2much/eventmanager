package com.banreservas.monitoreo.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventInfo {

	private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private long id;
	private String username;
	private String openDate = "";
	private String closeDate = "";
	private String editDate = "";
	private String editUsername;

	public EventInfo() {
	}

	public EventInfo(EventInfoBuilder builder) {
		this.id = builder.id;
		this.username = builder.username;
		setOpenDate(builder.openDate);
		setCloseDate(builder.closeDate);
		setEditDate(builder.editDate);
		this.editUsername = builder.editUsername;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getCloseDate() {
		return closeDate;
	}

	public String getEditDate() {
		return editDate;
	}

	public String getEditUsername() {
		return editUsername;
	}

	public String getOpenDate() {
		return openDate;
	}

	public String getUsername() {
		return username;
	}

	public void setCloseDate(LocalDate date) {
		if (date != null) {
			this.closeDate = date.format(dateFormat);
		} else {
			this.closeDate = "";
		}
	}

	public void setEditDate(LocalDate date) {
		if (date != null) {
			this.editDate = date.format(dateFormat);
		} else {
			this.closeDate = "";
		}
	}

	public void setEditUsername(String editUsername) {
		this.editUsername = editUsername;
	}

	public void setOpenDate(LocalDate date) {
		if (date != null) {
			this.openDate = date.format(dateFormat);
		} else {
			this.closeDate = "";
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "EventInfo [id=" + id + ", username=" + username + ", openDate=" + openDate + ", closeDate=" + closeDate
				+ ", editDate=" + editDate + ", editUsername=" + editUsername + "]";
	}

	public static class EventInfoBuilder {

		private long id;
		private String username;
		private LocalDate openDate;
		private LocalDate closeDate;
		private LocalDate editDate;
		private String editUsername;

		public EventInfoBuilder() {

		}

		public EventInfo build() {
			return new EventInfo(this);
		}

		public EventInfoBuilder id(long id) {
			this.id = id;

			return this;
		}

		public EventInfoBuilder closeDate(LocalDate date) {
			this.closeDate = date;

			return this;
		}

		public EventInfoBuilder editDate(LocalDate date) {
			this.editDate = date;

			return this;
		}

		public EventInfoBuilder editUsername(String username) {
			this.editUsername = username;

			return this;
		}

		public EventInfoBuilder openDate(LocalDate date) {
			this.openDate = date;

			return this;
		}

		public EventInfoBuilder username(String username) {
			this.username = username;

			return this;
		}
	}
}
