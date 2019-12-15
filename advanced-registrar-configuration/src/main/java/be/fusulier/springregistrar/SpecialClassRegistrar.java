package be.fusulier.springregistrar;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@AllArgsConstructor
public class SpecialClassRegistrar implements ImportBeanDefinitionRegistrar {

    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {

        if (!annotationMetadata.isAnnotated(EnableSpecialClassesScanning.class.getName())) {
            log.info("The annotation {} was not found, the special classes will not be scanned from the classpath.", EnableSpecialClassesScanning.class.getName());
            return;
        }

        log.info("The annotation {} was found, the special classes will be scanned from the classpath.", EnableSpecialClassesScanning.class.getName());
        Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(EnableSpecialClassesScanning.class.getName());
        registerBeanDefinitions(registry, attributes);
    }

    private void registerBeanDefinitions(BeanDefinitionRegistry registry, Map<String, Object> attributes) {

        String basePackage = (String) attributes.get("basePackage");
        if (basePackage == null) {
            throw new IllegalArgumentException(String.format("The attribute basePackage of %s is mandatory", EnableSpecialClassesScanning.class.getName()));
        }

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.setEnvironment(environment);
        scanner.addIncludeFilter(new AnnotationTypeFilter(SpecialClassMarker.class));

        log.info("Scanning the package {} for special classes", basePackage);
        Iterator<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage).iterator();

        int count = 0;
        while (candidateComponents.hasNext()) {
            BeanDefinition beanDefinition = candidateComponents.next();
            log.info("Found bean definition {}", beanDefinition.getBeanClassName());
            beanDefinition.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE);

            Class<?> theClass = getBeanClass(beanDefinition);

            if (!Serializable.class.isAssignableFrom(theClass)) {
                throw new RuntimeException(String.format("The special class %s must implement %s", theClass.getName(), Serializable.class.getName()));
            }

            SpecialClassMarker annotation = AnnotationUtils.findAnnotation(theClass, SpecialClassMarker.class);
            if (annotation == null) {
                throw new RuntimeException(String.format("The special class %s must be annotated with %s", theClass.getName(), SpecialClassMarker.class.getName()));
            }
            if (annotation.name() == null) {
                throw new RuntimeException(String.format("The attribute name of %s is mandatory", SpecialClassMarker.class.getName()));
            }
            log.info("Registering bean definition {} with class {}", annotation.name(), beanDefinition.getBeanClassName());
            registry.registerBeanDefinition(annotation.name(), beanDefinition);
            count++;
        }

        if (count == 0) {
            String message = "No special class was registered";
            log.error(message);
            throw new RuntimeException(message);
        }
    }

    private Class<?> getBeanClass(BeanDefinition beanDefinition) {
        Class<?> theClass;
        try {
            theClass = Class.forName(beanDefinition.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return theClass;
    }
}