package be.gert.trainapp.sm._shared.testdoubles;

import java.util.Map;

import org.mockito.Mockito;
import org.mockito.exceptions.misusing.NotAMockException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.test.context.event.BeforeTestMethodEvent;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("module-core-test")
public class ModuleTestListener implements ApplicationContextAware {
	private Map<String, Object> testDoubles;

	@BeforeTestMethod
	public void beforeTestMethod(BeforeTestMethodEvent event) {
		log.info("Resetting [{}] @ModuleTestDouble spy beans before [{}#{}].", testDoubles.size(),
				event.getTestContext().getTestClass().getSimpleName(),
				event.getTestContext().getTestMethod().getName());
		testDoubles.forEach((name, bean) -> {
			try {
				Mockito.reset(bean);
			} catch (NotAMockException ex) {
				throw new RuntimeException("TestDouble [beanName: " + name + "] must be a spy" +
						" (see adr-011-test-doubles-as-spy.md for info/howto)", ex);
			}
		});
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		testDoubles = applicationContext.getBeansWithAnnotation(ModuleTestDouble.class);
	}
}
