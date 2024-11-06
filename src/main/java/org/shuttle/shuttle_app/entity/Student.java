package org.shuttle.shuttle_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    private Long suID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String suEmail;

    @Column(nullable = false)
    private Status studentStatus = Status.IDLE;

    public Student(Long suID, String name, String suEmail) {
        this.suID = suID;
        this.name = name;
        this.suEmail = suEmail;
    }
}
