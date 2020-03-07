package com.banreservas.monitoreo.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserRepositoryStub implements UserRepository {

	private ObservableList<String> users = FXCollections.observableArrayList();
	private Long idIndex = 1L;

	{
		users.add("Anubis");
		users.add("Francis");
		users.add("Guillermo");
		users.add("Jossie");

	}

	@Override
	public boolean add(String username) {
		idIndex++;
		return users.add(username);
	}

	@Override
	public ObservableList<String> data() {
		return users;
	}

}
