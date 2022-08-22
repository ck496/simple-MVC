package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }



    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student){
        Optional<Student> studentByEmail = studentRepository.findByEmail(student.getEmail());
        if(studentByEmail.isPresent()){
            throw new IllegalStateException("Student with same email already exists");
//          Add server.error.include-message=always in application.properties to show error messages along with the HTTP errors going back to client
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean idExists = studentRepository.existsById(studentId);

        if(!idExists){
            throw new IllegalStateException( "Student with id " + studentId + " does not exist.");
        }

        studentRepository.deleteById(studentId);
    }
    @Transactional // <- Allows you to use setters in your class to change PUT student values without using JSQL queries
    public void updateStudent(Long studentId, String name, String email) {
        Student studentByID = studentRepository.findById(studentId).orElseThrow(() ->
                new IllegalStateException("Student with same email already exists"));

        if(name != null && name.length() > 0 && !Objects.equals(name, studentByID.getName())){
            studentByID.setName(name);
        }
        if(email != null && email.length() > 0 && !Objects.equals(email, studentByID.getEmail())){
//            Check to see if this new email isnt already taken by others in the DB
            Optional<Student> studentByEmail = studentRepository.findByEmail(email);
            if(studentByEmail.isPresent()){
                throw new IllegalStateException("Student with same email already exists");
            }
            studentByID.setEmail(email);
        }
    }
}
