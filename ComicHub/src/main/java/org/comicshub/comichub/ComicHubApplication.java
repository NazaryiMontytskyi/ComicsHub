package org.comicshub.comichub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ComicHubApplication {

    public static void main(String[] args) {

        SpringApplication.run(ComicHubApplication.class, args);
    }

}
