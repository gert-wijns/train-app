package be.gert.trainapp.sm._shared.config;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;

import be.gert.trainapp.sm.ModuleTest;
import lombok.extern.slf4j.Slf4j;

/**
 * Module tests load all beans of all domains to avoid spring context reloads.
 * During Module tests we do expect the modules to be tested in isolation.
 * Each module needs to provide testdoubles for beans needed from other modules.
 */
@Slf4j
public class ModuleTestAutowireCandidateResolverConfigurer implements BeanFactoryPostProcessor {
    private static final String DOMAIN_MODULES_ROOT = ModuleTest.class.getPackageName();
    private static final int DOMAIN_MODULE_PART = DOMAIN_MODULES_ROOT.split("\\.").length;
    private static final String API_MODULE_ROOT = "be.gert.trainapp.api";
    private static final int API_MODULE_PART = API_MODULE_ROOT.split("\\.").length;
    private static final Set<String> BEAN_NAME_WHITELIST = Set.of("userDetailsServiceFake");

    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException {
        DefaultListableBeanFactory bf = (DefaultListableBeanFactory) beanFactory;
        bf.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver() {
            @Override
            public boolean isAutowireCandidate(BeanDefinitionHolder bdHolder, DependencyDescriptor descriptor) {
                if (bdHolder.getBeanDefinition().isAutowireCandidate()
                        && !BEAN_NAME_WHITELIST.contains(bdHolder.getBeanName())
                        && isCandidateModuleDifferentFromTargetModule(bdHolder, descriptor)) {
                    log.info("Rejected candidate [{}, {}] because it is not in the same domain as [{}]",
                            bdHolder.getBeanName(),
                            bdHolder.getBeanDefinition().getBeanClassName(),
                            getDeclaringClass(descriptor).orElse(null));
                    return false;
                }
                return super.isAutowireCandidate(bdHolder, descriptor);
            }
        });
    }

    private boolean isCandidateModuleDifferentFromTargetModule(BeanDefinitionHolder bdHolder,
                                                               DependencyDescriptor descriptor) {
        String candidateModule = getCandidateModule(bdHolder);
        var targetModule = getTargetModule(descriptor);
        return candidateModule != null && !candidateModule.equals(targetModule.orElse(null));
    }

    private Optional<String> getTargetModule(DependencyDescriptor descriptor) {
        return getDeclaringClass(descriptor)
                .map(Class::getPackageName)
                .filter(packageName -> packageName.startsWith(DOMAIN_MODULES_ROOT))
                .map(packageName -> packageName.split("\\.")[DOMAIN_MODULE_PART]);
    }

    private Optional<Class<?>> getDeclaringClass(DependencyDescriptor descriptor) {
        if (descriptor.getField() != null) {
            return Optional.of(descriptor.getField().getDeclaringClass());
        } else if (descriptor.getMethodParameter() != null) {
            return Optional.of(descriptor.getMethodParameter().getDeclaringClass());
        } else {
            return Optional.empty();
        }
    }

    private String getCandidateModule(BeanDefinitionHolder bdHolder) {
        String candidateClassName = bdHolder.getBeanDefinition().getBeanClassName();
        if (candidateClassName == null) {
            return null;
        } else if (candidateClassName.startsWith(DOMAIN_MODULES_ROOT)) {
            return candidateClassName.split("\\.")[DOMAIN_MODULE_PART];
        } else if (candidateClassName.startsWith(API_MODULE_ROOT)) {
            return candidateClassName.split("\\.")[API_MODULE_PART];
        }
        return null;
    }
}
