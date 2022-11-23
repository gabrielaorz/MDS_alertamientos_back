package com.mds.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.mds.model.Alert;

public interface AlertRepository extends ElasticsearchRepository<Alert, String> {

    Page<Alert> findByEmpleado(String empleado, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"host\": \"?0\"}}]}}")
    Page<Alert> findByEmpleadoUsingCustomQuery(String empleado, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"match_all\": {}}]}}")
    List<Alert> findAllInactive();

    @Query("{\"bool\": {\"must\": [{\"match_all\": {}}]}}")
    List<Alert> findAll();
}