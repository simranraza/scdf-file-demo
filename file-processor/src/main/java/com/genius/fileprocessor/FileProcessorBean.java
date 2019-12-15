package com.genius.fileprocessor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;

import org.springframework.beans.factory.annotation.Value;

@EnableBinding(Processor.class)
public class FileProcessorBean {
	private static final Logger logger = LogManager.getLogger(FileProcessorBean.class);

	@Value("${local.output.dir}")
	private String localOutputeDir = "C:\\work\\processed-files";

	@Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
	public Object transformMessage(File fileToProcess) {
		List<String> lines = null;
		logger.info("Processing file: " + fileToProcess.getName());
		try {
			lines = enrichFileContents(readFileIntoStringArray(fileToProcess.getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Writting processed file to : " + localOutputeDir);
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(localOutputeDir + "\\" + fileToProcess.getName() + "-enriched.txt"), "utf-8"))) {
			for (String line : lines) {
				writer.write(line);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileToProcess;
	}

	private List<String> readFileIntoStringArray(String inputFilePath) throws IOException {
		List<String> lines = new ArrayList<String>();
		BufferedReader file = new BufferedReader(new FileReader(inputFilePath));
		String line;
		while ((line = file.readLine()) != null) {
			lines.add(line);
		}
		file.close();
		return lines;
	}

	private List<String> enrichFileContents(List<String> lines) {

		return lines.stream().map(s -> {
			return s + "abc" + "\n";
		}).collect(Collectors.toList());
		// return null;
	}
}
