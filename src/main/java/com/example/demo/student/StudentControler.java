package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/vi/student")
public class StudentControler {

    private final StudentService studentService;

    @Autowired
    public StudentControler(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    //Take request body and map it into a student
    @PostMapping
    public void registerNewStudent (@RequestBody Student student){

        studentService.addNewStudent(student);
    }


//    how to use, delete student with id "3" : DELETE http://localhost:8080/api/vi/student/3
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }

//    PUT: update an existing student
    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Long studentId,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String email){
        studentService.updateStudent(studentId,name,email);

    }
}
