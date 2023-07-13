package async.http;


import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

//@Service
@FeignClient(value = "test",url = "127.0.0.1:3000/api/v1")
//@Headers({"X-Request-Id=122333444"})
@RequestMapping(headers = {"X-Request-Id=122333444"})
public interface OpenFeign {
    @GetMapping(value = "article/list")
    String list2(@RequestParam int cid, @RequestParam int pageSize, @RequestParam int pageNum, @RequestHeader String test);


}
