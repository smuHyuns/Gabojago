package Gabojago.gabojago_be.config;

import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Server httpsServer = new Server()
                .url("https://api.gabojago.store")
                .description("Gabojago HTTPS Server");

        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .servers(List.of(httpsServer));
    }

    private Info apiInfo() {
        return new Info()
                .title("Gabojago 가보자고")
                .description("가보자고의 REST API 문서")
                .version("1.0.0");
    }
}
