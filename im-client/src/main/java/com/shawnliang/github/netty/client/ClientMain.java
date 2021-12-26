package com.shawnliang.github.netty.client;

import com.shawnliang.github.netty.client.init.ClientInitialize;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/12/4
 */
@SpringBootApplication
public class ClientMain {

    @Value("${register.user.name}")
    private String userName;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ClientMain.class);
    }

    @Bean
    public ClientInitialize clientInitialize() {
       return new ClientInitialize("127.0.0.1", 7071, userName);
    }

}
