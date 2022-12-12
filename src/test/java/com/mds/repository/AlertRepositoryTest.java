package com.mds.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.mds.model.Alert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class AlertRepositoryTest {
	@Autowired AlertRepository alertRepository;

	@Test
	void save() {
		Alert a = new Alert();
		a.setId("1");
		a.setEmpleado("12345");
		a.setHost("Host1");
		a = alertRepository.save(a);
		assertNotNull(a);
		assertNotNull(a.getId());
	}
	
	@Test
	void saveAll() {
		List<Alert> alerts = new ArrayList<>();
		Alert a = new Alert();
		a.setId("1");
		a.setEmpleado("12345");
		a.setHost("Host1");
		alerts.add(a);
		Iterable<Alert> i = alertRepository.saveAll(alerts);
		assertNotNull(i);
	}
	
	@Test
	void findByEmpleado() {
		Page<Alert> p
		  = alertRepository.findByEmpleado("12345", PageRequest.of(0, 10));
		assertNotNull(p);
	}
	@Test
	void findAll() {
		Iterable<Alert> i = alertRepository.findAll();
		List<Alert> all = new ArrayList<>();
		i.forEach(all::add);
		log.info("alerts:{}",all);
		assertNotNull(all);
	}
	@Test
	void deleteById() {
		alertRepository.deleteById("1");
	}

}
