package be.gert.trainapp.sm;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;

import lombok.extern.slf4j.Slf4j;

/**
 * Module tests load all beans of all domains to avoid spring context reloads.
 * During Module tests we do expect the modules to be tested in isolation.
 * Each module needs to provide testdoubles for beans needed from other modules.
 */
@Slf4j
public class ModuleTestAutowireCandidateResolverConfigurer implements BeanFactoryPostProcessor {
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException {
        DefaultListableBeanFactory bf = (DefaultListableBeanFactory) beanFactory;
        bf.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver() {
            private final String modulesRoot = ModuleTest.class.getPackageName();
            private final int domainPart = modulesRoot.split("\\.").length;

            @Override
            public boolean isAutowireCandidate(BeanDefinitionHolder bdHolder, DependencyDescriptor descriptor) {
                if (bdHolder.getBeanDefinition().isAutowireCandidate()) {
                    String candidateModule = getCandidateModule(bdHolder);
                    String targetModule = getTargetModule(descriptor);
                    if (candidateModule != null && !candidateModule.equals(targetModule)) {
                        log.info("Rejected candidate [{}, {}] because it is not in the same domain as [{}]",
                                bdHolder.getBeanName(),
                                bdHolder.getBeanDefinition().getBeanClassName(),
                                getDeclaringClassName(descriptor));
                        return false;
                    }
                }
                return super.isAutowireCandidate(bdHolder, descriptor);
            }

            private String getTargetModule(DependencyDescriptor descriptor) {
                String targetPackageName = getDeclaringClassName(descriptor);
                if (targetPackageName != null && targetPackageName.startsWith(modulesRoot)) {
                    return targetPackageName.split("\\.")[domainPart];
                }
                return null;
            }

            private String getDeclaringClassName(DependencyDescriptor descriptor) {
                if (descriptor.getField() != null) {
                    return descriptor.getField().getDeclaringClass().getName();
                } else if (descriptor.getMethodParameter() != null) {
                    return descriptor.getMethodParameter().getDeclaringClass().getName();
                } else {
                    return null;
                }
            }

            private String getCandidateModule(BeanDefinitionHolder bdHolder) {
                String candidateClassName = bdHolder.getBeanDefinition().getBeanClassName();
                if (candidateClassName == null) {
                    return null;
                } else if (candidateClassName.startsWith(modulesRoot)) {
                    return candidateClassName.split("\\.")[domainPart];
                } else if (candidateClassName.startsWith("be.gert.trainapp.api")) {
                    return candidateClassName.split("\\.")[4];
                }
                return null;
            }
        });
    }
}
