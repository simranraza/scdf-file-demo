package com.genius.filesource;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import org.springframework.messaging.Message;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@EnableBinding(Source.class)
public class SourceBean {
	//@Value("${local.source.dir}")
    private String localSourceDir="C:\\work\\sample";
	
    private static final Logger logger = LogManager.getLogger(SourceBean.class);

    private static final String MSG = "%s received. Content: %s";
//	@Bean
//    public MessageChannel fileInputChannel() {
//        return new DirectChannel();
//    }
	@Bean
	@InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedDelay = "1000", maxMessagesPerPoll = "1"))
	public MessageSource<File> fileReadingMessageSource() throws IOException {
		FileReadingMessageSource source = new FileReadingMessageSource();
		source.setDirectory(new File(localSourceDir));
		
		CompositeFileListFilter filter = new CompositeFileListFilter<>(
                Arrays.asList(new AcceptOnceFileListFilter<>(),
                		new SimplePatternFileListFilter("*.txt"))
        );
        //source.setFilter(new SimplePatternFileListFilter("*.txt"));
		source.setFilter(filter);
//        Message<File> msg = source.receive();
//        
//        File tempFile = msg.getPayload();
//        String fileContent = new String (Files.readAllBytes(Paths.get(tempFile.getPath())));
//        //System.out.println(String.format(MSG, content.getName(), content));
//        System.out.println(String.format("Received File: %s", tempFile.getAbsolutePath()));
//        System.out.println(String.format("File Content: %s", fileContent));
//        System.out.println(" ");
        return source;
	}
}
