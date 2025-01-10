package be.gert.trainapp.sm._localhost;

import static java.lang.ThreadLocal.withInitial;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.VirtualThreadTaskExecutor;

import lombok.Setter;

@Configuration
@Profile("localhost")
public class LocalhostAsyncConfig {
	@Setter
	private static ThreadLocal<Boolean> syncExecution = withInitial(() -> false);

	public static void useSyncTaskExecutor(Runnable runnable) {
		try {
			syncExecution.set(true);
			runnable.run();
		} finally {
			syncExecution.set(false);
		}
	}

	@Bean
	public TaskExecutor taskExecutor() {
		TaskExecutor virtualThreadExecutor = new VirtualThreadTaskExecutor("virtual-thread-executor");
		return task -> {
			if (syncExecution.get()) {
				task.run();
			} else {
				virtualThreadExecutor.execute(task);
			}
		};
	}
}
