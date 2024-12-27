package com.aps.echo.config;

import com.datastax.astra.client.DataAPIClient;
import com.datastax.astra.client.Database;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AstraConfig {

    @Bean
    public DataAPIClient dataAPIClient() {
        return new DataAPIClient("AstraCS:mAGJZZUUxgqPTbRdEXTkNMHQ:5867c0810edacad8436a30c1267ba9c776ef1a0e5ca466b4263a82218c041907");
    }

    @Bean
    public Database database(DataAPIClient dataAPIClient) {
        return dataAPIClient.getDatabase(
                "https://db5f43c6-e398-4194-bb53-3dfeb22275ef-us-east-2.apps.astra.datastax.com",
                 "main"
        );
    }
}

