package async.http;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@HttpExchange("api/v1")
public interface HttpService {
    @GetExchange("article/list")
    String list(@RequestParam int cid,@RequestParam int pageSize,@RequestParam int pageNum);




}
