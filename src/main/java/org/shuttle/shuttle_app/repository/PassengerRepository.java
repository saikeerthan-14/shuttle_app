package org.shuttle.shuttle_app.repository;

import org.shuttle.shuttle_app.entity.Passenger;
import org.shuttle.shuttle_app.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findByPassengerStatus(Status status);
    List<Passenger> findBySUIDEqualsAndPassengerStatus(Long suid, Status status);
    List<Passenger> findAllByPassengerStatus(Status status);
}
