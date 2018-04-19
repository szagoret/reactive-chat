package com.szagoret.springboot2.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.tuple.Tuple;
import org.springframework.web.server.WebSession;

@Configuration
public class GatewayConfig {

    private static final Logger log = LoggerFactory.getLogger(GatewayConfig.class);

    @Bean
    SaveSessionGatewayFilterFactory saveSessionGatewayFilterFactory(){
        return new SaveSessionGatewayFilterFactory();
    }

    /**
     * Force the current WebSession to get saved
     */
    static class SaveSessionGatewayFilterFactory implements GatewayFilterFactory{
        @Override
        public GatewayFilter apply(Tuple args) {
            return (exchange, chain) -> exchange.getSession()
                    .map(webSession -> {
                        log.debug("Session id: " + webSession.getId());
                        webSession.getAttributes().entrySet()
                                .forEach(entry -> log.debug(entry.getKey() + " => " + entry.getValue()));
                        return webSession;
                    })
                    .map(WebSession::save)
                    .then(chain.filter(exchange));
        }
    }
}
