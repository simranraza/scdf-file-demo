package com.genius.fileprocessor;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class OrderFileMessage implements Serializable {
	private List<String> orderCsvData = null;
	private File orderFile = null;
	
	public OrderFileMessage(List<String> orderCsvData, File orderFile) {
		super();
		this.orderCsvData = orderCsvData;
		this.orderFile = orderFile;
	}

	public List<String> getOrderCsvData() {
		return orderCsvData;
	}

	public void setOrderCsvData(List<String> orderCsvData) {
		this.orderCsvData = orderCsvData;
	}

	public File getOrderFile() {
		return orderFile;
	}

	public void setOrderFile(File orderFile) {
		this.orderFile = orderFile;
	}

	
}
