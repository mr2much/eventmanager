package com.banreservas.monitoreo.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.banreservas.monitoreo.model.EventInfo;
import com.banreservas.monitoreo.model.Evento;
import com.banreservas.monitoreo.model.Severidad;
import com.banreservas.monitoreo.model.Turnos;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class EventRepositoryStub implements EventRepository {

	private ObservableList<Evento> events = FXCollections.observableArrayList();
	private Long idIndex = 1L;

	{
		Evento.EventBuilder builder = new Evento.EventBuilder();
		EventInfo.EventInfoBuilder infoBuilder = new EventInfo.EventInfoBuilder();

		EventInfo eventInfo = infoBuilder.id(idIndex).username("ALockward").openDate(LocalDate.of(2019, 3, 12))
				.editDate(LocalDate.of(2017, 4, 14)).editUsername("ALockward").build();

		Evento a = builder.id(idIndex).entryDate(LocalDate.of(2019, 3, 12)).ticketNumber("335361543")
				.description(
						"Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas)")
				.shift(Turnos.MADRUGADA).comentary("Gerencia Soporte Sistemas Distribuidos.").status(false)
				.severity(Severidad.ALTA).eventInfo(eventInfo).build();

		idIndex++;
		eventInfo = infoBuilder.id(idIndex).username("GBonilla").openDate(LocalDate.now()).closeDate(LocalDate.now())
				.editDate(LocalDate.of(2015, 6, 30)).editUsername("ALockward").build();

		Evento b = builder.id(idIndex).entryDate(LocalDate.of(2019, 6, 23)).ticketNumber("335362015")
				.description("Inconvenientes con las consultas de firmas v�a Siebel CRM").shift(Turnos.VESPERTINO)
				.comentary("DTEL Zona Metro Este").status(true).severity(Severidad.BAJA).eventInfo(eventInfo).build();

		idIndex++;
		eventInfo = infoBuilder.id(idIndex).username("ALockward").openDate(LocalDate.now()).closeDate(null)
				.editDate(LocalDate.of(2018, 11, 29)).editUsername("GBonilla").build();

		Evento c = builder.id(idIndex).entryDate(LocalDate.of(2019, 9, 11)).ticketNumber("335361566")
				.description(
						"Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas) Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas) Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas)")
				.shift(Turnos.MATUTINO).comentary("Gerencia Soporte Sistemas Distribuidos.").status(false)
				.severity(Severidad.MEDIA).eventInfo(eventInfo).build();
		idIndex++;

		eventInfo = infoBuilder.id(idIndex).username("GBonilla").openDate(LocalDate.now()).closeDate(LocalDate.now())
				.editDate(LocalDate.now()).editUsername("GBonilla").build();

		Evento d = builder.id(idIndex).entryDate(LocalDate.of(2019, 9, 26)).ticketNumber("335361766")
				.description(
						"Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas) Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas) Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas) Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas) Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas) Banca Solidaria Charles de Gaulle fuera de servicio por problemas del inversor (baterias descargadas)")
				.shift(Turnos.MATUTINO).comentary("Gerencia Soporte Sistemas Distribuidos.").status(true)
				.severity(Severidad.BAJA).eventInfo(eventInfo).build();

		events.add(a);
		events.add(b);
		events.add(c);
		events.add(d);
	}

	@Override
	public boolean add(Evento event) {
		idIndex++;
		event.setId(idIndex);
		event.getEventInfo().setId(idIndex);
		return events.add(event);
	}

	@Override
	public void add(int index, Evento event) {
		events.add(index, event);
	}

	@Override
	public boolean addAll(Collection<? extends Evento> events) {
		return this.events.addAll(events);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Evento> events) {
		return this.events.addAll(index, events);
	}

	@Override
	public void clear() {
		events.clear();
	}

	@Override
	public boolean contains(Object o) {
		return events.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> events) {
		return this.events.containsAll(events);
	}

	@Override
	public Evento get(int index) {
		return events.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return events.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return events.isEmpty();
	}

	@Override
	public Iterator<Evento> iterator() {
		return events.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return events.lastIndexOf(o);
	}

	@Override
	public ListIterator<Evento> listIterator() {
		return events.listIterator();
	}

	@Override
	public ListIterator<Evento> listIterator(int index) {
		return events.listIterator(index);
	}

	@Override
	public boolean remove(Object o) {
		return events.remove(o);
	}

	@Override
	public Evento remove(int index) {
		return events.remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> events) {
		return events.removeAll(events);
	}

	@Override
	public boolean retainAll(Collection<?> events) {
		return events.retainAll(events);
	}

	@Override
	public Evento set(int index, Evento event) {
		return this.events.set(index, event);
	}

	@Override
	public int size() {
		return events.size();
	}

	@Override
	public List<Evento> subList(int fromIndex, int toIndex) {
		return events.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return events.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return events.toArray(a);
	}

	@Override
	public void addListener(InvalidationListener listener) {
		((ObservableList<Evento>) events).addListener(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		((ObservableList<Evento>) events).removeListener(listener);
	}

	@Override
	public boolean addAll(Evento... events) {
		return ((ObservableList<Evento>) this.events).addAll(events);
	}

	@Override
	public void addListener(ListChangeListener<? super Evento> listener) {
		((ObservableList<Evento>) events).addListener(listener);
	}

	@Override
	public void remove(int from, int to) {
		((ObservableList<Evento>) events).remove(from, to);
	}

	@Override
	public boolean removeAll(Evento... events) {
		return ((ObservableList<Evento>) this.events).removeAll(events);
	}

	@Override
	public void removeListener(ListChangeListener<? super Evento> listener) {
		((ObservableList<Evento>) events).removeListener(listener);
	}

	@Override
	public boolean retainAll(Evento... events) {
		return ((ObservableList<Evento>) this.events).retainAll(events);
	}

	@Override
	public boolean setAll(Evento... events) {
		return ((ObservableList<Evento>) this.events).setAll(events);
	}

	@Override
	public boolean setAll(Collection<? extends Evento> events) {
		return ((ObservableList<Evento>) this.events).setAll(events);
	}

	@Override
	public ObservableList<Evento> data() {
		return events;
	}

}
