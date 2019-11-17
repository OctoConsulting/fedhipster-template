package com.octo.app.config;

import java.util.concurrent.CompletableFuture;

public interface AsyncInitializer {
	public CompletableFuture<String> initializeEntity() throws InterruptedException;
}
