package org.example.dc.srv;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Project: Hive_Service
 * Package: org.example.dc.start
 * Class: Application
 * Author: RuiChao Lv
 * Date: 2020/8/24
 * Version: 1.0
 * Description:
 */

@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String args[]){
        SpringApplication.run(Application.class);
    }
}
