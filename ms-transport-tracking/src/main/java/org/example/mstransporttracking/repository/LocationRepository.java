package org.example.mstransporttracking.repository;

import org.example.mstransporttracking.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
