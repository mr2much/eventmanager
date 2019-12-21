package com.banreservas.monitoreo.model;

import java.time.LocalDate;

public class EventInfo {

	private long id;
	private String username;
	private LocalDate openDate;
	private LocalDate closeDate;
	private LocalDate editDate;
	private String editUsername;

	public EventInfo() {
	}

	public EventInfo(EventInfoBuilder builder) {
		this.id = builder.id;
		this.username = builder.username;
		this.openDate = builder.openDate;
		this.closeDate = builder.closeDate;
		this.editDate = builder.editDate;
		this.editUsername = builder.editUsername;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public LocalDate getCloseDate() {
		return closeDate;
	}

	public LocalDate getEditDate() {
		return editDate;
	}

	public String getEditUsername() {
		return editUsername;
	}

	public LocalDate getOpenDate() {
		return openDate;
	}

	public String getUsername() {
		return username;
	}

	public void setCloseDate(LocalDate closeDate) {
		this.closeDate = closeDate;
	}

	public void setEditDate(LocalDate editDate) {
		this.editDate = editDate;
	}

	public void setEditUsername(String editUsername) {
		this.editUsername = editUsername;
	}

	public void setOpenDate(LocalDate openDate) {
		this.openDate = openDate;
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
