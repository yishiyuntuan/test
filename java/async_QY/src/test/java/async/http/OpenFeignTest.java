package async.http;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenFeignTest {
    @Resource
    OpenFeign openFeign;

    @Test
    void list2() {
        String s = openFeign.list2(1, 22, 1,"11111111");
        System.out.println(s);


    }
}