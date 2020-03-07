package com.banreservas.monitoreo.repository;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.banreservas.monitoreo.model.Evento;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public interface EventRepository {
	boolean add(Evento event);

	void add(int index, Evento event);

	boolean addAll(Collection<? extends Evento> events);

	boolean addAll(int index, Collection<? extends Evento> events);

	void clear();

	boolean contains(Object o);

	boolean containsAll(Collection<?> events);

	Evento get(int index);

	int indexOf(Object o);

	boolean isEmpty();

	Iterator<Evento> iterator();

	int lastIndexOf(Object o);

	ListIterator<Evento> listIterator();

	ListIterator<Evento> listIterator(int index);

	boolean remove(Object o);

	Evento remove(int index);

	boolean removeAll(Collection<?> events);

	boolean retainAll(Collection<?> events);

	Evento set(int index, Evento event);

	int size();

	List<Evento> subList(int fromIndex, int toIndex);

	Object[] toArray();

	<T> T[] toArray(T[] a);

	void addListener(InvalidationListener listener);

	void removeListener(InvalidationListener listener);

	boolean addAll(Evento... events);

	void addListener(ListChangeListener<? super Evento> listener);

	void remove(int from, int to);

	boolean removeAll(Evento... events);

	void removeListener(ListChangeListener<? super Evento> listener);

	boolean retainAll(Evento... events);

	boolean setAll(Evento... events);

	boolean setAll(Collection<? extends Evento> events);

	ObservableList<Evento> data();
}