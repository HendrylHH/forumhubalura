package com.forumhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = org.springframework.boot.cloud.CloudFoundryVcapEnvironmentPostProcessor.class)
public class ForumHubApplication {
    public static void main(String[] args) {
        SpringApplication.run(ForumHubApplication.class, args);
    }
}

