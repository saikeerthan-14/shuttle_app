package org.shuttle.shuttle_app.service;

import org.shuttle.shuttle_app.entity.Status;
import org.shuttle.shuttle_app.entity.Student;

import javax.naming.ServiceUnavailableException;
import java.util.List;

public interface StudentService {

    String requestPickup(Long SUID) throws ServiceUnavailableException;

    Student addStudentToDB(Student student) throws ServiceUnavailableException;

    List<Student> generateStudents(int count) throws ServiceUnavailableException;

    List<Student> getAllStudents(Status status) throws ServiceUnavailableException;
}
