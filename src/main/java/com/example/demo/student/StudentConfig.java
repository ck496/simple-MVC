package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args -> {
            Student may = new Student(1L, "May", "may@gmail.com", LocalDate.of(2000, Month.JANUARY, 5));
            Student jon = new Student("John", "jon@gmail.com", LocalDate.of(1996, Month.JANUARY, 5));

            studentRepository.saveAll(
                    List.of(may,jon)
            );
        };
    }
}
