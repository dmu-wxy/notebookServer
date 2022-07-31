package org.meteor.notebookserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("org.meteor.notebookserver.mapper")
@SpringBootApplication
public class NotebookServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotebookServerApplication.class, args);
    }

}
