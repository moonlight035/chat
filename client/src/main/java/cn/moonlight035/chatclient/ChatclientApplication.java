package cn.moonlight035.chatclient;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import sun.applet.Main;

@SpringBootApplication
public class ChatclientApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ChatclientApplication.class);
        builder.web(WebApplicationType.NONE).headless(false).run(args);
    }

}
