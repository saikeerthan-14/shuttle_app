package org.shuttle.shuttle_app.repository;

import org.shuttle.shuttle_app.entity.Status;
import org.shuttle.shuttle_app.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findBySuID(Long suID);
    List<Student> findAllByStudentStatus(Status status);

    Student findAllBySuID(Long suid);
}
