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



}
