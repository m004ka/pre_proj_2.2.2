package org.example.springbootproj2.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

import java.util.Set;

@Getter
@Setter
@Component
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "car")
public class CarProperties {
    private int max;
    private Set<String> enabledFields;
}
