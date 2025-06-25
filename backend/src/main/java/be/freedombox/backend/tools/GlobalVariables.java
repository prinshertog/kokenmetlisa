package be.freedombox.backend.tools;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "global-variables")
public class GlobalVariables {
    private String baseImageURL;

    public String getBaseImageURL() {
        return this.baseImageURL;
    }

    public void setBaseImageURL(String baseImageURL) {
        this.baseImageURL = baseImageURL;
    }
}
