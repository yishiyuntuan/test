package async;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 主程序类;主配置类
 */

@SpringBootApplication
@MapperScan("async.generator.mapper")
@EnableFeignClients(basePackages = {"async.http"})
public class MainApplication {


    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
