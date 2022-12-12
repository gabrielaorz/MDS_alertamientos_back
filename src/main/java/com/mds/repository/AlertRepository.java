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

    @Query("{\n" +
            "  \"bool\":{\n" +
            "    \"must\":[{\n" +
            "        \"match_all\": {}\n" +
            "    }]\n" +
            "  }\n" +
            "}, \"sort\" : [\n" +
            "    {\n" +
            "      \"@timestamp.keyword\" : {\n" +
            "        \"order\" : \"desc\"\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n")
    List<Alert> findAll();

    @Query("{\n" +
            "  \"bool\":{\n" +
            "    \"must\":[{\n" +
            "        \"match\": { \"estatus\" : 0 }\n" +
            "    }]\n" +
            "  }\n" +
            "}, \"sort\" : [\n" +
            "    {\n" +
            "      \"@timestamp.keyword\" : {\n" +
            "        \"order\" : \"desc\"\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n")
    List<Alert> findAllInactiveSorted();

    @Query("{\n" +
            "  \"bool\":{\n" +
            "    \"must\":[{\n" +
            "        \"match\": { \"estatus\" : 1 }\n" +
            "    }]\n" +
            "  }\n" +
            "}, \"sort\" : [\n" +
            "    {\n" +
            "      \"@timestamp.keyword\" : {\n" +
            "        \"order\" : \"desc\"\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n")
    List<Alert> findAllActiveSorted();
}