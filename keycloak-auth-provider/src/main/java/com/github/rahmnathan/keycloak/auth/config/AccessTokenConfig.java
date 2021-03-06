package com.github.rahmnathan.keycloak.auth.config;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.HttpMethod;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class AccessTokenConfig {
    private final Logger logger = Logger.getLogger(AccessTokenConfig.class.getName());
    private final CamelContext camelContext;

    @Autowired
    public AccessTokenConfig(CamelContext camelContext){
        this.camelContext = camelContext;
    }

    @PostConstruct
    public void initialize(){
        try {
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    onException(Exception.class)
                            .useExponentialBackOff()
                            .redeliveryDelay(1000)
                            .maximumRedeliveries(5);

                    from("direct:accesstoken")
                            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST))
                            .setHeader(Exchange.CONTENT_TYPE, constant("application/x-www-form-urlencoded"))
                            .to("https4://localmovies-cloud.hopto.org/auth/realms/LocalMovies/protocol/openid-connect/token");
                }
            });
        } catch (Exception e){
            logger.log(Level.SEVERE, "Failure adding routes to Camel context", e);
        }
    }
}
