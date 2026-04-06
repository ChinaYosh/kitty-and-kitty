package com.animals;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@SpringBootApplication
@MapperScan("com.animals.mapper")
public class ServerApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(ServerApplication.class, args);
    }

}
