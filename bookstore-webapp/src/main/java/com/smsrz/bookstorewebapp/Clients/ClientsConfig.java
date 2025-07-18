package com.smsrz.bookstorewebapp.Clients;

import com.smsrz.bookstorewebapp.ApplicationProperties;
import com.smsrz.bookstorewebapp.Clients.Catalog.CatalogServiceClient;
import com.smsrz.bookstorewebapp.Clients.Orders.OrderServiceClient;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class ClientsConfig {
    private final ApplicationProperties applicationProperties;

     ClientsConfig(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    RestClientCustomizer restClientCustomizer() {
         var requestFactory = ClientHttpRequestFactoryBuilder.simple()
                 .withCustomizer(c -> {
                     c.setConnectTimeout(Duration.ofSeconds(5));
                     c.setReadTimeout(Duration.ofSeconds(5));
                 })
                 .build();
         return restClientBuilder ->
                 restClientBuilder.baseUrl(applicationProperties.apiGatewayUrl()).requestFactory(requestFactory);
    }

    @Bean
    CatalogServiceClient  catalogServiceClient(RestClient.Builder restClientBuilder) {
         RestClient restClient = restClientBuilder.build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(CatalogServiceClient.class);
    }

    @Bean
    OrderServiceClient orderServiceClient(RestClient.Builder restClientBuilder) {
         RestClient restClient = restClientBuilder.build();
         HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
         return factory.createClient(OrderServiceClient.class);
    }
}
