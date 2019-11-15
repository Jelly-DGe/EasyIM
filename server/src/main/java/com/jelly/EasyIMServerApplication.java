package com.jelly;

import com.jelly.config.AppConfiguration;
import com.jelly.zookeeper.RegistryZK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;

@SpringBootApplication
public class EasyIMServerApplication implements CommandLineRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(EasyIMServerApplication.class);

    @Autowired
    private AppConfiguration appConfiguration;

    @Value("${server.port}")
    private int httpPort ;

    public static void main(String[] args) {
        SpringApplication.run(EasyIMServerApplication.class, args);
        LOGGER.info("EasyIm Server Start Success!");
    }


    @Override
    public void run(String... strings) throws Exception {
        String addr = InetAddress.getLocalHost().getHostAddress();
        //注册zookeeper
        Thread thread =new Thread(new RegistryZK(addr,appConfiguration.getEasyIMServerPort(),httpPort));
        thread.setName("thread-registryZookeeper");
        thread.start();
    }
}
