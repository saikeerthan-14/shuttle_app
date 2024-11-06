package org.shuttle.shuttle_app.controller;

import org.shuttle.shuttle_app.entity.Status;
import org.shuttle.shuttle_app.entity.Student;
import org.shuttle.shuttle_app.service.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.ServiceUnavailableException;
import java.util.List;

@RestController
public class StudentController {

    private final StudentServiceImpl studentService;

    @Autowired
    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @PutMapping("/requestPickup")
    public String requestPickup(@RequestParam Long suid) throws ServiceUnavailableException {
        return studentService.requestPickup(suid);
    }

    @PostMapping("/addStudent")
    public String addStudent(@RequestBody Student student) throws ServiceUnavailableException {
        return studentService.addStudentToDB(student).toString();
    }

    @GetMapping("/generateStudents/{count}")
    public List<Student> generateStudents(@PathVariable int count) throws ServiceUnavailableException {
        return studentService.generateStudents(count);
    }

    @GetMapping("/getAllStudents/{status}")
    public List<Student> getAllStudents(@PathVariable Status status) throws ServiceUnavailableException {
        return studentService.getAllStudents(status);
    }
}