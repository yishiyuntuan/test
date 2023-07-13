package async.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.HashMap;

@Configuration
public class Config {
    @Bean
    public HttpService demoApi() {


        WebClient client = WebClient
                .builder()
                .defaultHeaders(e->{
                    e.add("test","123");
                })
                .baseUrl("http://localhost:3000").build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        return factory.createClient(HttpService.class);
    }
}
