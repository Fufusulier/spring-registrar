package be.fusulier.springregistrar;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface SpecialClassMarker {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";
}
