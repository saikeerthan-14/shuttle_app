package org.shuttle.shuttle_app.repository;

import org.shuttle.shuttle_app.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {
    List<Stop> findTop1ByStopName(String name);
}
