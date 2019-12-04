package com.jelly;

import com.jelly.zookeeper.ServerListListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EasyIMRouteApplication implements CommandLineRunner {
    private final static Logger LOGGER = LoggerFactory.getLogger(EasyIMRouteApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EasyIMRouteApplication.class, args);
        LOGGER.info("启动eastIM-route成功");
    }

    @Override
    public void run(String... strings) throws Exception {
        Thread thread = new Thread(new ServerListListener());
        thread.setName("zk-listener");
        thread.run();
    }
}
