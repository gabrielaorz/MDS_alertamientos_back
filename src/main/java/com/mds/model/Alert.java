package com.mds.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

import java.util.Date;

@Data
@Document(indexName = "aiops_excepciones")
public class Alert {
	@Id
	private String id;
	@Field(type = FieldType.Text, name = "empleado")
	private String empleado;
	@Field(type = FieldType.Text, name = "area")
	private String area;
	@Field(type = FieldType.Text, name = "justificacion")
	private String justificacion;
	@Field(type = FieldType.Text, name = "motivo")
	private String motivo;
	@Field(type = FieldType.Text, name = "dispositivo")
	private String dispositivo;
	@Field(type = FieldType.Text, name = "estatus")
	private Integer estatus;
	@Field(type = FieldType.Text, name = "host")
	private String host;
	@Field(type = FieldType.Text, name = "sensor")
	private String sensor;
	@Field(type = FieldType.Date, name = "vigencia")
	private String vigencia;
	@Field(type = FieldType.Date, name = "vigencia_inicio")
	private String vigenciaInicio;
	@Field(type = FieldType.Date, name = "@timestamp")
	private String timestamp;
}
