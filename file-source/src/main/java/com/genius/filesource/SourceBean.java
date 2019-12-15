package com.genius.filesource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;

@EnableBinding(Source.class)
public class SourceBean {
	
	@Value("${local.source.dir}")
    private String localSourceDir="C:\\work\\sample";
    private static final Logger logger = LogManager.getLogger(SourceBean.class);

	@Bean
	@InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedDelay = "1000", maxMessagesPerPoll = "1"))
	public MessageSource<File> fileReadingMessageSource() throws IOException {
		logger.info("Polling file from directory: " + localSourceDir);
		FileReadingMessageSource source = new FileReadingMessageSource();
		source.setDirectory(new File(localSourceDir));
		
		CompositeFileListFilter filter = new CompositeFileListFilter<>(
                Arrays.asList(new AcceptOnceFileListFilter<>(),
                		new SimplePatternFileListFilter("*.txt"))
        );

		source.setFilter(filter);
        return source;
	}
}
