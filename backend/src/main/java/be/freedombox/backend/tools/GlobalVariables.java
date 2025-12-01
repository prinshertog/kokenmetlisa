package be.freedombox.backend.tools;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "global-variables")
@Setter
@Getter
public class GlobalVariables {
    private String baseImageURL;
}
