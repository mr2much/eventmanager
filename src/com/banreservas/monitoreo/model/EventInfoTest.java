package com.banreservas.monitoreo.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import com.banreservas.monitoreo.model.EventInfo.EventInfoBuilder;

public class EventInfoTest {

	private final EventInfoBuilder infoBuilder = new EventInfo.EventInfoBuilder();

	@Test
	public void eventInfoEmptyTestReturnsEmptyString() {
		EventInfo a = infoBuilder.build();

		assertEquals(a.getOpenDate(), "");
		assertEquals(a.getCloseDate(), "");
		assertEquals(a.getEditDate(), "");
	}

	@Test
	public void eventInfoDateReturnsDate() {
		EventInfo a = infoBuilder.openDate(LocalDate.of(2017, 9, 12)).closeDate(LocalDate.now())
				.editDate(LocalDate.of(2016, 10, 1)).build();

		assertEquals(a.getOpenDate(), "12/09/2017");
		assertEquals(a.getCloseDate(), "24/12/2019");
		assertEquals(a.getEditDate(), "01/10/2016");
	}

}
