package com.banreservas.monitoreo.view;

import com.banreservas.monitoreo.model.EventInfo;

import javafx.scene.control.Tooltip;

public class EventInfoTooltip extends Tooltip {

	private EventInfo eventInfo;

	public EventInfoTooltip() {

	}

	public void setEventInfo(EventInfo info) {
		this.eventInfo = info;
	}

	@Override
	public String toString() {
		return "Testing Testing";
	}

}
