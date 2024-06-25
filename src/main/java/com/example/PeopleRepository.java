package com.example;


import jakarta.annotation.PostConstruct;
import localhost._8080.Error;
import localhost._8080.Information;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PeopleRepository {
	private static final Map<Integer, Information> bd = new HashMap<>();
	String str ;
	Error er;
	@PostConstruct
	public void initData() {
		Information exmp = new Information();
		exmp.setName("Ivan");
		exmp.setSurname("Petrov");
		exmp.setCity("Murom");
		exmp.setSnils(12345);
        er = new Error();
       // er.setNameError("Not found");

		bd.put(exmp.getSnils(), exmp);

	}

	public Information findPeople(int snils) {

		return bd.get(snils);

	}

	public Error getError(){
		return er;
	}


}
