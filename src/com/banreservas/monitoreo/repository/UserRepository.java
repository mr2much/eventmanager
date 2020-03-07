package com.banreservas.monitoreo.repository;

import javafx.collections.ObservableList;

public interface UserRepository {
	boolean add(String username);
	ObservableList<String> data();
}
