package async.http;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HttpServiceTest {

    @Resource
    HttpService httpService;

    @Test
    void list() {
        String list = httpService.list(1, 12, 1);
        System.out.println(list);

    }

}