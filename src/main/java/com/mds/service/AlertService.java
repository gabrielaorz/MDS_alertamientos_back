package com.mds.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import com.mds.model.Alert;
import com.mds.repository.AlertRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AlertService {

	@Autowired private AlertRepository alertRepository;
	@Autowired private ElasticsearchOperations elasticsearchTemplate;
	
	
	public Alert update(Alert alert) {
		log.info("Index updated:{}",alert);
		Optional<Alert> o = alertRepository.findById(alert.getId());
		if (!o.isPresent()) {
			throw new RuntimeException("Cannot update alert, invalid id:"+alert.getId());
		}
		Alert entity = o.get();
		entity.setEmpleado(alert.getEmpleado());
		entity.setDispositivo(alert.getDispositivo());
		entity.setJustificacion(alert.getJustificacion());
		entity.setHost(alert.getHost());
		entity.setSensor(alert.getSensor());
		entity.setVigencia(alert.getVigencia());
		entity.setArea(alert.getArea());
		entity.setMotivo(alert.getMotivo());
		entity.setEstatus(alert.getEstatus());
		return alertRepository.save(entity);
	}
	public Alert activate(String idAlert, String empleado) throws ParseException {

		DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE;
		SimpleDateFormat formato = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.SSSSSS");

		log.info("Activate:{}",idAlert);
		Optional<Alert> o = alertRepository.findById(idAlert);
		if (!o.isPresent()) {
			throw new RuntimeException("Cannot update alert, invalid id:"+idAlert);
		}
		Alert entity = o.get();
		entity.setEmpleado(empleado);
		entity.setTimestamp(LocalDateTime.now().format(FORMATTER));
		entity.setArea("Mesa de Servicio");
		entity.setMotivo("Reactivacion");
		entity.setEstatus(0);
		return alertRepository.save(entity);
	}
	public List<Alert> findAllInactive() {
		//return alertRepository.findAllInactiveSorted();
		Query searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.matchQuery("estatus",0))
				.withSort(SortBuilders.fieldSort("@timestamp")
						.order(SortOrder.DESC))
				.build();
		SearchHits<Alert> alerts =
				elasticsearchTemplate.search(searchQuery, Alert.class);

		return alerts.getSearchHits().stream().map(a -> a.getContent()).collect(Collectors.toList());

	}

	public List<Alert> findAllActive() {
		//return alertRepository.findAllActiveSorted();
		Query searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.matchQuery("estatus",1))
				.withSort(SortBuilders.fieldSort("@timestamp")
						.order(SortOrder.DESC))
				.build();
		SearchHits<Alert> alerts =
				elasticsearchTemplate.search(searchQuery, Alert.class);

		return alerts.getSearchHits().stream().map(a -> a.getContent()).collect(Collectors.toList());
	}
	public List<Alert> findAll() {
		/*List<Alert> alerts = new ArrayList<>();
		Iterable<Alert> i = alertRepository.findAll();
		i.forEach(alerts::add);
		return alerts;*/

		Query searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.matchAllQuery())
				.withSort(SortBuilders.fieldSort("@timestamp")
						.order(SortOrder.DESC))
				.build();
		SearchHits<Alert> alerts =
				elasticsearchTemplate.search(searchQuery, Alert.class);

		return alerts.getSearchHits().stream().map(a -> a.getContent()).collect(Collectors.toList());
	}
	public Optional<Alert> findById(String id) {
		return alertRepository.findById(id);
	}
	public Alert insert(Alert alert) throws ParseException {
		DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ss.SSSSSS");
		DateTimeFormatter FORMATTER2 = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
		if (alert.getEstatus() == null) {
			alert.setEstatus(1);
		}
		if (alert.getArea() == null) {
			alert.setArea("Mesa de servicio");
		}
		if (alert.getVigenciaInicio() == null) {
			alert.setVigenciaInicio(LocalDateTime.now().format(FORMATTER2));
		}
		if (alert.getTimestamp() == null) {
			alert.setTimestamp(LocalDateTime.now().format(FORMATTER));
		}
		log.info("Index insert:{}",alert);
		return alertRepository.save(alert);
	}
	public void deleteById(String id) {
		alertRepository.deleteById(id);
	}
	public Alert update_(Alert alert) {
		Query searchQuery = new NativeSearchQueryBuilder()
				  .withQuery(QueryBuilders.matchQuery("id", alert.getId()))
				  .build();
		SearchHits<Alert> articles = 
				   elasticsearchTemplate.search(searchQuery, Alert.class, IndexCoordinates.of("aiops_excepciones_gaby"));
		Alert entity = articles.getSearchHit(0).getContent();
		/*entity.setEmpleado(alert.getEmpleado());
		entity.setDispositivo(alert.getDispositivo());
		entity.setJustificacion(alert.getJustificacion());
		entity.setHost(alert.getHost());
		entity.setSensor(alert.getSensor());
		entity.setVigencia(alert.getVigencia());
		entity.setArea(alert.getArea());
		entity.setMotivo(alert.getMotivo());
		entity.setEstatus(alert.getEstatus());*/
		new ModelMapper().map(alert, entity);
		alertRepository.save(entity);
		return entity;
	}
}
