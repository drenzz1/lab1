package org.foo;

import com.github.javafaker.Faker;
import org.foo.enums.Gender;
import org.foo.models.User;
import org.foo.repository.RoleRepository;
import org.foo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;

@SpringBootApplication
public class Lab1Application {

  public static void main(String[] args) {
    SpringApplication.run(Lab1Application.class, args);
  }




//  @Bean
//  CommandLineRunner runner(UserRepository customerRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
//    return args -> {
//      Faker faker = new Faker();
//      Random random = new Random();
//      var name = faker.name();
//      String firstName = name.firstName();
//      String lastName = name.lastName();
//      User customer = new User(
//        "Dreni" , "Halili",
//"just.drenzz" + "@gmail.com",
//        passwordEncoder.encode("password"), true,"044321992",
//        roleRepository.findById(1L).get()
//        ,
//        Gender.MALE);
//      customerRepository.save(customer);
//    };
//  }
}
