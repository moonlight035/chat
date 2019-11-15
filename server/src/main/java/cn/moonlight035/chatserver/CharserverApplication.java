package cn.moonlight035.chatserver;

import cn.moonlight035.chatserver.chat.ChatServerStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CharserverApplication implements CommandLineRunner {

    @Autowired
    private ChatServerStart chatServerStart;

    public static void main(String[] args) {
        SpringApplication.run(CharserverApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        chatServerStart.start();
    }
}
