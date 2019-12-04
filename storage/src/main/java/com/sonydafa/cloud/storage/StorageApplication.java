package com.sonydafa.cloud.storage;

import com.sonydafa.cloud.config.CORSConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.sonydafa.cloud.*")
public class StorageApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }
    //部署成war重写的方法，开发时不用
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(StorageApplication.class);
    }
}
