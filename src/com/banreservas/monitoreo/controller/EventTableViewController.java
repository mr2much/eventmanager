package com.banreservas.monitoreo.controller;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.banreservas.monitoreo.model.Evento;
import com.banreservas.monitoreo.repository.EventRepository;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class EventTableViewController implements ObservableList<Evento> {

	private EventRepository eventRepository;

	public void setRepository(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@Override
	public boolean add(Evento event) {
		return eventRepository.add(event);
	}

	@Override
	public void add(int index, Evento event) {
		eventRepository.add(index, event);
	}

	@Override
	public boolean addAll(Collection<? extends Evento> events) {
		return eventRepository.addAll(events);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Evento> events) {
		return eventRepository.addAll(index, events);
	}

	@Override
	public void clear() {
		eventRepository.clear();
	}

	@Override
	public boolean contains(Object o) {
		return eventRepository.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> events) {
		return eventRepository.containsAll(events);
	}

	@Override
	public Evento get(int index) {
		return eventRepository.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return eventRepository.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return eventRepository.isEmpty();
	}

	@Override
	public Iterator<Evento> iterator() {
		return eventRepository.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return eventRepository.lastIndexOf(o);
	}

	@Override
	public ListIterator<Evento> listIterator() {
		return eventRepository.listIterator();
	}

	@Override
	public ListIterator<Evento> listIterator(int index) {
		return eventRepository.listIterator(index);
	}

	@Override
	public boolean remove(Object o) {
		return eventRepository.remove(o);
	}

	@Override
	public Evento remove(int index) {
		return eventRepository.remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> events) {
		return eventRepository.removeAll(events);
	}

	@Override
	public boolean retainAll(Collection<?> events) {
		return eventRepository.retainAll(events);
	}

	@Override
	public Evento set(int index, Evento event) {
		return eventRepository.set(index, event);
	}

	@Override
	public int size() {
		return eventRepository.size();
	}

	@Override
	public List<Evento> subList(int fromIndex, int toIndex) {
		return eventRepository.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return eventRepository.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return eventRepository.toArray(a);
	}

	@Override
	public void addListener(InvalidationListener listener) {
		eventRepository.addListener(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		eventRepository.removeListener(listener);
	}

	@Override
	public boolean addAll(Evento... events) {
		return eventRepository.addAll(events);
	}

	@Override
	public void addListener(ListChangeListener<? super Evento> listener) {
		eventRepository.addListener(listener);
	}

	@Override
	public void remove(int from, int to) {
		eventRepository.remove(from, to);
	}

	@Override
	public boolean removeAll(Evento... events) {
		return eventRepository.removeAll(events);
	}

	@Override
	public void removeListener(ListChangeListener<? super Evento> listener) {
		eventRepository.removeListener(listener);
	}

	@Override
	public boolean retainAll(Evento... events) {
		return eventRepository.retainAll(events);
	}

	@Override
	public boolean setAll(Evento... events) {
		return eventRepository.setAll(events);
	}

	@Override
	public boolean setAll(Collection<? extends Evento> events) {
		return eventRepository.setAll(events);
	}

	public ObservableList<Evento> data() {
		return eventRepository.data();
	}
}
