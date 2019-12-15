package be.fusulier.springregistrar;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@SpecialClassMarker(name = "specialClass")
@Slf4j
public class SpecialClass implements Serializable {

    void hello() {
        log.info("Hello! I'm special");
    }
}
