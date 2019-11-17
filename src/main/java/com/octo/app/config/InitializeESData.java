package com.octo.app.config;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class InitializeESData implements AsyncInitializer {
	private final Logger log = LoggerFactory.getLogger(InitializeESData.class);
	
	//private final CelebritySearchRepository celebritySearchRepository;	
	//private final CelebrityRepository celebrityRepository;
	
	public InitializeESData(
			//CelebritySearchRepository celebritySearchRepository, 
			//CelebrityRepository celebritySearchRepository
			) {
		
		//this.celebrityRepository = celebrityRepository;
		//this.celebritySearchRepository = celebritySearchRepository;
		

	}

	/*@Override
	@Async("indexEntities")
    public CompletableFuture<String> initializeEntities() throws InterruptedException {
	  TimeUnit.MINUTES.sleep(2);
      log.debug("index all celebrities");

	  log.info("Getting from DB");

      long totalCount = celebrityRepository.count();
      long indexTotalCount=celebritySearchRepository.count();
      log.info("Total DB celebrities:{}", totalCount);
      log.info("Total Indexed celebrities:{}", indexTotalCount);
      
      if(totalCount != indexTotalCount) {
    	  celebritySearchRepository.deleteAll();
      } else {
    	  return CompletableFuture.completedFuture("Success");
      }
        	    	  
	  IntStream.rangeClosed(0, ((int)totalCount/100)+1)
               .forEach(i -> {
	                  log.info("Getting data for range : {}, {}", i, 100);
        	    	  Pageable page = PageRequest.of(i, 100);
        	    	  Page<Celebrity> data = celebrityRepository.findAll(page);
                      log.info("Fetched rows " + data.get().collect(Collectors.toList()).size()); 
       	    	      data.stream()
        	              .forEach(c -> celebritySearchRepository.save(c));
               });
      log.info(">>>>>>>>>>>>> Finished Inexing Celebrities");

        
      return CompletableFuture.completedFuture("Success");
    }*/
	

	@Override
	public CompletableFuture<String> initializeEntity() throws InterruptedException {
		// TODO Auto-generated method stub
	      return CompletableFuture.completedFuture("Success");
	}
}