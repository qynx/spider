package xyz.zix.spider.repo.ds;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import xyz.zix.spider.cli.RestCli;

@Configuration
public class RestDs {

    @Getter
    private static RestCli restCli;

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create();
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean
    public RestCli restCli(WebClient webClient) {
        return (restCli = new RestCli(webClient));
    }

}
