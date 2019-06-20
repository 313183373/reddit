package com.xion.reddit;

import com.xion.reddit.model.Comment;
import com.xion.reddit.model.Link;
import com.xion.reddit.repository.CommentRepository;
import com.xion.reddit.repository.LinkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RedditApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedditApplication.class, args);
        System.out.println("Welcome Reddit!");
    }

    @Bean
    CommandLineRunner runner(LinkRepository linkRepository, CommentRepository commentRepository) {
        return args -> {
            Link link = new Link("Getting Started With Spring Boot 2!", "https://therealdanvega.com/spring-boot-2");
            linkRepository.save(link);

            Comment comment = new Comment("This Spring Boot 2 link is awesome!", link);
            commentRepository.save(comment);

            link.addComment(comment);
        };
    }
}
