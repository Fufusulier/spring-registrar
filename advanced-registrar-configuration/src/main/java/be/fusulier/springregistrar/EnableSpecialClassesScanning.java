package be.fusulier.springregistrar;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Import(SpecialClassRegistrar.class)
public @interface EnableSpecialClassesScanning {

    @AliasFor("basePackage")
    String value() default "";

    @AliasFor("value")
    String basePackage() default "";
}
