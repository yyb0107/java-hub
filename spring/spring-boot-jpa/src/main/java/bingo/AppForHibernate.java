package bingo;

import bingo.entity.TUser;
import bingo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * @author Bingo
 * @title: bingo.App
 * @projectName java-hub
 * @description: TODO
 * @date 2019/10/26  10:02
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "bingo.repository")
@EntityScan("bingo.entity")
public class AppForHibernate {

    public static void main(String[] args) {
        SpringApplication.run(AppForHibernate.class, args);
    }

    @Bean
    public CommandLineRunner springJpa(){
        return new CommandLineRunner() {
            @Autowired
            UserRepository repository;

            @Override
            public void run(String... args) throws Exception {
                TUser user = repository.findById(2).orElse(null);
                System.out.println(user);
            }
        };
    }


//    @Bean
    public CommandLineRunner select() {

        return new CommandLineRunner() {
            @Autowired
            EntityManager em;

            @Override
            public void run(String... args) throws Exception {
                TUser user = new TUser();
                user.setUserid(1);
                user = em.find(TUser.class, Integer.valueOf(1));
                System.out.println(user);
            }
        };

    }

//    @Bean
    public CommandLineRunner update() {
        return new CommandLineRunner() {
            @Autowired
            EntityManager em;

            @Override
            @Transactional
            public void run(String... args) throws Exception {
                TUser user = em.find(TUser.class, Integer.valueOf(2));
                String username = user.getUsername();
                String placeHolder = "-updated";
                if (username.contains(placeHolder)) {
                    user.setUsername(username.replace(placeHolder, ""));
                } else {
                    user.setUsername(placeHolder);
                }
                user = em.merge(user);
                System.out.println(user);
            }
        };
    }


}
