package com.genius.filesink;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.annotation.StreamMessageConverter;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

@EnableBinding(Sink.class)
public class OutputBean {
	private static final Logger logger = LogManager.getLogger(OutputBean.class);

	@StreamListener(Sink.INPUT)
	public void output(OrderFileMessage  incomingMessage) throws IOException {
	
		OrderFileMessage orderFileMessage =  (OrderFileMessage) incomingMessage;;
		File outFile = orderFileMessage.getOrderFile();
		logger.info("File Recived in sink " + outFile.getName());
		String outFileName = outFile.getName() + "-new" + "." + FilenameUtils.getExtension(outFile.getName());
		String outDir = "C:\\work\\sink-output";
		File dest = new File(outDir + "\\" + outFileName);
		FileUtils.copyFile(outFile, dest);
		//Remove File from source directory 
		FileUtils.forceDelete(outFile);
		logger.info("File moved successfully");
	}
	
	@Bean
	@StreamMessageConverter
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
	    return new Jackson2JsonMessageConverter();
	}
}
