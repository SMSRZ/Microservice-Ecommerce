package com.smsrz.apigateway;


import jakarta.annotation.PostConstruct;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springdoc.core.utils.Constants.DEFAULT_API_DOCS_URL;

@Configuration
public class SwaggerConfig {

    private final RouteDefinitionLocator locator;
    private final SwaggerUiConfigProperties properties;

    public SwaggerConfig(RouteDefinitionLocator locator, SwaggerUiConfigProperties properties) {
        this.locator = locator;
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();
        definitions.stream()
                .filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
                .forEach(routeDefinition -> {
                    String name = routeDefinition.getId().replaceAll("-service", "");
                    AbstractSwaggerUiConfigProperties.SwaggerUrl url = new AbstractSwaggerUiConfigProperties.SwaggerUrl(
                            name,DEFAULT_API_DOCS_URL+"/"+name,null
                    );
                    urls.add(url);
                });
        properties.setUrls(urls);
    }
}
