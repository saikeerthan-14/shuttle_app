package org.shuttle.shuttle_app.repository;

import org.shuttle.shuttle_app.entity.Shuttle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShuttleRepository extends JpaRepository<Shuttle, Long> {
//    To retrieve singleton instance if exists
    Shuttle findTopByOrderByIdAsc();
}
