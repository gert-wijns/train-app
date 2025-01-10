package be.gert.trainapp.sm._shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

/**
 * Simplify tests which execute code in @Async by running it Sync
 */
public class SyncTaskExecutorTestConfig {
	@Bean
	public TaskExecutor taskExecutor() {
		return new SyncTaskExecutor();
	}
}
