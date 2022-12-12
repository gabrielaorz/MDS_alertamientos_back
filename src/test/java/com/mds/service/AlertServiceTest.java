package com.mds.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mds.model.Alert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class AlertServiceTest {
	@Autowired AlertService alertService;
	private DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

	@Test
	void insertAndFindById() throws ParseException {
		Alert a = newAlert();
		String empleado = a.getEmpleado();

		a = alertService.insert(a);
		
		assertNotNull(a);
		assertNotNull(a.getId());

		Optional<Alert> o = alertService.findById(a.getId());

		assertTrue(o.isPresent());

		Alert e = o.get();
		assertEquals(a.getId(),e.getId());
		assertEquals(a.getEmpleado(),e.getEmpleado());
		assertEquals(a.getArea(),e.getArea());
		assertEquals(a.getJustificacion(),e.getJustificacion());
		assertEquals(a.getDispositivo(),e.getDispositivo());
		assertEquals(a.getEstatus(),e.getEstatus());
		assertEquals(a.getHost(),e.getHost());
		assertEquals(a.getSensor(),e.getSensor());
		assertEquals(a.getVigencia(),e.getVigencia());
		
		alertService.deleteById(empleado);
	}

	@Test
	void findAll() {
		Iterable<Alert> i = alertService.findAll();
		List<Alert> all = new ArrayList<>();
		i.forEach(all::add);
		log.info("alerts:{}",all);
		assertNotNull(all);
		assertFalse(all.isEmpty());
	}
	@Test
	void update() throws ParseException {
		Alert a = newAlert();
		String empleado = a.getEmpleado();

		a = alertService.insert(a);

		assertNotNull(a);
		assertNotNull(a.getId());

		Optional<Alert> o = alertService.findById(a.getId());

		Alert e = o.get();
		e.setEmpleado(empleado+"2");
		e.setHost("Host2");
		e.setJustificacion("Justificacion2");
		e.setSensor("Sensor2");
		e.setVigencia("24/11/22 00:00:00");
		e.setEstatus(2);
		e.setArea("Area2");
		e.setDispositivo("Dispositivo2");

		alertService.update(e);
		
		o = alertService.findById(a.getId());
		assertTrue(o.isPresent());
		a = o.get();
		
		assertEquals(a.getId(),e.getId());
		assertEquals(a.getEmpleado(),e.getEmpleado());
		assertEquals(a.getArea(),e.getArea());
		assertEquals(a.getJustificacion(),e.getJustificacion());
		assertEquals(a.getDispositivo(),e.getDispositivo());
		assertEquals(a.getEstatus(),e.getEstatus());
		assertEquals(a.getHost(),e.getHost());
		assertEquals(a.getSensor(),e.getSensor());
		assertEquals(a.getVigencia(),e.getVigencia());
		
		alertService.deleteById(empleado);
	}
	@Test
	void deleteById() throws ParseException {
		Alert a = newAlert();

		a = alertService.insert(a);

		assertNotNull(a);
		assertNotNull(a.getId());

		alertService.deleteById(a.getId());

		Optional<Alert> o = alertService.findById(a.getId());

		assertTrue(o.isEmpty());
	}
	private Alert newAlert() {
		String empleado = LocalDateTime.now().format(pattern);
		Alert a = new Alert();
		a.setEmpleado(empleado);
		a.setHost("Host");
		a.setJustificacion("Justificacion");
		a.setSensor("Sensor");
		a.setMotivo("Motivo");
		a.setVigencia("24/11/22 00:00:00");
		a.setEstatus(1);
		a.setArea("Area");
		a.setDispositivo("Dispositivo");
		a.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
		return a;
	}
}
