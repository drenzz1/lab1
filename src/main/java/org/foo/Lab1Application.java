package org.foo;

import com.github.javafaker.Faker;
import org.foo.models.User;
import org.foo.repository.CompanyRepository;
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


  @Bean
  CommandLineRunner runner(UserRepository customerRepository, RoleRepository repository, CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
    return args -> {
      Faker faker = new Faker();
      Random random = new Random();
      var name = faker.name();
      String firstName = name.firstName();
      String lastName = name.lastName();
      User customer = new User(        firstName.toLowerCase() + "." + lastName.toLowerCase() + "@gmail.com",
        passwordEncoder.encode("password"),
        firstName,
        lastName,
        "000",
        true,
        repository.findRoleById(1L),
        companyRepository.findById(2L).get());
      customerRepository.save(customer);
    };
  }

}
