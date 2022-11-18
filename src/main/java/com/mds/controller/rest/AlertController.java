package com.mds.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mds.model.Alert;
import com.mds.service.AlertService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/alert")
public class AlertController {
	
	@Autowired private AlertService elasticService;

	@GetMapping("/findAll")
	public List<Alert> findAll() {
		try {
			log.info("Elastic findAll");
			return elasticService.findAll();
		} catch (Exception e) {
			log.error("Internal Error",e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/findAllInactive")
	public List<Alert> findAllInactive() {
		try {
			log.info("Elastic findAllInactive");
			return elasticService.findAllInactive();
		} catch (Exception e) {
			log.error("Internal Error",e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/find/{id}")
	public Alert findById(@PathVariable(value = "id") String id) {
		log.info("Alert findbyId:{}",id);
		return elasticService.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	@PostMapping("/update")
	public ResponseEntity<?> update(@RequestBody Alert alert) {
		try {
			log.info("Alert update:{}", alert);
			elasticService.update(alert);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Internal Error",e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping("/insert")
	public ResponseEntity<?> insert(@RequestBody Alert alert) {
		try {
			log.info("Alert insert:{}", alert);
			Alert a = elasticService.insert(alert);
			return ResponseEntity.status(HttpStatus.CREATED).body(a);
		} catch (Exception e) {
			log.error("Internal Error",e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping("/activate")
	public ResponseEntity<?> activate(@RequestParam("id") String idAlert, @RequestParam("empleado") String empeado) {
		try {
			log.info("Alert activate:{}", idAlert);
			Alert a = elasticService.activate(idAlert, empeado);
			return ResponseEntity.ok(a);
		} catch (Exception e) {
			log.error("Internal Error",e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
