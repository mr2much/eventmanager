package com.banreservas.monitoreo.model;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;

public class NoDate implements ChronoLocalDate {

	@Override
	public long getLong(TemporalField arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Chronology getChronology() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int lengthOfMonth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ChronoPeriod until(ChronoLocalDate arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long until(Temporal arg0, TemporalUnit arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

}
