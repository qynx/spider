package xyz.zix.spider.repo.ds;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.HttpProtocol;
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
    public WebClient web2Client() {
        HttpClient httpClient = HttpClient.create()
                .wiretap(true)
                .protocol(HttpProtocol.H2);
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean
    public RestCli restCli(WebClient webClient) {
        return (restCli = new RestCli(webClient));
    }

    public static void main(String[] args) throws Exception {
        HttpClient httpClient = HttpClient.create()
                .wiretap(true)
                .protocol(HttpProtocol.H2);
         WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
         webClient.get()
                 .uri("https://www.69shu.pro/book/50734/")
                 .header("authority", "www.69shu.pro")
                 .header("host", "www.69shu.pro")
                 .header("method", "GET")
                 .header("path", "/book/50734/")
                 .retrieve()
                 .bodyToMono(String.class)
                 .subscribe(it -> {
                     System.out.println(it);
                 });

         Thread.sleep(2000);
    }

}
