package org.meteor.notebookserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//@MapperScan("org.meteor.notebookserver.mapper")
@SpringBootApplication
@EntityScan("org.meteor.notebookserver.entity")
public class NotebookServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotebookServerApplication.class, args);
    }

}
