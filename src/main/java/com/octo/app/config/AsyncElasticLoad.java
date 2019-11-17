package com.octo.app.config;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class AsyncElasticLoad {
	  private final AsyncInitializer bean;

	  public AsyncElasticLoad(AsyncInitializer bean) {
	    this.bean = bean;
	  }

	  @PostConstruct
	  public void initialize() throws InterruptedException {
		    bean.initializeEntity();
	  }
}