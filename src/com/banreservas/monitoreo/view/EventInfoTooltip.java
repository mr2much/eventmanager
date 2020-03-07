package com.banreservas.monitoreo.view;

import com.banreservas.monitoreo.model.EventInfo;

import javafx.scene.control.Tooltip;

public class EventInfoTooltip extends Tooltip {

	private EventInfo eventInfo;
	private final String msg = "Abierto por: %s\nFecha Apertura: %s\nFecha Cierre: %s\nUltima Modificación: %s\nUltima Edición por: %s";

	public EventInfoTooltip() {
	}

	public EventInfoTooltip(EventInfo info) {
		this.setEventInfo(info);
	}

	public void setEventInfo(EventInfo info) {
		this.eventInfo = info;

		this.setText(String.format(msg, eventInfo.getUsername(), eventInfo.getOpenDate(), eventInfo.getCloseDate(),
				eventInfo.getEditDate(), eventInfo.getEditUsername()));
	}

	@Override
	public String toString() {
		return "Testing Testing";
	}

}
