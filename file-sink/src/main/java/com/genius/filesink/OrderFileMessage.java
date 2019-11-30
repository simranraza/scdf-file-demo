package com.genius.filesink;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderFileMessage implements Serializable {
	
	@JsonProperty("orderCsvData")
	private List<String> orderCsvData = null;
	@JsonProperty("orderFile")
	private File orderFile = null;
	
	public OrderFileMessage() {
		
	}
	
	public OrderFileMessage(List<String> orderCsvData, File orderFile) {
		super();
		this.orderCsvData = orderCsvData;
		this.orderFile = orderFile;
	}

	@JsonProperty("orderCsvData")
	public List<String> getOrderCsvData() {
		return orderCsvData;
	}

	@JsonProperty("orderCsvData")
	public void setOrderCsvData(List<String> orderCsvData) {
		this.orderCsvData = orderCsvData;
	}

	@JsonProperty("orderFile")
	public File getOrderFile() {
		return orderFile;
	}

	@JsonProperty("orderFile")
	public void setOrderFile(File orderFile) {
		this.orderFile = orderFile;
	}

	
}
